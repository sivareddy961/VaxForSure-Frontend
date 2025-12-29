package com.example.vaxforsure.screens.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.vaxforsure.navigation.Destinations
import androidx.compose.ui.platform.LocalContext
import com.example.vaxforsure.utils.PreferenceManager
import androidx.compose.runtime.*
import com.example.vaxforsure.screens.vaccinedetails.vaccineData

/* =========================================================
   DASHBOARD SCREEN
   ========================================================= */
@Composable
fun DashboardScreen(
    profileName: String,
    onNotificationsClick: () -> Unit,
    onScheduleClick: () -> Unit,   // existing
    navController: NavController   // ✅ ADDED ONLY
) {
    val context = LocalContext.current
    // Load user name from PreferenceManager
    val userName by remember {
        mutableStateOf(
            PreferenceManager.getUserName(context).ifEmpty { profileName }
        )
    }
    Scaffold(
        bottomBar = {
            DashboardBottomNavigation(
                onScheduleClick = onScheduleClick,
                navController = navController   // ✅ PASSED
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5FAFA)),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { DashboardHeader(userName, onNotificationsClick, navController) }
            item { Spacer(Modifier.height(28.dp)) }

            item {
                SectionHeader(
                    title = "Upcoming Vaccines",
                    action = "View All",
                    onActionClick = {
                        navController.navigate(Destinations.UPCOMING_VACCINES)
                    }
                )
            }

            item { UpcomingVaccinesRow(navController) }

            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(title = "Indian Immunization Schedule")
            }

            items(scheduleData) { group ->
                AgeGroupCard(group, navController)
            }

            item {
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Destinations.VACCINE_SCHEDULE)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00BFA5)
                        )
                    ) {
                        Text("View Full Schedule")
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate(Destinations.RECORDS)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color(0xFF00BFA5))
                    ) {
                        Text(
                            "Mark Vaccine Completed",
                            color = Color(0xFF00BFA5)
                        )
                    }
                }
            }
        }
    }
}

/* =========================================================
   HEADER
   ========================================================= */
@Composable
fun DashboardHeader(
    userName: String,
    onNotificationsClick: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF00BFA5), Color(0xFF00897B))
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(Destinations.PROFILE_DETAILS)
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.firstOrNull()?.toString() ?: "C",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(userName, color = Color.White, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Your child's schedule",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }

            Row {
                Icon(Icons.Default.DarkMode, null, tint = Color.White)
                Spacer(Modifier.width(12.dp))
                BadgedBox(badge = { Badge { Text("5") } }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.clickable { onNotificationsClick() }
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ProfileChip(userName, navController)
        }
    }
}

/* =========================================================
   UPCOMING VACCINES
   ========================================================= */
@Composable
fun UpcomingVaccinesRow(navController: NavController) {
    val vaccines = listOf("BCG", "Hepatitis B", "OPV", "DTaP")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vaccines) {
            UpcomingVaccineCard(it, navController)
        }
    }
}

@Composable
fun UpcomingVaccineCard(name: String, navController: NavController) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable {
                if (name == "BCG" || name == "Hepatitis B" || name == "Hepatitis B (1st dose)" || name == "Hepatitis B (2nd dose)" || name == "Hepatitis B (2nd)" || name == "OPV" || name == "OPV 0" || name == "DTaP" || name == "IPV" || name == "IPV (1st)" || name == "Hib" || name == "Hib (1st)" || name == "PCV" || name == "PCV (1st)" || name == "Rotavirus" || name == "Rotavirus (1st dose)") {
                    val vaccineName = when (name) {
                        "OPV" -> "OPV 0"
                        "DTaP" -> "DTaP (1st dose)"
                        "IPV" -> "IPV (1st dose)"
                        "IPV (1st)" -> "IPV (1st dose)"
                        "Hepatitis B (2nd)" -> "Hepatitis B (2nd dose)"
                        "Hib" -> "Hib (1st dose)"
                        "Hib (1st)" -> "Hib (1st dose)"
                        "PCV" -> "PCV (1st dose)"
                        "PCV (1st)" -> "PCV (1st dose)"
                        "Rotavirus" -> "Rotavirus (1st dose)"
                        else -> name
                    }
                    navController.navigate("${Destinations.VACCINE_DETAILS}/$vaccineName")
                }
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconCircle()
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(name, fontWeight = FontWeight.SemiBold)
                    Text("Pending", fontSize = 12.sp, color = Color(0xFF00796B))
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "Protects against multiple diseases",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/* =========================================================
   SCHEDULE
   ========================================================= */
@Composable
fun AgeGroupCard(group: ScheduleGroup, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, null, tint = Color(0xFF00BFA5))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(group.title, fontWeight = FontWeight.SemiBold)
                    Text("${group.items.size} vaccines", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(12.dp))

            group.items.forEach {
                ScheduleItem(it, navController)
            }
        }
    }
}

