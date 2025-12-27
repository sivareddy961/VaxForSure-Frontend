package com.example.vaxforsure.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsScreen(
    navController: NavController? = null
) {
    val context = LocalContext.current
    var childName by remember { mutableStateOf("") }
    var childDob by remember { mutableStateOf("") }
    var childGender by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
        childName = pref.getString("name", "") ?: ""
        childDob = pref.getString("dob", "") ?: ""
        childGender = pref.getString("gender", "") ?: ""
    }

    val backgroundColor = Color(0xFFF7FBFB)
    val cardBorderColor = Color(0xFFD6EAEA)
    val primaryGreen = Color(0xFF4DB6AC)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Children Profiles",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController?.navigate(com.example.vaxforsure.navigation.Destinations.ADD_CHILD)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Child",
                            tint = primaryGreen
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
                .padding(16.dp)
        ) {

            if (childName.isNotEmpty()) {
                ChildProfileCard(
                    name = childName,
                    age = childDob.ifEmpty { "22y 2m" },
                    gender = childGender.ifEmpty { "Male" },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ChildProfileCard(
    name: String,
    age: String,
    gender: String,
    navController: NavController? = null
) {

    val primaryGreen = Color(0xFF4DB6AC)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFD6EAEA),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(primaryGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChildCare,
                        contentDescription = "Child",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name & details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Age",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = age,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = gender,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Edit icon
                IconButton(onClick = {
                    navController?.navigate(com.example.vaxforsure.navigation.Destinations.EDIT_PROFILE)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = primaryGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View Profile Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B2B2B)
                )
            ) {
                Text(
                    text = "View Profile",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
