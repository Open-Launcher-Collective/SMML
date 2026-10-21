import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.compose") version "2.3.0"
    id("org.jetbrains.compose") version "1.10.0"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(project(":"))
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")
    // SearchRequest exposes JetBrains annotations in its public API
    compileOnly("org.jetbrains:annotations:26.0.2")
}

compose.desktop {
    application {
        mainClass = "dev.cadindie.example.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Dmg, TargetFormat.Deb)
            packageName = "SMML Example"
            packageVersion = "1.0.0"
        }
    }
}
