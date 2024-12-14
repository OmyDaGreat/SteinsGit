import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization") version "2.1.0"
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  alias(libs.plugins.spotless)
}

group = "xyz.malefic"
version = "1.0.0"

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  google()
}

dependencies {
  implementation(compose.desktop.currentOs)
  implementation(libs.malefic.extensions)
  implementation(libs.malefic.components)
  implementation(libs.malefic.theming)
  implementation(libs.malefic.prefs)
  implementation(libs.malefic.nav)
  implementation(libs.filekit.compose)
  implementation(libs.filekit.core)
  implementation(libs.precompose)
  implementation(libs.kermit)
  implementation(libs.slf4j)
  implementation(libs.jgit)
}

compose.desktop {
  application {
    mainClass = "xyz.malefic.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "SteinsGit"
      packageVersion = "1.0.0"
    }
  }
}

spotless {
  kotlin {
    ktfmt().googleStyle()
  }
}
