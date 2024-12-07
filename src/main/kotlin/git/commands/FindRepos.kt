package git.commands

import git.GitRepository
import java.io.File

/**
 * Finds all Git repositories in the user's home directory and its subdirectories.
 *
 * @return a list of `GitRepository` objects representing the found repositories.
 */
fun findGitRepositories(): List<GitRepository> {
  val repos = mutableListOf<GitRepository>()
  println("Searching for Git repositories...")
  File(System.getProperty("user.home"))
    .walk()
    .filter { it.isDirectory && File(it, ".git").exists() }
    .forEach { repoDir ->
      println("Found: ${repoDir.name}")

      val name = repoDir.name
      val path = repoDir.absolutePath
      val currentBranch = runGitCommand(repoDir, "rev-parse --abbrev-ref HEAD")
      val remoteUrl = runGitCommand(repoDir, "remote get-url origin")
      val isDirty = runGitCommand(repoDir, "status --porcelain").isNotEmpty()

      repos.add(GitRepository(name, path, currentBranch, remoteUrl, isDirty))
    }
  return repos
}

/**
 * Runs a Git command in the specified repository directory.
 *
 * @param repoDir the directory of the Git repository.
 * @param command the Git command to run.
 * @return the output of the Git command.
 */
fun runGitCommand(repoDir: File, command: String): String {
  val process = ProcessBuilder("git", *command.split(" ").toTypedArray()).directory(repoDir).start()
  return process.inputStream.bufferedReader().readText().trim()
}
