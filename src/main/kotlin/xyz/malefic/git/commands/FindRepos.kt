package xyz.malefic.git.commands

import co.touchlab.kermit.Logger
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import xyz.malefic.git.GitRepository

/**
 * Finds all Git repositories in the user's home directory and its subdirectories.
 *
 * @return a flow of `GitRepository` objects representing the found repositories.
 */
fun findGitRepositoriesFlow(): Flow<GitRepository> = flow {
  Logger.d("Searching for Git repositories...")

  val repoDirs =
    File(System.getProperty("user.home"))
      .walk()
      .filter { it.isDirectory && File(it, ".git").exists() }
      .toList()

  repoDirs.forEach { repoDir ->
    Logger.d("Found: ${repoDir.name}")

    val repository =
      FileRepositoryBuilder()
        .setGitDir(File(repoDir, ".git"))
        .readEnvironment()
        .findGitDir()
        .build()
    val git = Git(repository)

    val name = repoDir.name
    val path = repoDir.absolutePath
    val currentBranch = repository.branch
    val remoteUrl =
      git.remoteList().call().firstOrNull()?.urIs?.firstOrNull()?.toString() ?: "<no remote>"
    val isDirty = git.status().call().hasUncommittedChanges()

    emit(GitRepository(name, path, currentBranch, remoteUrl, isDirty))
  }

  Logger.d("Repo search complete.")
}
