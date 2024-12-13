package xyz.malefic.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.malefic.git.GitRepository
import xyz.malefic.components.box.BackgroundBox

/**
 * A composable function that represents the Home screen. It displays the RepoList on the left and
 * the Paradox on the right.
 */
@Composable
fun Home(currentRepo: MutableState<GitRepository?>) {
  BackgroundBox(contentAlignment = Alignment.Center) {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.Top,
      modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
      // Column for RepoList
      Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(end = 8.dp)) {
        Worldlines(onRepoSelected = { repo -> currentRepo.value = repo })
      }

      // Column for Paradox
      Column(modifier = Modifier.weight(2f).fillMaxHeight().padding(start = 8.dp)) { Paradox() }
    }
  }
}