@Composable
fun ScheduleItem(name: String, navController: NavController) {
    // Get diseases prevented from vaccineData
    val diseasesPrevented = remember(name) {
        val vaccineName = when (name) {
            "DTaP (1st)" -> "DTaP (1st dose)"
            "DTaP (2nd)" -> "DTaP (2nd dose)"
            "DTaP (3rd)" -> "DTaP (3rd dose)"
            "IPV (1st)" -> "IPV (1st dose)"
            "IPV (2nd)" -> "IPV (2nd dose)"
            "IPV (3rd)" -> "IPV (3rd dose)"
            "IPV (Final)" -> "IPV (Final dose)"
            "Hepatitis B (2nd)" -> "Hepatitis B (2nd dose)"
            "Hepatitis B (3rd)" -> "Hepatitis B (3rd dose)"
            "Hib (1st)" -> "Hib (1st dose)"
            "Hib (2nd)" -> "Hib (2nd dose)"
            "Hib (3rd)" -> "Hib (3rd dose)"
            "PCV (1st)" -> "PCV (1st dose)"
            "PCV (2nd)" -> "PCV (2nd dose)"
            "PCV (3rd)" -> "PCV (3rd dose)"
            "PCV Booster" -> "PCV (Booster)"
            "Rotavirus" -> "Rotavirus (1st dose)"
            "Rotavirus (2nd)" -> "Rotavirus (2nd dose)"
            "Rotavirus (3rd)" -> "Rotavirus (3rd dose)"
            "MMR (1st)" -> "MMR (1st dose)"
            "MMR (2nd)" -> "MMR (2nd dose)"
            "MMR (3rd)" -> "MMR (3rd dose)"
            "Typhoid" -> "Typhoid Conjugate Vaccine"
            "Varicella (1st)" -> "Varicella (1st dose)"
            "Varicella (2nd)" -> "Varicella (2nd dose)"
            "Hepatitis A" -> "Hepatitis A (1st dose)"
            "HPV (1st)" -> "HPV (1st dose)"
            "HPV (2nd)" -> "HPV (2nd dose)"
            "Meningococcal" -> "Meningococcal Vaccine"
            "Meningococcal vaccine" -> "Meningococcal Vaccine"
            else -> name
        }
        vaccineData[vaccineName]?.diseasesPrevented 
            ?: vaccineData.values.firstOrNull { 
                it.name.contains(name.split(" ")[0], ignoreCase = true) ||
                name.contains(it.name.split(" ")[0], ignoreCase = true)
            }?.diseasesPrevented
            ?: "Vaccination pending"
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (name == "BCG" || name == "Hepatitis B (1st dose)" || name == "Hepatitis B (2nd dose)" || name == "Hepatitis B (2nd)" || name == "Hepatitis B (3rd)" || name == "OPV 0" || name == "DTaP (1st)" || name == "DTaP (2nd)" || name == "DTaP (3rd)" || name == "DTaP (1st Booster)" || name == "DTaP (2nd Booster)" || name == "Tdap" || name == "IPV (1st)" || name == "IPV (2nd)" || name == "IPV (3rd)" || name == "IPV (Booster)" || name == "IPV (Final)" || name == "IPV (Final dose)" || name == "Hib (1st)" || name == "Hib (2nd)" || name == "Hib (3rd)" || name == "PCV (1st)" || name == "PCV (2nd)" || name == "PCV (3rd)" || name == "PCV Booster" || name == "Rotavirus" || name == "Rotavirus (2nd)" || name == "Rotavirus (3rd)" || name == "MMR (1st)" || name == "MMR (2nd)" || name == "MMR (2nd dose)" || name == "MMR (3rd)" || name == "MMR (3rd dose)" || name == "Varicella (1st)" || name == "Varicella (1st dose)" || name == "Varicella (2nd)" || name == "Varicella (2nd dose)" || name == "Hepatitis A" || name == "Hepatitis A (1st dose)" || name == "Typhoid" || name == "HPV (1st)" || name == "HPV (1st dose)" || name == "HPV (2nd)" || name == "HPV (2nd dose)" || name == "Meningococcal" || name == "Meningococcal Vaccine" || name == "Meningococcal vaccine") {
                    val vaccineName = when (name) {
                        "DTaP (1st)" -> "DTaP (1st dose)"
                        "DTaP (2nd)" -> "DTaP (2nd dose)"
                        "DTaP (3rd)" -> "DTaP (3rd dose)"
                        "IPV (1st)" -> "IPV (1st dose)"
                        "IPV (2nd)" -> "IPV (2nd dose)"
                        "IPV (3rd)" -> "IPV (3rd dose)"
                        "IPV (Final)" -> "IPV (Final dose)"
                        "Hepatitis B (2nd)" -> "Hepatitis B (2nd dose)"
                        "Hepatitis B (3rd)" -> "Hepatitis B (3rd dose)"
                        "Hib (1st)" -> "Hib (1st dose)"
                        "Hib (2nd)" -> "Hib (2nd dose)"
                        "Hib (3rd)" -> "Hib (3rd dose)"
                        "PCV (1st)" -> "PCV (1st dose)"
                        "PCV (2nd)" -> "PCV (2nd dose)"
                        "PCV (3rd)" -> "PCV (3rd dose)"
                        "PCV Booster" -> "PCV (Booster)"
                        "Rotavirus" -> "Rotavirus (1st dose)"
                        "Rotavirus (2nd)" -> "Rotavirus (2nd dose)"
                        "Rotavirus (3rd)" -> "Rotavirus (3rd dose)"
                        "MMR (1st)" -> "MMR (1st dose)"
                        "MMR (2nd)" -> "MMR (2nd dose)"
                        "MMR (3rd)" -> "MMR (3rd dose)"
                        "Typhoid" -> "Typhoid Conjugate Vaccine"
                        "Varicella (1st)" -> "Varicella (1st dose)"
                        "Varicella (2nd)" -> "Varicella (2nd dose)"
                        "Hepatitis A" -> "Hepatitis A (1st dose)"
                        "HPV (1st)" -> "HPV (1st dose)"
                        "HPV (2nd)" -> "HPV (2nd dose)"
                        "Meningococcal" -> "Meningococcal Vaccine"
                        "Meningococcal vaccine" -> "Meningococcal Vaccine"
                        else -> name
                    }
                    navController.navigate("${Destinations.VACCINE_DETAILS}/$vaccineName")
                }
            }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconCircle()
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Medium)
            Text(
                diseasesPrevented,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        StatusBadge()
    }
}

