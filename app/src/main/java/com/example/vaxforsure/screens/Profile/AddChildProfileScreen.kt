package com.example.vaxforsure.screens.onboarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.utils.ChildManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun AddChildProfileScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current

    var childName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    val userId = PreferenceManager.getUserId(context)

    fun saveAndContinue() {
        if (childName.isBlank() || dob.isBlank() || gender.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (userId == 0) {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }
        
        isLoading = true
        
        // Convert date format if needed (dd-mm-yyyy to yyyy-mm-dd)
        val dateParts = dob.split("-")
        val formattedDob = if (dateParts.size == 3) {
            "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}"
        } else {
            dob
        }
        
        // Simulate adding child (local only)
        scope.launch {
            delay(1000) // Simulate network delay
            isLoading = false
            
            // Generate new child ID
            val existingChildren = ChildManager.getAllChildren(context)
            val newChildId = (existingChildren.maxOfOrNull { it.id } ?: 0) + 1
            
            // Save to temp SharedPreferences for next screen
            val pref = context.getSharedPreferences("temp_child", Context.MODE_PRIVATE)
            pref.edit()
                .putString("name", childName)
                .putString("dob", dob)
                .putString("gender", gender)
                .putInt("child_id", newChildId)
                .remove("birthWeight") // Clear previous health details
                .remove("birthHeight")
                .remove("bloodGroup")
                .apply()
            
            Toast.makeText(context, "Child profile added!", Toast.LENGTH_SHORT).show()
            onNext()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {

        /* Top Bar */
        Row(
            modifier = Modifier.padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Add Child Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        /* Icon */
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ChildCare,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* Title */
        Text(
            text = "Child Information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Let’s start by adding your child’s basic details",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* Child Name */
        Label(text = "Child’s Name *")
        OutlinedTextField(
            value = childName,
            onValueChange = { childName = it },
            placeholder = { Text("Enter child’s name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Date of Birth */
        Label(text = "Date of Birth *")
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            placeholder = { Text("dd-mm-yyyy") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Gender */
        Label(text = "Gender *")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            GenderOption("Male", gender == "Male") { gender = "Male" }
            GenderOption("Female", gender == "Female") { gender = "Female" }
            GenderOption("Other", gender == "Other") { gender = "Other" }
        }

        Spacer(modifier = Modifier.height(40.dp))

        /* Continue Button */
        Button(
            onClick = { saveAndContinue() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ),
            enabled = childName.isNotBlank() && dob.isNotBlank() && gender.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Continue to Health Details",
                    fontSize = 16.sp
                )
            }
        }
    }
}

/* ---------- Reusable Components ---------- */

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun GenderOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                if (selected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else
                    MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}
