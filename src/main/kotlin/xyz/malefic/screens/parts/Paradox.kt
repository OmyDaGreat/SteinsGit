package xyz.malefic.screens.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import xyz.malefic.git.commands.cloneRepository

/** Composable function that provides a UI for cloning a repository. */
@Composable
fun Paradox() {
    var repoUrl by remember { mutableStateOf("") }
    var destinationPath by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Heading5("Clone a Repository")
        Spacer(modifier = Modifier.height(16.dp))
        RepoUrlInput(repoUrl) { repoUrl = it }
        Spacer(modifier = Modifier.height(16.dp))
        DestinationPathInput(destinationPath) { destinationPath = it }
        Spacer(modifier = Modifier.height(16.dp))
        CloneButton(repoUrl, destinationPath) { statusMessage = it }
        Spacer(modifier = Modifier.height(16.dp))
        StatusMessage(statusMessage)
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
    Column {
        TextField(
            value = destinationPath,
            onValueChange = onValueChange,
            label = { Body1("Destination Path") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { launcher.launch() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        ) {
            Body1("Choose Directory")
        }
    }
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
    Button(
        onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    cloneRepository(repoUrl, destinationPath)
                    onStatusChange("Repository cloned successfully!")
                } catch (e: Exception) {
                    onStatusChange("Failed to clone repository: ${e.message}")
                }
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
    ) {
        Body1("Clone Repository")
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
