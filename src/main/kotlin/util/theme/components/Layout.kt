@file:Suppress("unused")

package util.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import util.Global.theme
import util.theme.components.TextComponents.Heading4

object LayoutComponents {
  @Composable
  fun SteinsTheme(stuff: @Composable BoxScope.() -> Unit) {
    MaterialTheme(colors = theme.colors) { ThemedBox { stuff() } }
  }

  @Composable
  fun ThemedBox(stuff: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.background(theme.colors.background)) { stuff() }
  }

  object Sidebar {
    @Composable
    fun Sidebar(navigator: Navigator) {
      Column(
        modifier =
          Modifier.fillMaxHeight()
            .width(200.dp)
            .background(theme.colors.onBackground)
            .padding(16.dp)
      ) {
        SidebarButton("Home") { navigator.navigate("home") }
        SidebarButton("Home 2") { navigator.navigate("home2") }
        SidebarButton("Settings") { navigator.navigate("settings") }
      }
    }

    @Composable
    fun SidebarButton(text: String, onClick: () -> Unit) {
      ThemedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        invert = true,
      ) {
        Heading4(text)
      }
    }
  }

  @Composable
  fun ThemedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    invert: Boolean = false,
    content: @Composable () -> Unit,
  ) =
    Button(
      onClick = onClick,
      colors =
        ButtonDefaults.buttonColors(
          backgroundColor =
            theme.colors.onBackground.takeUnless { invert } ?: theme.colors.background,
          contentColor = theme.colors.background.takeUnless { invert } ?: theme.colors.onBackground,
        ),
      contentPadding = PaddingValues(16.dp),
      modifier = modifier,
      shape = shape,
    ) {
      content()
    }

  @Composable
  fun ThemedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
  ) =
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = onDismissRequest,
      modifier = Modifier.background(theme.colors.onBackground),
    ) {
      content()
    }
}
