package com.example.myapp

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

@Composable
fun RecipeTextInput(value: String, onChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = value,
        onValueChange = { eventValue -> onChange(eventValue) },
        textStyle = TextStyle(fontSize = 17.sp),
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(if (isFocused) Color(0xFFfafafa) else Color(0xFFededed), RoundedCornerShape(10.dp))
                    .border(1.dp, if (isFocused) Color(0xFFcf9e82) else Color(0xFFdbdbdb), RoundedCornerShape(10.dp))
                    .padding(45.dp, 0.dp)
            ) {
                innerTextField()
            }
        }
    )
}

@Composable
fun RecipeField(
    title: String,
    icon: Int,
    value: String,
    onChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight(600),
            modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp)
        )
        Box() {
            RecipeTextInput(
                value = value,
                onChange = onChange
            )
            Icon(
                painter = painterResource(icon),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .padding(15.dp, 0.dp)
                    .height(16.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun CreationFlowPage(navController: NavHostController) {
    data class FormFields(
        val name: String,
        val description: String,
        val cookingTime: String
    )

    var formFields by remember {
        mutableStateOf(FormFields(
            name = "",
            description = "",
            cookingTime = ""
        ))
    }

    fun onChangeValue(key: String, eventValue: String) {
        formFields = when (key) {
            "name" -> formFields.copy(name = eventValue)
            "description" -> formFields.copy(description = eventValue)
            "cookingTime" -> formFields.copy(cookingTime = eventValue)
            else -> formFields
        }
    }

    val stroke = Stroke(
        width = 3f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp, 0.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                // .background(Color.Red)
        ) {
            Text(
                text = "Cancel",
                color = Color(0xFFababab),
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    .align(Alignment.TopStart)
                    .clickable { navController.navigate("Home") }
            )
            Text(
                text = "Create Recipe",
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    .align(Alignment.TopCenter)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp, 0.dp, 0.dp, 15.dp)
                .background(Color.Transparent, RoundedCornerShape(16.dp))
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFFcacaca),
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .clip(shape = RoundedCornerShape(16.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_file),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .offset(-25.dp, 0.dp)
                    .width(32.dp)
                    .height(32.dp)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = -25f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_file),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .offset(0.dp, -5.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = 0f)
                    .zIndex(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_file),
                contentDescription = "Icon Description",
                tint = Color(0xFFababab),
                modifier = Modifier
                    .offset(25.dp, 0.dp)
                    .width(32.dp)
                    .height(32.dp)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = 25f)
            )
            Text(
                text = "Choose a file",
                color = Color(0xFF777777),
                modifier = Modifier
                    .offset(0.dp, 38.dp)
                    .align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            RecipeField(
                title = "Name *",
                icon = R.drawable.ic_icon_keyboard,
                value = formFields.name,
                onChange = { eventValue -> onChangeValue("name", eventValue) }
            )
            RecipeField(
                title = "Description",
                icon = R.drawable.ic_icon_keyboard,
                value = formFields.description,
                onChange = { eventValue -> onChangeValue("description", eventValue) }
            )
            RecipeField(
                title = "Cooking time *",
                icon = R.drawable.ic_icon_time,
                value = formFields.cookingTime,
                onChange = { eventValue -> onChangeValue("cookingTime", eventValue) }
            )
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF212121), RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Create",
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}