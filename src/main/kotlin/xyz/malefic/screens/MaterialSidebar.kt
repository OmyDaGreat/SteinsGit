package xyz.malefic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import xyz.malefic.compose.comps.text.typography.Body1
import xyz.malefic.compose.engine.factory.ButtonFactory
import xyz.malefic.compose.engine.factory.ColumnFactory
import xyz.malefic.compose.engine.pocket.timesAssign
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
                Body1(route.name.capitalize(Locale.current))
            } *= {
                onClick = { navi gate route.name }
            }
        }
    } *= {
        modifier = Modifier.width(200.dp).fillMaxHeight()
        verticalArrangement = Arrangement.Center
        horizontalAlignment = Alignment.CenterHorizontally
    }
}
