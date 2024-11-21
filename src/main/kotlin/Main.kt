import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.ProvidePreComposeLocals
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import util.theme.ThemeSelectionDropdown
import util.theme.components.LayoutComponents.Sidebar.Sidebar
import util.theme.components.LayoutComponents.SteinsTheme
import util.theme.components.TextComponents.Heading1

@Composable
@Preview
fun App() {
  val navi = rememberNavigator()
  Row(modifier = Modifier.fillMaxSize()) {
    Sidebar(navi)
    Box(modifier = Modifier.fillMaxSize()) {
      NavHost(
        navigator = navi,
        navTransition = NavTransition(),
        initialRoute = "home",
        modifier = Modifier.align(Alignment.Center)
      ) {
        scene("home") { Heading1("Home!") }
        scene("home2") { Heading1("Home 2...") }
        scene("settings") { ThemeSelectionDropdown() }
      }
    }
  }
}

fun main() = application {
  Window(onCloseRequest = ::exitApplication, title = "Steins;Git") {
    ProvidePreComposeLocals { PreComposeApp { SteinsTheme { App() } } }
  }
}
