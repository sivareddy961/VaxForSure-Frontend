package com.example.vaxforsure.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vaxforsure.screens.vaccinedetails.vaccineData
import com.example.vaxforsure.screens.vaccinedetails.VaccineInfo

/* =========================================================
   Mark as Completed Screen
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkAsCompletedScreen(
    navController: NavController,
    vaccineName: String
) {
    val vaccineInfo: VaccineInfo = vaccineData[vaccineName] ?: vaccineData["BCG"]!!
    
    var dateAdministered by remember { mutableStateOf("27-12-2025") }
    var healthcareFacility by remember { mutableStateOf("") }
    var batchLotNumber by remember { mutableStateOf("") }
    var additionalNotes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mark as Completed",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE0F2F1)
                )
            )
        },
        containerColor = Color(0xFFF5FAFA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            /* ---------------- Vaccine Info Card ---------------- */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0F2F1)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = vaccineInfo.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${vaccineInfo.fullName} • ${vaccineInfo.ageGroup}",
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            /* ---------------- Date Administered ---------------- */
            OutlinedTextField(
                value = dateAdministered,
                onValueChange = { dateAdministered = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Date Administered")
                        Text(
                            text = "*",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color(0xFF00BFA5)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BFA5),
                    unfocusedBorderColor = Color(0xFFBDBDBD)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            /* ---------------- Healthcare Facility ---------------- */
            OutlinedTextField(
                value = healthcareFacility,
                onValueChange = { healthcareFacility = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Healthcare Facility")
                        Text(
                            text = "*",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                },
                placeholder = {
                    Text("e.g., City Health Center")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF00BFA5)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BFA5),
                    unfocusedBorderColor = Color(0xFFBDBDBD)
                )
            )

            /* ---------------- Batch/Lot Number (Optional) ---------------- */
            OutlinedTextField(
                value = batchLotNumber,
                onValueChange = { batchLotNumber = it },
                label = {
                    Text("Batch/Lot Number (Optional)")
                },
                placeholder = {
                    Text("Enter batch number")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF00BFA5)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BFA5),
                    unfocusedBorderColor = Color(0xFFBDBDBD)
                )
            )

            /* ---------------- Additional Notes (Optional) ---------------- */
            OutlinedTextField(
                value = additionalNotes,
                onValueChange = { additionalNotes = it },
                label = {
                    Text("Additional Notes (Optional)")
                },
                placeholder = {
                    Text("Any side effects or observations...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Note,
                        contentDescription = null,
                        tint = Color(0xFF00BFA5),
                        modifier = Modifier.padding(top = 48.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BFA5),
                    unfocusedBorderColor = Color(0xFFBDBDBD)
                ),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- Save Record Button ---------------- */
            Button(
                onClick = {
                    // TODO: Save the record
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BFA5)
                )
            ) {
                Text(
                    text = "Save Record",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

