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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    
    // Load existing data from SharedPreferences
    val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
    val savedName = pref.getString("name", "") ?: ""
    val savedDob = pref.getString("dob", "") ?: ""
    val savedGender = pref.getString("gender", "") ?: ""
    
    var childName by remember { mutableStateOf(savedName.ifEmpty { "siva" }) }
    var dob by remember { mutableStateOf(savedDob.ifEmpty { "21-10-2003" }) }
    var gender by remember { mutableStateOf(savedGender.ifEmpty { "Male" }) }
    var birthWeight by remember { mutableStateOf("3.5") }
    var birthHeight by remember { mutableStateOf("50") }
    var bloodGroup by remember { mutableStateOf("Select blood group") }
    var expandedBloodGroup by remember { mutableStateOf(false) }
    
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

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
                    // Save to SharedPreferences
                    pref.edit()
                        .putString("name", childName)
                        .putString("dob", dob)
                        .putString("gender", gender)
                        .putString("birthWeight", birthWeight)
                        .putString("birthHeight", birthHeight)
                        .putString("bloodGroup", bloodGroup)
                        .apply()
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
                    "Save Changes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            /* ---------------- Delete Profile Button ---------------- */
            OutlinedButton(
                onClick = {
                    // TODO: Handle delete profile
                    navController.popBackStack()
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

