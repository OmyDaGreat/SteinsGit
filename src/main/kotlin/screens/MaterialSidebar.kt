package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import xyz.malefic.components.text.typography.Body1
import xyz.malefic.extensions.precompose.gate
import xyz.malefic.navigate.RouteManager
import xyz.malefic.navigate.RouteManager.navi

/**
 * Composable function that displays a sidebar with navigation buttons. The buttons are generated
 * based on the non-hidden routes from the RouteManager.
 */
@Composable
fun MaterialSidebar() {
  Column(
    modifier = Modifier.width(200.dp).fillMaxHeight(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    RouteManager.getNonHiddenRoutes().forEach { route ->
      Button(
        onClick = { navi gate route.name },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
      ) {
        Body1(route.name.capitalize(Locale.current))
      }
    }
  }
}
