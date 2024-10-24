package com.example.myapp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Recipe1 (
    val item : String,
    val color : Color
)

@Composable
fun Slider(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            var yPosition = 0

            placeables.forEach { placeable ->
                placeable.placeRelative(xPosition, yPosition)
                xPosition = xPosition + placeable.width
            }
        }
    }
}

@Composable
fun Recipes(recipes : List<Recipe1>) {
    val density = LocalDensity.current.density
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var sliderOffset by remember { mutableStateOf(0) }
    var sliderIndex = 0
    var startOffset = 0f
    var currentOffset = 0f

    val animatedOffset = animateDpAsState(
        targetValue = sliderOffset.dp
    )

    fun onHandleDragStart(offset : Float) {
        startOffset = offset / density
    }

    fun onHandleDrag(offset : Float, changeAmount : Float) {
        currentOffset = offset / density
        if (changeAmount < 0 && sliderIndex < 5) {
            sliderOffset -= -(changeAmount / density).toInt()
        }
        if (changeAmount > 0 && sliderIndex > 0) {
            sliderOffset += (changeAmount / density).toInt()
        }
    }

    fun onHandleDragEnd(startOffset : Float, currentOffset : Float) {
        if (startOffset > currentOffset && sliderIndex < 5) {
            sliderIndex += 1
            sliderOffset = sliderIndex * -(screenWidth)
        }
        if (startOffset < currentOffset && sliderIndex > 0) {
            sliderIndex -= 1
            sliderOffset = sliderIndex * -(screenWidth)
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset -> onHandleDragStart(offset.x) },
                    onDrag = { change, changeAmount -> onHandleDrag(change.position.x, changeAmount.x) },
                    onDragEnd = { onHandleDragEnd(startOffset, currentOffset) },
                    onDragCancel = {},
                )
            }
    ) {
        Slider (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                // .background(Color.Blue)
                .offset(animatedOffset.value, 0.dp)
        ) {
            recipes.map { recipe ->
                Box(contentAlignment = Alignment.Center) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(color = recipe.color, shape = RoundedCornerShape(5.dp))
                            .width(screenWidth.dp - 20.dp)
                            .height(150.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_icon_image),
                            contentDescription = "Icon Description",
                            tint = Color(0xFF000000),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }
        }
    }
}