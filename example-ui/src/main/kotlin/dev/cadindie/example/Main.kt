package dev.cadindie.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "SMML Example Mod Manager") {
        App()
    }
}
