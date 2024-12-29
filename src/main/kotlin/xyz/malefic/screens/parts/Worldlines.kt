@file:Suppress("ktlint:standard:no-wildcard-imports")

package xyz.malefic.screens.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.malefic.compose.comps.text.typography.Body1
import xyz.malefic.compose.comps.text.typography.Body2
import xyz.malefic.compose.prefs.collection.PersistentArrayList
import xyz.malefic.git.GitRepository
import xyz.malefic.git.commands.findGitRepositoriesFlow
import xyz.malefic.prefNode

/**
 * Sidebar composable function that displays a list of repositories.
 *
 * @param onRepoSelected A callback function that is invoked when a repository is selected.
 */
@Composable
fun Worldlines(
    onRepoSelected: (GitRepository) -> Unit,
    currentRepo: MutableState<GitRepository?>,
) {
    val repos = remember { mutableStateListOf<GitRepository>() }
    val persistentRepos = remember { PersistentArrayList<GitRepository>("steins;repo", prefNode) }

    repos.addAll(persistentRepos)

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            if (currentRepo.value == null && repos.isNotEmpty()) {
                currentRepo.value = repos.first()
            }
            findGitRepositoriesFlow().collect { repo ->
                if (persistentRepos.none { it.path == repo.path }) {
                    persistentRepos.add(repo)
                }
                if (repos.none { it.path == repo.path }) {
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
                        Modifier
                            .clickable { onRepoSelected(repo) }
                            .padding(8.dp)
                            .horizontalScroll(rememberScrollState()),
                )
            }
        }
    }
}
