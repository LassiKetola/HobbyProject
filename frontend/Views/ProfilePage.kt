package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CategoryButton(
    modifier: Modifier,
    title: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .fillMaxHeight()
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .then(if (selected)
                Modifier.drawBehind {
                    val borderSize = 3.dp.toPx()
                    drawLine(
                        color = Color(0xFFcf9e82),
                        start = Offset(0f, size.height), // Start at the bottom-left
                        end = Offset(size.width, size.height), // End at the bottom-right
                        strokeWidth = borderSize
                    )
                } else Modifier
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onSelect() }
            )
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight(500),
            color = if (selected) Color(0xFFcf9e82) else Color.Black,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipesList(
    onOpenRecipe: (Recipe) -> Unit,
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var recipes by remember { mutableStateOf<List<Recipe>?>(null) }

    LaunchedEffect(Unit) {
        try {
            recipes = fetchRecipes()
        } catch (e: Exception) {
            null
        }
    }

    FlowRow(
        modifier = Modifier
            .width(screenWidth)
            .padding(0.dp, 5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 5.dp)
                .width((screenWidth / 3) - 7.dp)
                .height(120.dp)
                .background(Color(0xFFededed), RoundedCornerShape(3.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(3.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate("Create") }
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_add),
                contentDescription = "Icon Description",
                tint = Color.Black,
                modifier = Modifier
                    .width(26.dp)
                    .height(30.dp)
            )
            Text(
                text = "Add recipe",
                fontWeight = FontWeight(400),
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        recipes?.map { recipe -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable { onOpenRecipe(recipe) }
                .padding(5.dp, 0.dp, 0.dp, 5.dp)
                .width((screenWidth / 3) - 6.dp)
                .height(120.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(3.dp))
            ) {
                Text(text = recipe.name, fontSize = 14.sp, fontWeight = FontWeight(500))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileView(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: RecipeViewModel
) {
    var selectedCategory by remember { mutableStateOf("My posts") }

    fun onOpenRecipe(recipe: Recipe) {
        navController.navigate("Recipe")
        viewModel.setRecipe(recipe)
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // .background(Color(0xFFededed))
                .drawBehind {
                    val borderSize = 2.dp.toPx() // Thickness of the border
                    drawLine(
                        color = Color(0xFFdedede),
                        start = Offset(0f, size.height), // Start at the bottom-left
                        end = Offset(size.width, size.height), // End at the bottom-right
                        strokeWidth = borderSize
                    )
                }
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(0.dp, 0.dp)
            ) {
                listOf("My posts", "Bookmarked", "Settings").map { category ->
                    CategoryButton(
                        modifier = Modifier.weight(1f),
                        title = category,
                        selected = category == selectedCategory,
                        onSelect = { selectedCategory = category }
                    )
                }
            }
        }
        RecipesList(
            onOpenRecipe = { recipe -> onOpenRecipe(recipe) },
            navController = navController
        )
    }
}

@Composable
fun ProfilePage(
    viewModel: RecipeViewModel,
    navController: NavHostController
) {
    Column() {
        ProfileView(
            modifier = Modifier.weight(1f),
            navController = navController,
            viewModel = viewModel
        )
        BottomNavigation(navController = navController)
    }
}