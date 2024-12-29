package xyz.malefic.git.commands

import org.eclipse.jgit.api.Git
import xyz.malefic.git.GitRepository
import java.io.File

fun fetchChanges(repo: GitRepository) {
    val git = Git.open(File(repo.path))
    git.fetch().call()
}
