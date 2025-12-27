package com.example.vaxforsure.screens.reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomRemainderScreen(
    navController: NavController,
    isDarkTheme: Boolean
) {
    var reminderTitle by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("09:00") }
    var notes by remember { mutableStateOf("") }

    val background = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF6F7F9)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textPrimary = if (isDarkTheme) Color.White else Color(0xFF1F2937)
    val textSecondary = Color(0xFF6B7280)
    val border = Color(0xFFE5E7EB)
    val teal = Color(0xFF009688)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Custom Reminder", fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        containerColor = background
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            /* ---------------- CHILD ---------------- */
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Child", fontSize = 12.sp, color = textSecondary)

                    Spacer(Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFF374151), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFF0F766E), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("S", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.width(12.dp))

                        Text("siva", color = Color.White, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------------- TITLE ---------------- */
            InputCard(
                label = "Reminder Title *",
                placeholder = "e.g., Doctor's Appointment",
                value = reminderTitle,
                onValueChange = { reminderTitle = it },
                textPrimary = textPrimary,
                cardColor = cardColor,
                border = border
            )

            Spacer(Modifier.height(16.dp))

            /* ---------------- DATE ---------------- */
            IconInputCard(
                label = "Date *",
                placeholder = "dd-mm-yyyy",
                value = date,
                onValueChange = { date = it },
                icon = Icons.Default.CalendarToday,
                textPrimary = textPrimary,
                cardColor = cardColor,
                border = border
            )

            Spacer(Modifier.height(16.dp))

            /* ---------------- TIME ---------------- */
            IconInputCard(
                label = "Time",
                placeholder = "09:00",
                value = time,
                onValueChange = { time = it },
                icon = Icons.Default.AccessTime,
                textPrimary = textPrimary,
                cardColor = cardColor,
                border = border
            )

            Spacer(Modifier.height(16.dp))

            /* ---------------- NOTES ---------------- */
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            tint = textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Notes (Optional)", fontSize = 12.sp, color = textSecondary)
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("Add any additional notes...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = border,
                            focusedBorderColor = teal
                        )
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            /* ---------------- SAVE ---------------- */
            Button(
                onClick = {
                    navController.navigate(com.example.vaxforsure.navigation.Destinations.REMINDER_SETTINGS)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = teal),
                shape = RoundedCornerShape(50)
            ) {
                Text("Save Reminder", fontSize = 16.sp)
            }
        }
    }
}

/* =========================================================
   REUSABLE INPUT CARDS
   ========================================================= */

@Composable
private fun InputCard(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    textPrimary: Color,
    cardColor: Color,
    border: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, fontSize = 12.sp, color = Color(0xFF6B7280))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = border,
                    focusedBorderColor = Color(0xFF009688),
                    unfocusedTextColor = textPrimary,
                    focusedTextColor = textPrimary
                )
            )
        }
    }
}

@Composable
private fun IconInputCard(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    textPrimary: Color,
    cardColor: Color,
    border: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text(label, fontSize = 12.sp, color = Color(0xFF6B7280))
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = border,
                    focusedBorderColor = Color(0xFF009688),
                    unfocusedTextColor = textPrimary,
                    focusedTextColor = textPrimary
                )
            )
        }
    }
}
