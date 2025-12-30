package com.example.vaxforsure.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector   // ✅ REQUIRED IMPORT
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.api.RetrofitClient
import com.example.vaxforsure.models.RegisterRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onCreateAccount: () -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        /* Top Bar */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Create Account",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        /* Header */
        Text(
            text = "Join VaxForSure",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Create your account to start managing your child's vaccinations",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* Full Name */
        Label("Full Name")
        InputField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Enter your full name",
            icon = Icons.Filled.Person
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Email */
        Label("Email Address")
        InputField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter your email",
            icon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Phone */
        Label("Phone Number")
        InputField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = "Enter your phone number",
            icon = Icons.Filled.Phone,
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Password */
        Label("Password")
        PasswordField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Create a password",
            showPassword = showPassword,
            onToggle = { showPassword = !showPassword }
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Confirm Password */
        Label("Confirm Password")
        PasswordField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirm your password",
            showPassword = showConfirmPassword,
            onToggle = { showConfirmPassword = !showConfirmPassword }
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* Terms */
        Text(
            text = buildAnnotatedString {
                append("By creating an account, you agree to our ")
                withStyle(SpanStyle(color = Color(0xFF4DB6AC))) {
                    append("Terms of Service")
                }
                append(" and ")
                withStyle(SpanStyle(color = Color(0xFF4DB6AC))) {
                    append("Privacy Policy")
                }
            },
            fontSize = 12.sp,
            color = Color(0xFF9E9E9E)
        )

        Spacer(modifier = Modifier.height(24.dp))

        /* Create Account Button */
        Button(
            onClick = {
                if (fullName.isBlank() || email.isBlank() || phone.isBlank() || 
                    password.isBlank() || confirmPassword.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                
                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                
                if (password.length < 6) {
                    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                
                isLoading = true
                
                // Create registration request
                val registerRequest = RegisterRequest(
                    fullName = fullName.trim(),
                    email = email.trim(),
                    phone = phone.trim(),
                    password = password
                )
                
                // Call registration API
                RetrofitClient.apiService.register(registerRequest)
                    .enqueue(object : Callback<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>> {
                        override fun onResponse(
                            call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>,
                            response: Response<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>
                        ) {
                            isLoading = false
                            
                            if (response.isSuccessful && response.body() != null) {
                                val apiResponse = response.body()!!
                                
                                if (apiResponse.success && apiResponse.data != null) {
                                    val user = apiResponse.data.user
                                    
                                    // Save user session locally
                                    PreferenceManager.saveUserSession(
                                        context,
                                        user.id,
                                        user.fullName,
                                        user.email,
                                        user.phone,
                                        "",
                                        user.emailVerified
                                    )
                                    
                                    Toast.makeText(
                                        context,
                                        apiResponse.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onCreateAccount()
                                } else {
                                    // Clean error message from API
                                    val cleanMessage = apiResponse.message
                                        .replace(Regex("<[^>]*>"), "")
                                        .replace("<br/>", " ")
                                        .replace("<br>", " ")
                                        .trim()
                                    
                                    Toast.makeText(
                                        context,
                                        cleanMessage.ifEmpty { "Registration failed. Please try again." },
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                // Try to parse error message from error body
                                val errorMessage = try {
                                    val errorBody = response.errorBody()?.string() ?: ""
                                    
                                    // Try to parse as JSON first
                                    try {
                                        val jsonObject = org.json.JSONObject(errorBody)
                                        val message = jsonObject.optString("message", "")
                                        message.replace(Regex("<[^>]*>"), "")
                                            .replace("<br/>", " ")
                                            .replace("<br>", " ")
                                            .trim()
                                    } catch (e: org.json.JSONException) {
                                        // If not JSON, clean HTML tags
                                        errorBody
                                            .replace(Regex("<[^>]*>"), "")
                                            .replace("<br/>", " ")
                                            .replace("<br>", " ")
                                            .replace("&nbsp;", " ")
                                            .trim()
                                    }
                                } catch (e: Exception) {
                                    "Registration failed. Please try again."
                                }
                                
                                Toast.makeText(
                                    context,
                                    errorMessage.ifEmpty { "Registration failed. Please try again." },
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        
                        override fun onFailure(
                            call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>,
                            t: Throwable
                        ) {
                            isLoading = false
                            
                            // Detailed error handling with specific solutions
                            val errorMessage = when {
                                t.message?.contains("Failed to connect", ignoreCase = true) == true -> {
                                    "Cannot connect to backend server!\n\n" +
                                    "Please check:\n" +
                                    "1. XAMPP Apache is running (GREEN)\n" +
                                    "2. Device and computer on SAME WiFi\n" +
                                    "3. Test in browser: http://10.148.199.69:8080/vaxforsure/api/auth/login.php\n" +
                                    "4. Check Windows Firewall settings"
                                }
                                t.message?.contains("timeout", ignoreCase = true) == true -> {
                                    "⏱️ Connection timeout!\n\n" +
                                    "Check:\n" +
                                    "1. XAMPP Apache running\n" +
                                    "2. Same WiFi network\n" +
                                    "3. Firewall not blocking"
                                }
                                t.message?.contains("Unable to resolve host", ignoreCase = true) == true -> {
                                    "🌐 Cannot find server!\n\n" +
                                    "Check:\n" +
                                    "1. Device on same WiFi\n" +
                                    "2. Computer IP: 10.148.199.69\n" +
                                    "3. Test URL in browser first"
                                }
                                t.message?.contains("Connection refused", ignoreCase = true) == true -> {
                                    "🚫 Connection refused!\n\n" +
                                    "XAMPP Apache is NOT running!\n" +
                                    "Start Apache in XAMPP Control Panel."
                                }
                                else -> {
                                    "❌ Connection Error\n\n" +
                                    "Error: ${t.message}\n\n" +
                                    "Quick Fix:\n" +
                                    "1. Start XAMPP Apache\n" +
                                    "2. Same WiFi network\n" +
                                    "3. Test in browser first"
                                }
                            }
                            
                            Toast.makeText(
                                context,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4DB6AC)
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            } else {
                Text("Create Account", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        /* Sign In */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Already have an account? ", color = Color(0xFF757575))
            Text(
                text = "Sign In",
                color = Color(0xFF4DB6AC),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSignIn() }
            )
        }
    }
}

/* ---------- Reusable Components ---------- */

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF757575)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(icon, null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showPassword: Boolean,
    onToggle: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(Icons.Filled.Lock, null) },
        trailingIcon = {
            Icon(
                imageVector =
                    if (showPassword) Icons.Filled.VisibilityOff
                    else Icons.Filled.Visibility,
                contentDescription = "Toggle Password",
                modifier = Modifier.clickable { onToggle() }
            )
        },
        visualTransformation =
            if (showPassword)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    )
}
