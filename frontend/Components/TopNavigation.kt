package com.example.myapp

import android.view.Surface
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController


@Composable
fun StyledTextField() {
    var value by rememberSaveable { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = value,
        onValueChange = { value = it },
        textStyle = TextStyle(
            fontSize = 18.sp,
        ),
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
                    .background(Color(0xFFededed), RoundedCornerShape(20.dp))
                    // .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .padding(50.dp, 0.dp)
            ) {
                innerTextField()
            }
        }
    )

    if (isFocused == false) {
        Box(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_search),
                contentDescription = "Icon Description",
                tint = Color.Black,
                modifier = Modifier
                    .padding(15.dp, 0.dp)
                    .height(20.dp)
                    .align(Alignment.CenterStart)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(45.dp, 45.dp)
                .padding(0.dp, 0.dp)
                .background(Color(0xFFe1e1e1), RoundedCornerShape(30.dp, 0.dp, 0.dp, 30.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        value = ""
                        isFocused = false
                        focusManager.clearFocus()
                    }
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_arrowleft),
                contentDescription = "Icon Description",
                tint = Color.Black,
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .height(20.dp)
                    .align(Alignment.Center)
            )
        }
    }

    if (value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(50.dp, 0.dp)) {
            Text(
                text = "Type to search.",
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun NavigationSmall(title: String) {
    Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(0.dp, 15.dp, 15.dp, 0.dp)
                .size(30.dp, 30.dp)
                .align(Alignment.TopEnd)
                .background(Color(0xFFededed), RoundedCornerShape(20.dp))
                .border(1.dp, Color(0xFFdedede), RoundedCornerShape(20.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_basket2),
                contentDescription = "Icon Description",
                tint = Color.Black,
                modifier = Modifier.height(16.dp)
            )
        }
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(15.dp, 0.dp, 0.dp, 10.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun NavigationLarge(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(10.dp, 0.dp)
    ) {
        StyledTextField()
        Icon(
            painter = painterResource(id = R.drawable.ic_icon_filter),
            contentDescription = "Icon Description",
            tint = Color.Black,
            modifier = Modifier
                .padding(15.dp, 0.dp)
                .height(20.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun TopNavigation(
    navController: NavHostController,
    expandedNav: Boolean,
    title: String
) {
    val navHeight by animateDpAsState(
        targetValue = if (expandedNav) 84.dp else 84.dp,
        animationSpec = tween(durationMillis = 150)
    )
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(0.dp))
            .background(color = Color(0xFFffffff))
            .zIndex(1f)
            .animateContentSize()
            .padding(top = 24.dp, bottom = 15.dp)
    ) {
        NavigationLarge(title = title)
    }
}