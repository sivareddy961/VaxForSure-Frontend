package com.example.vaxforsure.screens.records

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/* -------------------- DATA MODELS -------------------- */
data class Child(
    val id: String,
    val name: String
)

data class VaccinationRecord(
    val id: String,
    val vaccineName: String,
    val dateAdministered: Long,
    val facility: String,
    val ageGroup: String
)

/* -------------------- MAIN SCREEN -------------------- */
@Composable
fun RecordsScreen(
    navController: NavController,
    selectedChild: Child?,
    childRecords: List<VaccinationRecord>
) {
    var filterType by remember { mutableStateOf("all") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FBFB))
    ) {

        /* ================= HEADER ================= */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF63C7A4),
                            Color(0xFF4FB392)
                        )
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                /* -------- TOP ROW (BACK ONLY) -------- */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                /* -------- TITLE + DOWNLOAD (MOVED DOWN) -------- */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vaccination Records",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        onClick = { navController.navigate("export-records") },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Export",
                            tint = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                /* -------- RECORDS FOR CARD -------- */
                if (selectedChild != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White.copy(alpha = 0.35f),
                                RoundedCornerShape(18.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Records for",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                        Text(
                            text = selectedChild.name,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= FILTER CHIPS ================= */
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("All", "Recent").forEach { label ->
                val selected = filterType == label.lowercase()

                Box(
                    modifier = Modifier
                        .background(
                            if (selected) Color(0xFF4DB6AC) else Color.Transparent,
                            RoundedCornerShape(50)
                        )
                        .border(
                            if (selected) BorderStroke(0.dp, Color.Transparent)
                            else BorderStroke(1.dp, Color(0xFFCCE6E2)),
                            RoundedCornerShape(50)
                        )
                        .clickable { filterType = label.lowercase() }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = label,
                        color = if (selected) Color.White else Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ================= EMPTY STATE ================= */
        if (childRecords.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .border(
                        BorderStroke(1.dp, Color(0xFFD6EDEA)),
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF90A4AE),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "No vaccination records yet",
                        color = Color(0xFF607D8B)
                    )
                }
            }
        }
    }
}
