package xyz.malefic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import xyz.malefic.compose.engine.factory.ButtonFactory
import xyz.malefic.compose.engine.factory.ColumnFactory
import xyz.malefic.compose.engine.factory.TextFactory
import xyz.malefic.compose.engine.factory.div
import xyz.malefic.compose.engine.factory.divAssign
import xyz.malefic.compose.engine.factory.timesAssign
import xyz.malefic.compose.engine.fuel.padding
import xyz.malefic.compose.nav.RouteManager
import xyz.malefic.compose.nav.RouteManager.navi
import xyz.malefic.ext.precompose.gate

/**
 * Composable function that displays a sidebar with navigation buttons. The buttons are generated
 * based on the non-hidden routes from the RouteManager.
 */
@Composable
fun MaterialSidebar() {
    ColumnFactory {
        RouteManager.getNonHiddenRoutes().forEach { route ->
            ButtonFactory {
                TextFactory() /= {
                    text = route.name.capitalize(Locale.current)
                    color = MaterialTheme.colors.onPrimary
                    style = typography.body1
                }
            } / {
                onClick = { navi gate route.name }
            } *= {
                padding(vertical = 8.dp, horizontal = 16.dp)
            }
        }
    } / {
        modifier = Modifier.width(200.dp).fillMaxHeight()
        verticalArrangement = Arrangement.Center
        horizontalAlignment = Alignment.CenterHorizontally
    } *= { padding(16.dp) }
}
