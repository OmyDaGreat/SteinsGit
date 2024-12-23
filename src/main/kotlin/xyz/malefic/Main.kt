package xyz.malefic

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import java.io.InputStream
import xyz.malefic.components.box.MaleficBox
import xyz.malefic.components.precompose.NavWindow
import xyz.malefic.extensions.standard.inputstream.grass
import xyz.malefic.git.GitRepository
import xyz.malefic.navigate.RouteManager
import xyz.malefic.navigate.RouteManager.RoutedNavHost
import xyz.malefic.navigate.config.MalefiConfigLoader
import xyz.malefic.screens.Home
import xyz.malefic.screens.LineEditing
import xyz.malefic.screens.MaterialSidebar
import xyz.malefic.screens.parts.SelectWorldlineMessage

/**
 * The main entry point of the application. Sets up the main window and initializes the application
 * components.
 */
fun main() = application {
  NavWindow(onCloseRequest = ::exitApplication, title = "Steins;Git") {
    val currentRepo = remember { mutableStateOf<GitRepository?>(null) }
    val composableMap = composableMap(currentRepo)
    val themeInputStream = getThemeInputStream()

    initializeRouteManager(composableMap)

    MaleficBox(themeInputStream) {
      Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Divider(color = colors.onBackground, modifier = Modifier.fillMaxHeight().width(1.dp))
        MaterialSidebar()
        RoutedNavHost()
      }
    }
  }
}

/**
 * Creates a map of composable functions for different routes.
 *
 * @param currentRepo A mutable state holding the current Git repository.
 * @return A map where the key is the route name and the value is a composable function.
 */
@Composable
fun composableMap(
  currentRepo: MutableState<GitRepository?>
): Map<String, @Composable (List<String?>) -> Unit> {
  return mapOf(
    "Home" to { _ -> Home(currentRepo) },
    "LineEditing" to
      { _ ->
        currentRepo.value?.let { LineEditing(it) { repo -> currentRepo.value = repo } }
          ?: SelectWorldlineMessage()
      },
  )
}

/**
 * Retrieves the input stream for the theme file based on the system's theme setting.
 *
 * @return An InputStream for the theme file.
 * @throws IllegalArgumentException if the theme file is not found.
 */
@Composable
fun Any.getThemeInputStream(): InputStream =
  grass(if (isSystemInDarkTheme()) "/theme/dark.json" else "/theme/light.json")
    ?: throw IllegalArgumentException("Theme file not found")

/**
 * Initializes the route manager with the provided composable map and configuration.
 *
 * @param composableMap A map of composable functions for different routes.
 */
fun Any.initializeRouteManager(composableMap: Map<String, @Composable (List<String?>) -> Unit>) {
  RouteManager.initialize(composableMap, grass("/routes.mfc")!!, MalefiConfigLoader())
}
