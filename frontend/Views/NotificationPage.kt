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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun NotificationPage(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current.density
    var testData by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(Unit) {
        // val randomPost = fetchPosts()

        // testData = randomPost
    }

    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.White)
        )

        // Text(text = "${ testData?.id } :: ${ testData?.body}", fontSize = 10.sp)
        BottomNavigation(navController = navController)
    }
}