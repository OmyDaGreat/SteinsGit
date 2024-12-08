import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import git.GitRepository
import screens.App1
import screens.Home
import screens.RepoList
import xyz.malefic.components.precompose.NavWindow
import xyz.malefic.components.text.typography.Heading1
import xyz.malefic.extensions.standard.get
import xyz.malefic.navigate.RouteManager
import xyz.malefic.navigate.RouteManager.RoutedNavHost
import xyz.malefic.navigate.RouteManager.RoutedSidebar
import xyz.malefic.navigate.RouteManager.navi
import xyz.malefic.navigate.config.JsonConfigLoader
import xyz.malefic.theme.MaleficTheme

/**
 * The main entry point of the application. It sets up the main window and applies the appropriate
 * theme based on the system's theme.
 */
fun main() = application {
  NavWindow(onCloseRequest = ::exitApplication, title = "Steins;Git") {
    // Determine the theme file path based on the system's theme (dark or light)
    val themeInputStream =
      javaClass.getResourceAsStream(
        if (isSystemInDarkTheme()) "/theme/dark.json" else "/theme/light.json"
      ) ?: throw IllegalArgumentException("Theme file not found")

    // Apply the selected theme and initialize the route manager
    MaleficTheme(themeInputStream) {
      RouteManager.initialize(
        composableMap,
        this::class.java.getResourceAsStream("/routes.json")!!,
        JsonConfigLoader(),
      )
      NavigationMenu()
    }
  }
}

/**
 * Composable function that defines the navigation menu layout. It includes a sidebar and a main
 * content area separated by a divider.
 */
@Composable
fun NavigationMenu() {
  Box(
    modifier = Modifier.fillMaxSize().background(colors.background),
    contentAlignment = Alignment.Center,
  ) {
    val currentRepo = remember { mutableStateOf<GitRepository?>(null) }
    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
      RepoList { repo -> currentRepo.value = repo }
      Divider(color = colors.onBackground, modifier = Modifier.fillMaxHeight().width(1.dp))
      RoutedSidebar()
      RoutedNavHost()
    }
  }
}

/**
 * A map of composable functions used for routing. Each entry maps a route name to a composable
 * function that takes a list of parameters.
 */
val composableMap: Map<String, @Composable (List<String?>) -> Unit> =
  mapOf(
    "App1" to { params -> App1(id = params[0]!!, name = params[1, null]) },
    "Home" to { _ -> Home(navi) },
    "Text" to { params -> Heading1(text = params[0, "Nope."]) },
  )
