package com.example.myapp


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.navigationBarColor = Color(0xFFededed).toArgb()
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun App() {
    val recipeViewModel = RecipeViewModel()
    val navController = rememberNavController()
    val b1 = Build.VERSION.SDK_INT
    val b2 = Build.VERSION_CODES.S

    NavHost(
        navController = navController,
        startDestination = "Profile",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable("Home") {
            Home(
                recipeViewModel,
                navController
            )
        }
        composable("Search") {
            SearchPage(navController)
        }
        composable("Create") {
            CreationFlowPage(navController)
        }
        composable("Activity") {
            NotificationPage(navController)
        }
        composable("Profile") {
            ProfilePage(
                recipeViewModel,
                navController
            )
        }
        composable("Recipe") {
            RecipePage(
                recipeViewModel,
                navController
            )
        }
    }

}
