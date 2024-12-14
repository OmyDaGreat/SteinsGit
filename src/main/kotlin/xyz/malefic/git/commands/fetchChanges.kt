package xyz.malefic.git.commands

import java.io.File
import org.eclipse.jgit.api.Git
import xyz.malefic.git.GitRepository

fun fetchChanges(repo: GitRepository) {
  val git = Git.open(File(repo.path))
  git.fetch().call()
}
