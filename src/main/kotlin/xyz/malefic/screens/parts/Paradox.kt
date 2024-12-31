package xyz.malefic.screens.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.malefic.compose.comps.text.typography.Body1
import xyz.malefic.compose.comps.text.typography.Heading5
import xyz.malefic.compose.engine.factory.ButtonFactory
import xyz.malefic.compose.engine.factory.CardFactory
import xyz.malefic.compose.engine.factory.ColumnFactory
import xyz.malefic.compose.engine.factory.div
import xyz.malefic.compose.engine.factory.divAssign
import xyz.malefic.compose.engine.factory.timesAssign
import xyz.malefic.compose.engine.fuel.background
import xyz.malefic.compose.engine.fuel.fuel
import xyz.malefic.compose.engine.fuel.padding
import xyz.malefic.compose.engine.fuel.space
import xyz.malefic.git.commands.cloneRepository

@Composable
fun Paradox() {
    var repoUrl by remember { mutableStateOf("") }
    var destinationPath by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    ColumnFactory {
        fuel {
            Heading5("Clone a Repository")
        }.space(24.dp)() // Increased spacing for better separation

        CardFactory {
            ColumnFactory {
                fuel {
                    RepoUrlInput(repoUrl) { repoUrl = it }
                }.space(16.dp)()

                fuel {
                    DestinationPathInput(destinationPath) { destinationPath = it }
                }.space(16.dp)()

                fuel {
                    CloneButton(repoUrl, destinationPath) { statusMessage = it }
                }.space(16.dp)()
            } /= {
                modifier = Modifier.padding(16.dp) // Padding inside the card
            }
        } / {
            shape = RoundedCornerShape(8.dp)
            elevation = 4.dp
            modifier = Modifier.fillMaxWidth()
        } *= {
            padding(8.dp)
        }

        StatusMessage(statusMessage)
    }.apply {
        horizontalAlignment = Alignment.CenterHorizontally
        verticalArrangement = Arrangement.Center
        modifier =
            Modifier
                .fillMaxSize()
    } *= {
        padding(16.dp)
        background(MaterialTheme.colors.background)
    }
}

/**
 * Composable function for inputting the repository URL.
 *
 * @param repoUrl The current value of the repository URL.
 * @param onValueChange Callback to update the repository URL.
 */
@Composable
fun RepoUrlInput(
    repoUrl: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = repoUrl,
        onValueChange = onValueChange,
        label = { Body1("Repository URL") },
        modifier = Modifier.fillMaxWidth(),
    )
}

/**
 * Composable function for inputting the destination path.
 *
 * @param destinationPath The current value of the destination path.
 * @param onValueChange Callback to update the destination path.
 */
@Composable
fun DestinationPathInput(
    destinationPath: String,
    onValueChange: (String) -> Unit,
) {
    val launcher =
        rememberDirectoryPickerLauncher(
            title = "Pick a directory",
            initialDirectory = System.getProperty("user.home"),
        ) { directory ->
            directory?.path?.let { onValueChange(it) }
        }
    ColumnFactory {
        fuel {
            TextField(
                value = destinationPath,
                onValueChange = onValueChange,
                label = { Body1("Destination Path") },
                modifier = Modifier.fillMaxWidth(),
            )
        }.space(8.dp)()
        ButtonFactory { Body1("Choose Directory") } /= {
            onClick = { launcher.launch() }
        }
    }()
}

/**
 * Composable function for the clone button.
 *
 * @param repoUrl The URL of the repository to clone.
 * @param destinationPath The path where the repository will be cloned.
 * @param onStatusChange Callback to update the status message.
 */
@Composable
fun CloneButton(
    repoUrl: String,
    destinationPath: String,
    onStatusChange: (String) -> Unit,
) {
    ButtonFactory { Body1("Clone Repository") } /= {
        onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    cloneRepository(repoUrl, destinationPath)
                    onStatusChange("Repository cloned successfully!")
                } catch (e: Exception) {
                    onStatusChange("Failed to clone repository: ${e.message}")
                }
            }
        }
    }
}

/**
 * Composable function to display the status message.
 *
 * @param statusMessage The current status message.
 */
@Composable
fun StatusMessage(statusMessage: String) {
    if (statusMessage.isNotEmpty()) {
        Body1(statusMessage)
    }
}
