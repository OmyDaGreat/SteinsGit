import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  id("com.diffplug.spotless") version "7.0.0.BETA4"
}

group = "xyz.malefic.steinsgit"
version = "1.0.0"

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  google()
}

val preCompose = "1.6.2"
val jgit = "7.0.0.202409031743-r"

dependencies {
  implementation(compose.desktop.currentOs)
  implementation(compose.animation)
  implementation(compose.foundation)
  implementation("moe.tlaster:precompose:$preCompose")
  implementation("org.eclipse.jgit:org.eclipse.jgit-parent:$jgit")
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
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
