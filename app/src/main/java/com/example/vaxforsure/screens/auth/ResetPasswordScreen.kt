package com.example.vaxforsure.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.api.RetrofitClient
import com.example.vaxforsure.models.ResetPasswordRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onPasswordReset: () -> Unit
) {
    val context = LocalContext.current
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val otpEmail = remember { PreferenceManager.getOtpEmail(context) }
    val otpCode = remember { PreferenceManager.getOtpCode(context) }

    fun handleResetPassword() {
        if (newPassword.isBlank()) {
            Toast.makeText(context, "Please enter a new password", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (newPassword.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (newPassword != confirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (otpEmail.isEmpty() || otpCode.isEmpty()) {
            Toast.makeText(context, "OTP verification required. Please start over.", Toast.LENGTH_SHORT).show()
            return
        }
        
        isLoading = true
        
        // Call backend API to reset password
        val resetPasswordRequest = ResetPasswordRequest(
            email = otpEmail,
            otp = otpCode,
            newPassword = newPassword
        )
        
        RetrofitClient.apiService.resetPassword(resetPasswordRequest)
            .enqueue(object : Callback<com.example.vaxforsure.models.ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<com.example.vaxforsure.models.ApiResponse<Any>>,
                    response: Response<com.example.vaxforsure.models.ApiResponse<Any>>
                ) {
                    isLoading = false
                    
                    if (response.isSuccessful && response.body() != null) {
                        val apiResponse = response.body()!!
                        
                        if (apiResponse.success) {
                            // Clear OTP data
                            PreferenceManager.clearOtpData(context)
                            
                            Toast.makeText(
                                context,
                                apiResponse.message ?: "Password reset successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                            
                            onPasswordReset()
                        } else {
                            Toast.makeText(
                                context,
                                apiResponse.message ?: "Failed to reset password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val errorMessage = try {
                            val errorBody = response.errorBody()?.string() ?: ""
                            val jsonError = com.google.gson.Gson().fromJson(errorBody, com.example.vaxforsure.models.ApiResponse::class.java)
                            jsonError.message.replace(Regex("<[^>]*>"), "").trim()
                                .ifEmpty { "Failed to reset password. Please try again." }
                        } catch (e: Exception) {
                            "Failed to reset password. Please check your internet connection."
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                
                override fun onFailure(
                    call: Call<com.example.vaxforsure.models.ApiResponse<Any>>,
                    t: Throwable
                ) {
                    isLoading = false
                    Toast.makeText(
                        context,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
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
            modifier = Modifier.padding(24.dp),
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
                text = "Reset Password",
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
                text = "Create New Password",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please enter your new password below. Make sure it's at least 6 characters long.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* New Password Label */
            Text(
                text = "New Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* New Password Field */
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Enter new password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* Confirm Password Label */
            Text(
                text = "Confirm Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* Confirm Password Field */
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm new password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* Submit Button */
            Button(
                onClick = { handleResetPassword() },
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
                        text = "Reset Password",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}



