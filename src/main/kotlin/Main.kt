import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.ProvidePreComposeLocals
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import theme.SteinsTheme

@Composable
@Preview
fun App() = PreComposeApp {
    SteinsTheme {
        val navi = rememberNavigator()
        NavHost(
            navigator = navi, navTransition = NavTransition(), initialRoute = "home"
        ) {
            scene("home") {
                Text("Home!")
            }
            scene("home2") {
                Text("Home 2...")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Steins;Git") {
        ProvidePreComposeLocals {
            App()
        }
    }
}
