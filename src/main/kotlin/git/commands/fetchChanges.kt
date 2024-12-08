package git.commands

import git.GitRepository
import java.io.File
import org.eclipse.jgit.api.Git

fun fetchChanges(repo: GitRepository) {
  val git = Git.open(File(repo.path))
  git.fetch().call()
}
