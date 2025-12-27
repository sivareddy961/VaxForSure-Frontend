package com.example.vaxforsure.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.navigation.Destinations   // ✅ ADDED IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSettingsScreen(
    navController: NavController,
    isDarkTheme: Boolean
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var reminderTime by remember { mutableStateOf("09:00") }
    var advanceDays by remember { mutableIntStateOf(3) }

    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textPrimary = if (isDarkTheme) Color.White else Color(0xFF212121)
    val textSecondary = Color.Gray
    val teal = Color(0xFF009688)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reminder Settings",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {

            /* ---------------- Notifications Toggle ---------------- */
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = teal,
                        modifier = Modifier
                            .size(40.dp)
                            .background(teal.copy(alpha = 0.15f), CircleShape)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Vaccination Notifications",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textPrimary
                        )
                        Text(
                            text = "Get notified about upcoming vaccinations",
                            fontSize = 13.sp,
                            color = textSecondary
                        )
                    }

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = teal
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------------- Reminder Time ---------------- */
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = teal,
                            modifier = Modifier
                                .size(40.dp)
                                .background(teal.copy(alpha = 0.15f), CircleShape)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Default Reminder Time",
                                fontWeight = FontWeight.SemiBold,
                                color = textPrimary
                            )
                            Text(
                                text = "When to send daily reminders",
                                fontSize = 13.sp,
                                color = textSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = reminderTime,
                        onValueChange = { reminderTime = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Time (HH:MM)") },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------------- Advance Notice ---------------- */
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Advance Notice",
                        fontWeight = FontWeight.SemiBold,
                        color = textPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Remind me $advanceDays days before due date",
                        fontSize = 13.sp,
                        color = textSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = advanceDays.toFloat(),
                        onValueChange = { advanceDays = it.toInt() },
                        valueRange = 1f..14f,
                        steps = 12,
                        colors = SliderDefaults.colors(
                            thumbColor = teal,
                            activeTrackColor = teal
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("1 day", fontSize = 12.sp, color = textSecondary)
                        Text("14 days", fontSize = 12.sp, color = textSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- Add Custom Reminder ---------------- */
            Button(
                onClick = {
                    navController.navigate(Destinations.ADD_CUSTOM_REMINDER) // ✅ ONLY CHANGE
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = teal),
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Custom Reminder")
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------------- Save Button ---------------- */
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("Save Settings", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
