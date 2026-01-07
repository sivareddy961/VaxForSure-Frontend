package com.example.vaxforsure.screens.onboarding

import androidx.compose.foundation.background   // ✅ REQUIRED
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
import android.widget.Toast
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.api.RetrofitClient
import com.example.vaxforsure.models.ForgotPasswordRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onResetConfirmation: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun handleSubmit() {
        if (email.isBlank()) {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }
        
        isLoading = true
        
        // Call backend API to send OTP
        val forgotPasswordRequest = ForgotPasswordRequest(email.trim())
        
        RetrofitClient.apiService.forgotPassword(forgotPasswordRequest)
            .enqueue(object : Callback<com.example.vaxforsure.models.ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<com.example.vaxforsure.models.ApiResponse<Any>>,
                    response: Response<com.example.vaxforsure.models.ApiResponse<Any>>
                ) {
                    isLoading = false
                    
                    if (response.isSuccessful && response.body() != null) {
                        val apiResponse = response.body()!!
                        
                        if (apiResponse.success) {
                            // Save email for OTP verification screen (OTP will come from email)
                            PreferenceManager.saveOtpData(
                                context,
                                email.trim(),
                                "", // OTP will be entered by user from email
                                "password_reset"
                            )
                            
                            Toast.makeText(
                                context,
                                apiResponse.message ?: "Verification code sent to your email. Please check your inbox.",
                                Toast.LENGTH_LONG
                            ).show()
                            
                            onResetConfirmation()
                        } else {
                            Toast.makeText(
                                context,
                                apiResponse.message ?: "Failed to send verification code",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Better error handling
                        val errorMessage = try {
                            val errorBody = response.errorBody()?.string() ?: ""
                            if (errorBody.isNotEmpty()) {
                                val jsonError = com.google.gson.Gson().fromJson(errorBody, com.example.vaxforsure.models.ApiResponse::class.java)
                                jsonError.message.replace(Regex("<[^>]*>"), "").trim()
                                    .ifEmpty { "Server error (${response.code()}). Please try again." }
                            } else {
                                "Server error (${response.code()}). Please check if backend is running."
                            }
                        } catch (e: Exception) {
                            "Network error: ${response.code()}. Check: 1) Backend is running 2) Correct IP address in ApiConstants 3) Internet connection"
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                
                override fun onFailure(
                    call: Call<com.example.vaxforsure.models.ApiResponse<Any>>,
                    t: Throwable
                ) {
                    isLoading = false
                    val errorMsg = when {
                        t.message?.contains("Failed to connect") == true -> 
                            "Cannot connect to server. Check: 1) XAMPP is running 2) IP address in ApiConstants.kt matches your computer's IP 3) Phone and computer are on same network"
                        t.message?.contains("timeout") == true -> 
                            "Connection timeout. Server is slow or unreachable."
                        else -> 
                            "Network error: ${t.message}\nCheck ApiConstants.BASE_URL is correct"
                    }
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                }
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        /* Header */
        Row(
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Forgot Password",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        /* Content */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Reset Your Password",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your registered email address and we'll send you a verification code to reset your password.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* Email Label */
            Text(
                text = "Email Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* Email Field */
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* Submit Button */
            Button(
                onClick = { handleSubmit() },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4DB6AC)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Send Verification Code",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
