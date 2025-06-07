package com.example.cardify.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cardify.ui.components.BottomNavBar

@Composable
fun CardBookScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = "cardbook",
                onNavigateToMain = { navController.popBackStack() },
                onNavigateToCardBook = {},
                onNavigateToSettings = {}
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding))
    }
}
