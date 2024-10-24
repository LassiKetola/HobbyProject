package com.example.myapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.json.Json

@Composable
fun DailyItem(
    recipe: Recipe,
    onOpenRecipe: (Recipe) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp, 10.dp, 0.dp)
            .size(225.dp, 160.dp)
            .background(Color(0xFFededed))
            .clickable { onOpenRecipe(recipe) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(225.dp, 160.dp)
                .border(1.dp, Color(0xFFdedede), RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_image),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .width(26.dp)
                    .height(30.dp)
            )
            Text(text = "missing content", fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SliderComponent(
    recipes: List<Recipe>,
    title: String,
    onOpenRecipe: (Recipe) -> Unit
) {
    // var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }

    LaunchedEffect(Unit) {
        // val res = fetchRecipes(context, fileName)
        // recipes = Json.decodeFromString<List<Recipe>>(res)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        // .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(15.dp, 0.dp)
            // .background(Color.Red)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .align(Alignment.BottomStart)
            )
            Text(
                text = "View all",
                color = Color(0xFFcf9e82),
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Row(
            modifier = Modifier
                .width(800.dp)
                .height(200.dp)
                // .background(Color.LightGray)
                .horizontalScroll(rememberScrollState())
                .padding(10.dp, 0.dp)
        ) {
            recipes?.map { recipe ->
                DailyItem(
                    recipe = recipe,
                    onOpenRecipe = onOpenRecipe
                )
            }
        }
    }
}
