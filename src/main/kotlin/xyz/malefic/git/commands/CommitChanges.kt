package xyz.malefic.git.commands

import org.eclipse.jgit.api.Git
import xyz.malefic.git.GitRepository
import java.io.File

fun commitChanges(
    repo: GitRepository,
    message: String,
) {
    val git = Git.open(File(repo.path))
    git.commit().setMessage(message).call()
}
