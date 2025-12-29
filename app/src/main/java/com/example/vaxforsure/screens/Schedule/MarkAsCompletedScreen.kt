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
import android.content.Context
import com.example.vaxforsure.screens.vaccinedetails.vaccineData
import com.example.vaxforsure.screens.vaccinedetails.VaccineInfo
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.vaxforsure.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

/* =========================================================
   Mark as Completed Screen
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkAsCompletedScreen(
    navController: NavController,
    vaccineName: String
) {
    val context = LocalContext.current
    val vaccineInfo: VaccineInfo = vaccineData[vaccineName] ?: vaccineData["BCG"]!!
    
    var dateAdministered by remember { mutableStateOf("") }
    var healthcareFacility by remember { mutableStateOf("") }
    var batchLotNumber by remember { mutableStateOf("") }
    var additionalNotes by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var childId by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    
    // Get child ID from SharedPreferences (you may need to pass this from previous screen)
    LaunchedEffect(Unit) {
        val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
        // For now, we'll need to get the first child or pass childId as parameter
        // This is a simplified version - you may want to improve this
        childId = 1 // Default, should be passed from previous screen
    }

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
                    if (dateAdministered.isBlank() || healthcareFacility.isBlank()) {
                        Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    if (childId == 0) {
                        Toast.makeText(context, "Child not selected", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    isLoading = true
                    
                    // Simulate saving vaccination (local only)
                    scope.launch {
                        delay(1000) // Simulate network delay
                        isLoading = false
                        
                        Toast.makeText(context, "Vaccination record saved!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BFA5)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Save Record",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

