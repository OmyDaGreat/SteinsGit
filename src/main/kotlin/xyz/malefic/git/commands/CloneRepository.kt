package xyz.malefic.git.commands

import org.eclipse.jgit.api.Git
import java.io.File

fun cloneRepository(
    url: String,
    destination: String,
) {
    Git
        .cloneRepository()
        .setURI(url)
        .setDirectory(File(destination))
        .call()
}
