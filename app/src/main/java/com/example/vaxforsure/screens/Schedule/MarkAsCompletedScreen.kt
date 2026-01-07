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
import com.example.vaxforsure.utils.ChildManager
import com.example.vaxforsure.utils.VaccineManager
import com.example.vaxforsure.api.RetrofitClient
import com.example.vaxforsure.models.MarkVaccineCompletedRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson

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
    var children by remember { mutableStateOf<List<com.example.vaxforsure.screens.profile.Child>>(emptyList()) }
    var selectedChildIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    
    val userId = remember { PreferenceManager.getUserId(context) }
    
    // Get children and select first one by default (filtered by current user)
    LaunchedEffect(Unit) {
        children = ChildManager.getAllChildren(context, userId)
        if (children.isNotEmpty()) {
            childId = children[selectedChildIndex].id
        }
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

            /* ---------------- Child Selection ---------------- */
            if (children.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Select Child",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1976D2)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        children.forEachIndexed { index, child ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedChildIndex = index
                                        childId = child.id
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedChildIndex == index,
                                    onClick = {
                                        selectedChildIndex = index
                                        childId = child.id
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        child.name,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "DOB: ${child.date_of_birth}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

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
                    
                    // Convert date format if needed (dd-mm-yyyy to yyyy-mm-dd)
                    val dateParts = dateAdministered.split("-")
                    val formattedDate = if (dateParts.size == 3) {
                        "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}"
                    } else {
                        dateAdministered
                    }
                    
                    // Create API request
                    val markCompletedRequest = MarkVaccineCompletedRequest(
                        childId = childId,
                        vaccineName = vaccineName,
                        dateAdministered = formattedDate,
                        healthcareFacility = healthcareFacility.trim(),
                        batchLotNumber = batchLotNumber.takeIf { it.isNotBlank() },
                        notes = additionalNotes.takeIf { it.isNotBlank() }
                    )
                    
                    // Call backend API
                    RetrofitClient.apiService.markVaccineCompleted(markCompletedRequest)
                        .enqueue(object : Callback<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.MarkVaccineCompletedResponse>> {
                            override fun onResponse(
                                call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.MarkVaccineCompletedResponse>>,
                                response: Response<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.MarkVaccineCompletedResponse>>
                            ) {
                                isLoading = false
                                
                                if (response.isSuccessful && response.body() != null) {
                                    val apiResponse = response.body()!!
                                    
                                    if (apiResponse.success && apiResponse.data != null) {
                                        // Also save locally for offline access
                                        VaccineManager.markVaccineCompleted(
                                            context = context,
                                            childId = childId,
                                            vaccineName = vaccineName,
                                            dateAdministered = dateAdministered,
                                            healthcareFacility = healthcareFacility,
                                            batchLotNumber = batchLotNumber,
                                            notes = additionalNotes
                                        )
                                        
                                        val childName = children.getOrNull(selectedChildIndex)?.name ?: "child"
                                        Toast.makeText(context, "Vaccination record saved for $childName!", Toast.LENGTH_SHORT).show()
                                        
                                        // Navigate to Records screen after saving
                                        navController.navigate(com.example.vaxforsure.navigation.Destinations.RECORDS) {
                                            popUpTo(com.example.vaxforsure.navigation.Destinations.DASHBOARD) { inclusive = false }
                                        }
                                    } else {
                                        Toast.makeText(context, apiResponse.message ?: "Failed to save vaccination", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    val errorMessage = try {
                                        val errorBody = response.errorBody()?.string() ?: ""
                                        val jsonError = com.google.gson.Gson().fromJson(errorBody, com.example.vaxforsure.models.ApiResponse::class.java)
                                        jsonError.message.replace(Regex("<[^>]*>"), "").trim()
                                            .ifEmpty { "Failed to save vaccination. Please try again." }
                                    } catch (e: Exception) {
                                        "Failed to save vaccination. Please try again."
                                    }
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                            
                            override fun onFailure(
                                call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.MarkVaccineCompletedResponse>>,
                                t: Throwable
                            ) {
                                isLoading = false
                                
                                val errorMessage = when {
                                    t.message?.contains("Failed to connect", ignoreCase = true) == true -> {
                                        "Cannot connect to backend server. Please check XAMPP Apache is running."
                                    }
                                    t.message?.contains("timeout", ignoreCase = true) == true -> {
                                        "Connection timeout. Please check your internet connection."
                                    }
                                    t.message?.contains("JsonReader", ignoreCase = true) == true -> {
                                        "Server response error. Please try again."
                                    }
                                    t.message?.contains("Unable to resolve host", ignoreCase = true) == true -> {
                                        "Cannot reach server. Check your network connection."
                                    }
                                    else -> {
                                        "Network error: ${t.message ?: "Unknown error"}"
                                    }
                                }
                                
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        })
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

