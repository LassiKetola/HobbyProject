package com.example.myapp

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

data class Route (
    val path : String,
    val text : String
)

data class NavLink(
    val path: String,
    val icon: Int,
    val iconSize: Int,
    val textSize: Int
)

@Composable
fun NavLinkButton(
    navLink: NavLink,
    activeRoute: String?
) {
    if (navLink.path != "Create") {
        Icon(
            painter = painterResource(id = navLink.icon),
            contentDescription = "Icon Description",
            tint = if (navLink.path == activeRoute) Color(0xFFcf9e82) else Color(0xFF212121),
            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp).height(navLink.iconSize.dp)
        )
        Text(
            text = navLink.path,
            fontSize = navLink.textSize.sp,
            fontWeight = FontWeight(500),
            color = if (navLink.path == activeRoute) Color(0xFFcf9e82) else Color(0xFF212121),
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .size(65.dp, 40.dp)
                .background(Color(0xFFcf9e82), RoundedCornerShape(20.dp))
        ) {
            Icon(
                painter = painterResource(id = navLink.icon),
                contentDescription = "Icon Description",
                tint = Color.White,
                modifier = Modifier.height(navLink.iconSize.dp)
            )
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    val currentView = navController.currentBackStackEntry?.destination?.route
    val navLinks = listOf(
        NavLink(path = "Home", icon = R.drawable.ic_icon_home2, iconSize = 21, textSize = 12),
        NavLink(path = "Search", icon = R.drawable.ic_icon_search3, iconSize = 21, textSize = 12),
        // NavLink(path = "Create", icon = R.drawable.ic_icon_add, iconSize = 21, textSize = 12),
        NavLink(path = "Activity", icon = R.drawable.ic_icon_bell1, iconSize = 21, textSize = 12),
        NavLink(path = "Profile", icon = R.drawable.ic_icon_avatar, iconSize = 21, textSize = 12),
    )
    fun onHandleRoute(path: String) {
        navController.navigate(path)
    }
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(65.dp)
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(0.dp))
            .background(color = Color(0xFFededed))
    ) {
        navLinks.map { navLink ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onHandleRoute(navLink.path) }
                    )
                    // .clickAnimation()
            ) {
                NavLinkButton(
                    navLink = navLink,
                    activeRoute = currentView,
                )
            }
        }
    }
}