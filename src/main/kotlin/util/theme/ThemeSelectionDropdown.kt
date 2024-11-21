package util.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import util.Global
import util.theme.components.LayoutComponents.ThemedButton
import util.theme.components.LayoutComponents.ThemedDropdownMenu
import util.theme.components.TextComponents.Body2

@Composable
fun ThemeSelectionDropdown() {
  var expanded by remember { mutableStateOf(false) }
  val themes = ThemeType.entries.toTypedArray()

  Box(contentAlignment = Alignment.Center) {
    ThemedButton(onClick = { expanded = true }) { Body2("Select Theme") }
    ThemedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      themes.forEach { themeType ->
        DropdownMenuItem(
          onClick = {
            Global.theme = themeType
            expanded = false
          }
        ) {
          Body2(text = themeType.name)
        }
      }
    }
  }
}
