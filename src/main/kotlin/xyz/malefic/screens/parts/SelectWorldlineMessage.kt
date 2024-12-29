package xyz.malefic.screens.parts

import androidx.compose.runtime.Composable
import xyz.malefic.compose.comps.box.BackgroundBox
import xyz.malefic.compose.comps.text.typography.Heading5

/**
 * A composable function that displays a message prompting the user to select a Worldline before
 * attempting to edit one.
 */
@Composable
fun SelectWorldlineMessage() {
    BackgroundBox { Heading5("Please select a Worldline before trying to edit one.") }
}
