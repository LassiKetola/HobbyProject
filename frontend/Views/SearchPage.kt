package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SearchPage(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current.density
    Column() {
        TopNavigation(
            navController = navController,
            expandedNav = (scrollState.value / density) < 45,
            title = "Search"
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.White)
        )
        BottomNavigation(navController = navController)
    }
}