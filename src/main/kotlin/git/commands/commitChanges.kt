package git.commands

import git.GitRepository
import java.io.File
import org.eclipse.jgit.api.Git

fun commitChanges(repo: GitRepository, message: String) {
  val git = Git.open(File(repo.path))
  git.commit().setMessage(message).call()
}
