package xyz.malefic.git.commands

import java.io.File
import org.eclipse.jgit.api.Git
import xyz.malefic.git.GitRepository

fun commitChanges(repo: GitRepository, message: String) {
  val git = Git.open(File(repo.path))
  git.commit().setMessage(message).call()
}
