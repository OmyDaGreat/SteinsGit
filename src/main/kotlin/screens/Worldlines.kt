@file:Suppress("kotlin:S1128")

package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import git.GitRepository
import git.commands.findGitRepositories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import prefNode
import xyz.malefic.components.text.typography.Body1
import xyz.malefic.components.text.typography.Body2
import xyz.malefic.extensions.composables.clickableWithRipple
import xyz.malefic.prefs.collection.PersistentArrayList

/**
 * Sidebar composable function that displays a list of repositories.
 *
 * @param onRepoSelected A callback function that is invoked when a repository is selected.
 */
@Composable
fun Worldlines(onRepoSelected: (GitRepository) -> Unit) {
  val repos = remember { mutableStateListOf<GitRepository>() }
  val persistentRepos = remember { PersistentArrayList<GitRepository>("steins;repo", prefNode) }

  if (persistentRepos.isEmpty()) {
    persistentRepos.addAll(findGitRepositories())
  }
  repos.addAll(persistentRepos)

  // Start a coroutine to update the persistent repos
  LaunchedEffect(Unit) {
    CoroutineScope(Dispatchers.IO).launch {
      val newRepos = findGitRepositories()
      val existingRepoNames = repos.map { it.name }.toSet()
      newRepos.forEach { repo ->
        if (repo.name !in existingRepoNames) {
          persistentRepos.add(repo)
          repos.add(repo)
        }
      }
    }
  }

  // Column layout for the sidebar
  Column(modifier = Modifier.fillMaxHeight().background(colors.background).width(200.dp)) {
    // Heading for the repositories section
    Body1("Repositories", modifier = Modifier.padding(16.dp))

    // LazyColumn to display the list of repositories
    LazyColumn {
      items(repos) { repo ->
        // Display each repository name as a clickable item
        Body2(
          text = repo.name,
          modifier =
            Modifier.clickableWithRipple { onRepoSelected(repo) }
              .padding(8.dp)
              .horizontalScroll(rememberScrollState()),
        )
      }
    }
  }
}