/* =========================================================
   SMALL COMPONENTS
   ========================================================= */
@Composable
fun IconCircle() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(Color(0xFFE0F2F1), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.MedicalServices,
            null,
            tint = Color(0xFF00BFA5),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun StatusBadge() {
    Box(
        modifier = Modifier
            .background(Color(0xFF1E3A3A), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text("Pending", fontSize = 12.sp, color = Color.White)
    }
}

@Composable
fun ProfileChip(text: String, navController: NavController) {
    Box(
        modifier = Modifier
            .clickable {
                navController.navigate(Destinations.PROFILE_DETAILS)
            }
            .background(Color.White, RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, color = Color(0xFF00897B), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

/* =========================================================
   SECTION HEADER
   ========================================================= */
@Composable
fun SectionHeader(title: String, action: String? = null, onActionClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        action?.let {
            Text(
                it,
                color = Color(0xFF00BFA5),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    onActionClick?.invoke()
                }
            )
        }
    }
}

/* =========================================================
   BOTTOM NAV (REMINDER NAVIGATION ADDED)
   ========================================================= */
@Composable
fun DashboardBottomNavigation(
    onScheduleClick: () -> Unit,
    navController: NavController
) {
    NavigationBar {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = onScheduleClick,
            icon = { Icon(Icons.Default.DateRange, null) },
            label = { Text("Schedule") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Destinations.RECORDS) },
            icon = { Icon(Icons.Default.Description, null) },
            label = { Text("Records") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Destinations.REMINDER_SETTINGS) }, // ✅ ONLY ADDED LINE
            icon = { Icon(Icons.Default.Notifications, null) },
            label = { Text("Reminders") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Destinations.PROFILE_DETAILS) },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}

/* =========================================================
   DATA
   ========================================================= */
data class ScheduleGroup(val title: String, val items: List<String>)

val scheduleData = listOf(
    ScheduleGroup("At Birth", listOf("BCG", "Hepatitis B (1st dose)", "OPV 0")),
    ScheduleGroup("6 Weeks", listOf("DTaP (1st)", "IPV (1st)", "Hib (1st)", "PCV (1st)", "Rotavirus")),
    ScheduleGroup("10 Weeks", listOf("DTaP (2nd)", "IPV (2nd)", "Hib (2nd)", "PCV (2nd)", "Rotavirus (2nd)")),
    ScheduleGroup("14 Weeks", listOf("DTaP (3rd)", "IPV (3rd)", "Hepatitis B (3rd)", "Hib (3rd)", "PCV (3rd)", "Rotavirus (3rd)")),
    ScheduleGroup("9–12 Months", listOf("MMR (1st)", "PCV Booster", "Typhoid")),
    ScheduleGroup("16–24 Months", listOf("DTaP (1st Booster)", "IPV (Booster)", "MMR (2nd)", "Varicella (1st)", "Hepatitis A")),
    ScheduleGroup("5–6 Years", listOf("DTaP (2nd Booster)", "MMR (3rd)", "Varicella (2nd)", "IPV (Final)")),
    ScheduleGroup("10 Years", listOf("Tdap")),
    ScheduleGroup("12+ Years", listOf("HPV (1st)", "HPV (2nd)", "Meningococcal"))
)
