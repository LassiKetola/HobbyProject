package com.example.myapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Composable
fun QuickLinks(links: List<String>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .horizontalScroll(rememberScrollState())) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .width((links.size * 85).dp)
                .height(45.dp)
                .padding(5.dp, 0.dp)
        ) {
            links.map { link ->
                Box(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        .height(30.dp)
                        .background(Color(0xFFededed), RoundedCornerShape(15.dp))
                        .border(1.dp, Color(0xFFdedede), RoundedCornerShape(15.dp))
                        .padding(20.dp, 0.dp)
                ) {
                    Text(
                        text = link,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    viewModel: RecipeViewModel,
    navController: NavHostController,
    scrollState: ScrollState
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    var dailyPickPosts by remember { mutableStateOf<List<Recipe>?>(null) }
    var popularPosts by remember { mutableStateOf<List<Recipe>?>(null) }
    var recentlyViewedPosts by remember { mutableStateOf<List<Recipe>?>(null) }


    fun onOpenRecipe(recipe: Recipe) {
        navController.navigate("Recipe")
        viewModel.setRecipe(recipe)
    }

    LaunchedEffect(Unit) {
        try {
            dailyPickPosts = fetchRecipes()
            recentlyViewedPosts = fetchRecipes()
        } catch (e: Exception) {
            // null
        }
    }

    Column(
        modifier = Modifier
            .width(width)
            .verticalScroll(scrollState)
    ) {
        QuickLinks(
            links = listOf("Breakfast", "Pizza", "Salad", "Snacks", "Meat", "Vegetarian", "Pasta")
        )
        Box(
            modifier = Modifier
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
                .fillMaxWidth()
                .height(155.dp)
                .background(Color(0xFFededed), RoundedCornerShape(10.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_image),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .width(26.dp)
                    .height(30.dp)
                    .align(Alignment.Center)
            )
        }
        dailyPickPosts?.let {
            SliderComponent(
                recipes = it,
                title = "Daily picks",
                onOpenRecipe = { recipe -> onOpenRecipe(recipe) }
            )
        }
        recentlyViewedPosts?.let {
            SliderComponent(
                recipes = it,
                title = "Recently viewed",
                onOpenRecipe = { recipe -> onOpenRecipe(recipe) }
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1200.dp)
        )
    }

}

@Composable
fun Home(viewModel: RecipeViewModel, navController: NavHostController) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current.density
    Surface(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopNavigation(
                navController = navController,
                expandedNav = (scrollState.value / density) < 45,
                title = "Discover"
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
            ) {
                HomeContent(
                    viewModel = viewModel,
                    navController = navController,
                    scrollState = scrollState
                )
            }
            BottomNavigation(navController = navController)
        }
    }

}
