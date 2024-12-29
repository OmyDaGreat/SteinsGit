package xyz.malefic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.git.GitRepository
import xyz.malefic.screens.parts.Paradox
import xyz.malefic.screens.parts.Worldlines

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
                Worldlines(onRepoSelected = { repo -> currentRepo.value = repo }, currentRepo)
            }

            // Column for Paradox
            Column(modifier = Modifier.weight(2f).fillMaxHeight().padding(start = 8.dp)) { Paradox() }
        }
    }
}
