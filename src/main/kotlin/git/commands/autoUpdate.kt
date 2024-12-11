package git.commands

import git.GitRepository
import java.io.File
import java.time.LocalDateTime.now
import org.eclipse.jgit.api.Git

fun autoUpdate(repo: GitRepository) {
  val git = Git.open(File(repo.path))
  git.fetch().call()
  git.pull().call()
  git.commit().setMessage("Auto-commit at ${now()}").call()
  git.push().call()
}
