package xyz.malefic.git.commands

import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import xyz.malefic.git.GitRepository
import java.io.File
import kotlin.system.measureTimeMillis

/**
 * Finds all Git repositories in the user's home directory and its subdirectories.
 *
 * @return a flow of `GitRepository` objects representing the found repositories.
 */
fun findGitRepositoriesFlow(): Flow<GitRepository> =
    flow {
        Logger.d("Starting Git repository search...")

        val duration =
            measureTimeMillis {
                // Get home directory
                val homeDir = File(System.getProperty("user.home"))

                // Use parallel processing for efficiency
                coroutineScope {
                    homeDir
                        .walkTopDown()
                        .filter { it.isDirectory }
                        .filter { it.name != ".git" } // Skip `.git` directories themselves
                        .map { dir ->
                            async(Dispatchers.IO) {
                                val gitDir = File(dir, ".git")
                                if (gitDir.exists()) dir else null
                            }
                        }.toList()
                        .mapNotNull { it.await() } // Wait for parallel checks to complete
                        .forEach { repoDir ->
                            Logger.d("Found Git repository: ${repoDir.name}")

                            try {
                                val repository =
                                    FileRepositoryBuilder()
                                        .setGitDir(File(repoDir, ".git"))
                                        .readEnvironment()
                                        .findGitDir()
                                        .build()
                                val git = Git(repository)

                                emit(
                                    GitRepository(
                                        name = repoDir.name,
                                        path = repoDir.absolutePath,
                                        currentBranch = repository.branch,
                                        remoteUrl =
                                            git
                                                .remoteList()
                                                .call()
                                                .firstOrNull()
                                                ?.urIs
                                                ?.firstOrNull()
                                                ?.toString(),
                                        isDirty = git.status().call().hasUncommittedChanges(),
                                    ),
                                )
                            } catch (e: Exception) {
                                Logger.e("Error reading repository ${repoDir.name}: ${e.message}")
                            }
                        }
                }
            }

        Logger.d("Git repository search complete. Duration: ${duration}ms")
    }.flowOn(Dispatchers.IO)
