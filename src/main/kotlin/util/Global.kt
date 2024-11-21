package util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import util.theme.ThemeType

object Global {
  var theme by mutableStateOf(ThemeType.Mint)
}
