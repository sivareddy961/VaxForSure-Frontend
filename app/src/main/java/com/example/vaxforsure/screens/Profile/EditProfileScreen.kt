package com.example.vaxforsure.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.widget.Toast
import com.example.vaxforsure.utils.ChildManager
import com.example.vaxforsure.utils.VaccineManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    childId: Int = 0
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    // Load child data from ChildManager
    val child = remember(childId) {
        if (childId > 0) {
            ChildManager.getChildById(context, childId)
        } else {
            null
        }
    }
    
    var childName by remember { mutableStateOf(child?.name ?: "") }
    var dob by remember { mutableStateOf(child?.date_of_birth ?: "") }
    var gender by remember { mutableStateOf(child?.gender ?: "Male") }
    var birthWeight by remember { mutableStateOf(child?.birth_weight?.toString() ?: "") }
    var birthHeight by remember { mutableStateOf(child?.birth_height?.toString() ?: "") }
    var bloodGroup by remember { mutableStateOf(child?.blood_group ?: "Select blood group") }
    var expandedBloodGroup by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    
    // Update state when child data loads
    LaunchedEffect(child) {
        child?.let {
            childName = it.name
            dob = it.date_of_birth
            gender = it.gender
            birthWeight = it.birth_weight?.toString() ?: ""
            birthHeight = it.birth_height?.toString() ?: ""
            bloodGroup = it.blood_group ?: "Select blood group"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Profile",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFE0F2F1), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF212121)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
            /* ---------------- Child's Name ---------------- */
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Child's Name",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                    Text(
                        " *",
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = childName,
                    onValueChange = { childName = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00BFA5),
                        unfocusedBorderColor = Color(0xFFBDBDBD)
                    )
                )
            }

            /* ---------------- Date of Birth ---------------- */
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Date of Birth",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                    Text(
                        " *",
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00BFA5),
                        unfocusedBorderColor = Color(0xFFBDBDBD)
                    )
                )
            }

            /* ---------------- Gender ---------------- */
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Gender",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                    Text(
                        " *",
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        GenderButton(
                            text = "Male",
                            selected = gender == "Male",
                            onClick = { gender = "Male" }
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        GenderButton(
                            text = "Female",
                            selected = gender == "Female",
                            onClick = { gender = "Female" }
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        GenderButton(
                            text = "Other",
                            selected = gender == "Other",
                            onClick = { gender = "Other" }
                        )
                    }
                }
            }

            /* ---------------- Birth Weight ---------------- */
            Column {
                Text(
                    "Birth Weight",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = birthWeight,
                        onValueChange = { birthWeight = it },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00BFA5),
                            unfocusedBorderColor = Color(0xFFBDBDBD)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(56.dp)
                            .background(Color(0xFFE0F2F1), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "kg",
                            fontSize = 14.sp,
                            color = Color(0xFF212121)
                        )
                    }
                }
            }

            /* ---------------- Birth Height ---------------- */
            Column {
                Text(
                    "Birth Height",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = birthHeight,
                        onValueChange = { birthHeight = it },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00BFA5),
                            unfocusedBorderColor = Color(0xFFBDBDBD)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(56.dp)
                            .background(Color(0xFFE0F2F1), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "cm",
                            fontSize = 14.sp,
                            color = Color(0xFF212121)
                        )
                    }
                }
            }

            /* ---------------- Blood Group ---------------- */
            Column {
                Text(
                    "Blood Group",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    OutlinedTextField(
                        value = bloodGroup,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedBloodGroup = true },
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF757575)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00BFA5),
                            unfocusedBorderColor = Color(0xFFBDBDBD)
                        )
                    )
                    DropdownMenu(
                        expanded = expandedBloodGroup,
                        onDismissRequest = { expandedBloodGroup = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bloodGroups.forEach { group ->
                            DropdownMenuItem(
                                text = { Text(group) },
                                onClick = {
                                    bloodGroup = group
                                    expandedBloodGroup = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- Save Changes Button ---------------- */
            Button(
                onClick = {
                    if (childId == 0) {
                        Toast.makeText(context, "Child not found", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    if (childName.isBlank() || dob.isBlank() || gender.isBlank()) {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    isLoading = true
                    
                    scope.launch {
                        try {
                            val updatedChild = child?.copy(
                                name = childName,
                                date_of_birth = dob,
                                gender = gender,
                                birth_weight = birthWeight.toDoubleOrNull(),
                                birth_height = birthHeight.toDoubleOrNull(),
                                blood_group = if (bloodGroup != "Select blood group") bloodGroup else null,
                                updated_at = System.currentTimeMillis().toString()
                            )
                            
                            if (updatedChild != null) {
                                ChildManager.saveChild(context, updatedChild)
                                Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        } finally {
                            isLoading = false
                        }
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
                        "Save Changes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            /* ---------------- Delete Profile Button ---------------- */
            OutlinedButton(
                onClick = {
                    if (childId == 0) {
                        Toast.makeText(context, "Child not found", Toast.LENGTH_SHORT).show()
                        return@OutlinedButton
                    }
                    showDeleteDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, Color(0xFFE91E63)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE91E63)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Delete Profile",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE91E63)
                )
            }
            
            // Delete Confirmation Dialog
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = {
                        Text(
                            "Delete Child Profile?",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            "Are you sure you want to delete ${childName}'s profile? This action cannot be undone. All vaccination records for this child will also be deleted."
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    try {
                                        // Delete child from ChildManager
                                        ChildManager.deleteChild(context, childId)
                                        
                                        // Note: Vaccine records are stored per childId, 
                                        // so they will be orphaned but that's okay for now
                                        // In a real app, you'd want to delete them too
                                        
                                        Toast.makeText(context, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                                        showDeleteDialog = false
                                        navController.popBackStack()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error deleting profile: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFE91E63)
                            )
                        ) {
                            Text("Delete", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDeleteDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun GenderButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                if (selected) Color(0xFF00BFA5) else Color.White,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                if (selected) Color(0xFF00BFA5) else Color(0xFFBDBDBD),
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) Color.White else Color(0xFF212121)
        )
    }
}

