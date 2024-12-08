package git.commands

import java.io.File
import org.eclipse.jgit.api.Git

fun cloneRepository(url: String, destination: String) {
  Git.cloneRepository().setURI(url).setDirectory(File(destination)).call()
}
