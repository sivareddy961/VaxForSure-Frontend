package com.example.vaxforsure.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.Context

@Composable
fun ProfileConfirmationScreen(
    onGoToDashboard: () -> Unit,
    onAddAnotherChild: () -> Unit
) {
    val context = LocalContext.current
    var childName by remember { mutableStateOf("Child") }
    
    LaunchedEffect(Unit) {
        val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
        childName = pref.getString("name", "Child") ?: "Child"
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAF9)) // background
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /* ---------- Success Icon ---------- */
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .shadow(16.dp, CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4DB6AC),
                                Color(0xFF81C784)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Profile Created",
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- Title ---------- */
            Text(
                text = "Profile Created Successfully!",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- Description ---------- */
            Text(
                text = "$childName's profile has been created. You can now start tracking their vaccination schedule.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- Buttons ---------- */
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                /* Go to Dashboard Button */
                Button(
                    onClick = onGoToDashboard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4DB6AC)
                    ),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text(
                        text = "Go to Dashboard",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Go",
                        tint = Color.White
                    )
                }

                /* Add Another Child Button */
                OutlinedButton(
                    onClick = onAddAnotherChild,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, Color(0xFFE5E7EB))
                ) {
                    Text(
                        text = "Add Another Child",
                        fontSize = 16.sp,
                        color = Color(0xFF111827)
                    )
                }
            }
        }
    }
}
