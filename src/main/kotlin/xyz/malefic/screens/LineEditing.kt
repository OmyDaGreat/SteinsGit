package xyz.malefic.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.eclipse.jgit.api.Git
import xyz.malefic.compose.comps.text.typography.Body1
import xyz.malefic.compose.comps.text.typography.Heading4
import xyz.malefic.compose.comps.text.typography.Heading5
import xyz.malefic.git.GitRepository
import java.io.File

/**
 * Composable function to display and edit repository settings.
 *
 * @param repo The Git repository to edit.
 * @param onRepoUpdate Callback function to handle repository updates.
 */
@Composable
fun LineEditing(
    repo: GitRepository,
    onRepoUpdate: (GitRepository) -> Unit,
) {
    var name by remember { mutableStateOf(repo.name) }
    var path by remember { mutableStateOf(repo.path) }
    var currentBranch by remember { mutableStateOf(repo.currentBranch) }
    var autoUpdate by remember { mutableStateOf(repo.autoUpdate) }
    var branches by remember { mutableStateOf(listOf<String>()) }

    // Load branches when the repository path changes
    LaunchedEffect(repo.path) {
        val git = Git.open(File(repo.path))
        branches = git.branchList().call().map { it.name }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Heading4("Repository Settings")
        Spacer(modifier = Modifier.height(8.dp))

        RepoNameField(name) { name = it }
        Spacer(modifier = Modifier.height(8.dp))

        RepoPathField(path) { path = it }
        Spacer(modifier = Modifier.height(8.dp))

        CurrentBranchField(currentBranch) { currentBranch = it }
        Spacer(modifier = Modifier.height(8.dp))

        BranchesList(branches, currentBranch) { branch ->
            currentBranch = branch
            val git = Git.open(File(repo.path))
            git.checkout().setName(branch).call()
        }
        Spacer(modifier = Modifier.height(8.dp))

        AutoUpdateCheckbox(autoUpdate) { autoUpdate = it }
        Spacer(modifier = Modifier.height(8.dp))

        SaveButton(repo, name, path, currentBranch, autoUpdate, onRepoUpdate)
    }
}

/**
 * Composable function to display and edit the repository name.
 *
 * @param name The current name of the repository.
 * @param onNameChange Callback function to handle name changes.
 */
@Composable
fun RepoNameField(
    name: String,
    onNameChange: (String) -> Unit,
) {
    Heading5("Name")
    BasicTextField(
        value = name,
        onValueChange = onNameChange,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colors.onSurface),
    )
}

/**
 * Composable function to display and edit the repository path.
 *
 * @param path The current path of the repository.
 * @param onPathChange Callback function to handle path changes.
 */
@Composable
fun RepoPathField(
    path: String,
    onPathChange: (String) -> Unit,
) {
    Heading5("Path")
    BasicTextField(
        value = path,
        onValueChange = onPathChange,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colors.onSurface),
    )
}

/**
 * Composable function to display and edit the current branch of the repository.
 *
 * @param currentBranch The current branch of the repository.
 * @param onBranchChange Callback function to handle branch changes.
 */
@Composable
fun CurrentBranchField(
    currentBranch: String,
    onBranchChange: (String) -> Unit,
) {
    Heading5("Current Branch")
    BasicTextField(
        value = currentBranch,
        onValueChange = onBranchChange,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colors.onSurface),
    )
}

/**
 * Composable function to display the list of branches and allow branch selection.
 *
 * @param branches The list of branches in the repository.
 * @param currentBranch The currently selected branch.
 * @param onBranchSelect Callback function to handle branch selection.
 */
@Composable
fun BranchesList(
    branches: List<String>,
    currentBranch: String,
    onBranchSelect: (String) -> Unit,
) {
    Heading5("Branches")
    branches.forEach { branch ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = branch.contains(currentBranch),
                onClick = { onBranchSelect(branch) },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary),
            )
            Body1(branch)
        }
    }
}

/**
 * Composable function to display and edit the auto-update setting.
 *
 * @param autoUpdate The current auto-update setting.
 * @param onAutoUpdateChange Callback function to handle auto-update changes.
 */
@Composable
fun AutoUpdateCheckbox(
    autoUpdate: Boolean,
    onAutoUpdateChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = autoUpdate, onCheckedChange = onAutoUpdateChange)
        Body1("Auto Update")
    }
}

/**
 * Composable function to display a save button and handle saving the repository settings.
 *
 * @param repo The current Git repository.
 * @param name The current name of the repository.
 * @param path The current path of the repository.
 * @param currentBranch The current branch of the repository.
 * @param autoUpdate The current auto-update setting.
 * @param onRepoUpdate Callback function to handle repository updates.
 */
@Composable
fun SaveButton(
    repo: GitRepository,
    name: String,
    path: String,
    currentBranch: String,
    autoUpdate: Boolean,
    onRepoUpdate: (GitRepository) -> Unit,
) {
    Button(
        onClick = {
            val updatedRepo =
                repo.copy(name = name, path = path, currentBranch = currentBranch, autoUpdate = autoUpdate)
            onRepoUpdate(updatedRepo)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
    ) {
        Body1("Save")
    }
}
