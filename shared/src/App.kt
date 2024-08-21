package com.sun5066.dailypulse

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sun5066.dailypulse.screens.home.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(HomeScreen)
    }
}