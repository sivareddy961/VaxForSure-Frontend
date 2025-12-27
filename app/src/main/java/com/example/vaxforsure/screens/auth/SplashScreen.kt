package com.example.vaxforsure.screens.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    // Navigate after 2.5 seconds
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate("welcome") {
            popUpTo("splash") { inclusive = true }
        }
    }

    val fadeIn by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000),
        label = "fade"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF4DB6AC),
                        Color(0xFF81C784),
                        Color(0xFF4DB6AC)
                    )
                )
            )
            .alpha(fadeIn)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Security,
                contentDescription = "Shield",
                tint = Color(0xFF4DB6AC),
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "VaxForSure",
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Smart Vaccination Tracker for Kids",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        BouncingDots() // ✅ now resolved
    }
}

/* -------------------------------- */
/* LOADING DOTS COMPOSABLE           */
/* -------------------------------- */

@Composable
fun BouncingDots() {

    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    val dots = listOf(
        infiniteTransition.animateFloat(
            0f, -10f,
            infiniteRepeatable(
                animation = tween(300),
                repeatMode = RepeatMode.Reverse
            ),
            label = "dot1"
        ),
        infiniteTransition.animateFloat(
            0f, -10f,
            infiniteRepeatable(
                animation = tween(300),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(150)
            ),
            label = "dot2"
        ),
        infiniteTransition.animateFloat(
            0f, -10f,
            infiniteRepeatable(
                animation = tween(300),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(300)
            ),
            label = "dot3"
        )
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        dots.forEach {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .offset(y = it.value.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.6f))
            )
        }
    }
}
