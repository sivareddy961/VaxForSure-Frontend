package com.example.vaxforsure.screens.notifications

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.navigation.Destinations

/* =========================================================
   DATA MODEL
   ========================================================= */
data class NotificationItem(
    val id: String,
    val type: String,
    val title: String,
    val message: String,
    val time: String,
    val read: Boolean
)

/* =========================================================
   SAMPLE DATA
   ========================================================= */
val notificationList = listOf(
    NotificationItem(
        id = "1",
        type = "reminder",
        title = "Vaccination Reminder",
        message = "DTaP (2nd Booster) is due in 3 days for Emma",
        time = "2 hours ago",
        read = false
    ),
    NotificationItem(
        id = "2",
        type = "tip",
        title = "Health Tip",
        message = "Make sure to keep your child hydrated after vaccination",
        time = "1 day ago",
        read = false
    ),
    NotificationItem(
        id = "3",
        type = "reminder",
        title = "Schedule Updated",
        message = "New vaccine added to schedule",
        time = "2 days ago",
        read = true
    )
)

/* =========================================================
   ICON MAPPER
   ========================================================= */
@Composable
fun notificationIcon(type: String) = when (type) {
    "reminder" -> Icons.Filled.CalendarToday
    "tip" -> Icons.Filled.Info
    else -> Icons.Filled.Notifications
}

/* =========================================================
   SCREEN
   ========================================================= */
@Composable
fun NotificationsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFA))
    ) {

        /* ---------------- HEADER ---------------- */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Notifications",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Mark all read",
                color = Color(0xFF4DB6AC),
                fontSize = 14.sp
            )
        }

        /* ---------------- LIST ---------------- */
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            notificationList.forEach { item ->
                NotificationCard(
                    item = item,
                    onClick = {
                        // ✅ NAVIGATE TO TODAY ALERTS
                        navController.navigate(Destinations.TODAY_ALERTS)
                    }
                )
            }
        }
    }
}

/* =========================================================
   NOTIFICATION CARD
   ========================================================= */
@Composable
fun NotificationCard(
    item: NotificationItem,
    onClick: () -> Unit
) {

    val background =
        if (item.read) Color.White else Color(0xFF0F2E2E)

    val textColor =
        if (item.read) Color.Black else Color.White

    val subTextColor =
        if (item.read) Color.Gray else Color.White.copy(alpha = 0.7f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // ✅ CLICK HANDLER
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = background),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* ICON */
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (item.read)
                            Color(0xFFE0F7FA)
                        else
                            Color(0xFF163F3F),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notificationIcon(item.type),
                    contentDescription = null,
                    tint = Color(0xFF4DB6AC),
                    modifier = Modifier.size(24.dp)
                )
            }

            /* CONTENT */
            Column(modifier = Modifier.weight(1f)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = textColor
                    )

                    if (!item.read) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF4DB6AC), CircleShape)
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = item.message,
                    fontSize = 13.sp,
                    color = subTextColor
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = item.time,
                    fontSize = 12.sp,
                    color = subTextColor
                )
            }
        }
    }
}
