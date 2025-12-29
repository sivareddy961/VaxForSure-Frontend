package com.example.vaxforsure.screens.onboarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.vaxforsure.utils.ChildManager
import com.example.vaxforsure.screens.profile.Child
import com.example.vaxforsure.utils.PreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthDetailsScreen(
    onBack: () -> Unit,
    onSkip: () -> Unit,
    onSubmit: () -> Unit
) {
    val context = LocalContext.current
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var bloodGroup by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    var childName by remember { mutableStateOf("") }
    var childId by remember { mutableStateOf(0) }
    
    LaunchedEffect(Unit) {
        val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
        childName = pref.getString("name", "") ?: ""
        childId = pref.getInt("child_id", 0)
    }

    val bloodGroups = listOf(
        "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"
    )

    Scaffold(
        containerColor = Color(0xFFF8FCFB),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Health Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = {
                    // Save child even when skipping health details
                    val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
                    val childName = pref.getString("name", "") ?: ""
                    val childDob = pref.getString("dob", "") ?: ""
                    val childGender = pref.getString("gender", "") ?: ""
                    val userId = PreferenceManager.getUserId(context)
                    
                    if (childName.isNotEmpty()) {
                        val child = Child(
                            id = pref.getInt("child_id", 0).takeIf { it > 0 } ?: (ChildManager.getAllChildren(context).maxOfOrNull { it.id } ?: 0) + 1,
                            user_id = userId,
                            name = childName,
                            date_of_birth = childDob,
                            gender = childGender,
                            birth_weight = null,
                            birth_height = null,
                            blood_group = null,
                            created_at = System.currentTimeMillis().toString(),
                            updated_at = System.currentTimeMillis().toString()
                        )
                        
                        ChildManager.saveChild(context, child)
                    }
                    onSkip()
                }) {
                    Text(
                        text = "Skip",
                        color = Color(0xFF4FB6A5)
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Additional Health Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "These details help us provide better health tracking (Optional)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- Birth Weight ---------------- */
            Text("Birth Weight", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                UnitBox("kg")
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------------- Birth Height ---------------- */
            Text("Birth Height", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                UnitBox("cm")
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------------- Blood Group ---------------- */
            Text("Blood Group", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (bloodGroup.isEmpty()) "Select blood group" else bloodGroup,
                        color = if (bloodGroup.isEmpty()) Color.Gray else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    bloodGroups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group) },
                            onClick = {
                                bloodGroup = group
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------------- Complete Profile Button ---------------- */
            Button(
                onClick = {
                    if (childName.isBlank()) {
                        Toast.makeText(context, "Child information missing", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    isLoading = true
                    
                    // Simulate saving health details (local only)
                    scope.launch {
                        delay(1000) // Simulate network delay
                        isLoading = false
                        
                        // Get child data from temp SharedPreferences
                        val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
                        val childName = pref.getString("name", "") ?: ""
                        val childDob = pref.getString("dob", "") ?: ""
                        val childGender = pref.getString("gender", "") ?: ""
                        val userId = PreferenceManager.getUserId(context)
                        
                        // Save health details to temp SharedPreferences
                        pref.edit()
                            .putString("birthWeight", weight)
                            .putString("birthHeight", height)
                            .putString("bloodGroup", bloodGroup)
                            .apply()
                        
                        // Save complete child to children list
                        if (childName.isNotEmpty()) {
                            val birthWeightDouble = weight.toDoubleOrNull()
                            val birthHeightDouble = height.toDoubleOrNull()
                            
                            val child = Child(
                                id = pref.getInt("child_id", 0).takeIf { it > 0 } ?: (ChildManager.getAllChildren(context).maxOfOrNull { it.id } ?: 0) + 1,
                                user_id = userId,
                                name = childName,
                                date_of_birth = childDob,
                                gender = childGender,
                                birth_weight = birthWeightDouble,
                                birth_height = birthHeightDouble,
                                blood_group = bloodGroup.takeIf { it.isNotEmpty() },
                                created_at = System.currentTimeMillis().toString(),
                                updated_at = System.currentTimeMillis().toString()
                            )
                            
                            ChildManager.saveChild(context, child)
                        }
                        
                        Toast.makeText(context, "Health details saved!", Toast.LENGTH_SHORT).show()
                        onSubmit()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4FB6A5)
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
                        text = "Complete Profile",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/* ---------------- Unit Pill (kg / cm) ---------------- */
@Composable
private fun UnitBox(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFEAF6F3),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4FB6A5)
        )
    }
}
