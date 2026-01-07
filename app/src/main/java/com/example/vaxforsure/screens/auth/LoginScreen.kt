package com.example.vaxforsure.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaxforsure.utils.PreferenceManager
import com.example.vaxforsure.utils.GoogleSignInHelper
import com.example.vaxforsure.api.RetrofitClient
import com.example.vaxforsure.models.LoginRequest
import com.example.vaxforsure.models.GoogleLoginRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoogleLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isGoogleLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    // Google Sign-In launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val account = GoogleSignInHelper.getAccountFromIntent(result.data)
        
        if (account != null) {
            // User signed in with Google
            isGoogleLoading = true
            
            // Create Google login request
            val googleLoginRequest = GoogleLoginRequest(
                googleId = account.id ?: "",
                email = account.email ?: "",
                fullName = account.displayName ?: "User",
                photoUrl = account.photoUrl?.toString(),
                phone = null
            )
            
            // Call Google login API
            RetrofitClient.apiService.googleLogin(googleLoginRequest)
                .enqueue(object : Callback<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>> {
                    override fun onResponse(
                        call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>,
                        response: Response<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>
                    ) {
                        isGoogleLoading = false
                        
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
                                
                                Toast.makeText(context, "Google login successful!", Toast.LENGTH_SHORT).show()
                                onLogin()
                            } else {
                                Toast.makeText(context, apiResponse.message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorMessage = try {
                                response.errorBody()?.string() ?: "Google login failed. Please try again."
                            } catch (e: Exception) {
                                "Google login failed. Please try again."
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                    
                    override fun onFailure(
                        call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>,
                        t: Throwable
                    ) {
                        isGoogleLoading = false
                        Toast.makeText(
                            context,
                            "Google login failed: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            Toast.makeText(context, "Google sign-in cancelled or failed", Toast.LENGTH_SHORT).show()
        }
    }

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
                text = "Sign In",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Welcome Back!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Sign in to continue managing your child's health",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Email or Phone", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter your email") },
            leadingIcon = { Icon(Icons.Filled.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Password", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            trailingIcon = {
                Icon(
                    imageVector =
                        if (showPassword) Icons.Filled.VisibilityOff
                        else Icons.Filled.Visibility,
                    contentDescription = "Toggle password",
                    modifier = Modifier.clickable { showPassword = !showPassword }
                )
            },
            visualTransformation =
                if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                "Forgot Password?",
                color = Color(0xFF4DB6AC),
                fontSize = 13.sp,
                modifier = Modifier.clickable { onForgotPassword() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                
                isLoading = true
                
                // Create login request
                val loginRequest = LoginRequest(
                    email = email.trim(),
                    password = password
                )
                
                // Call login API
                RetrofitClient.apiService.login(loginRequest)
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
                                    
                                    Toast.makeText(context, apiResponse.message, Toast.LENGTH_SHORT).show()
                                    onLogin()
                                } else {
                                    Toast.makeText(context, apiResponse.message, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Try to parse error message
                                val errorMessage = try {
                                    response.errorBody()?.string() ?: "Login failed. Please try again."
                                } catch (e: Exception) {
                                    "Login failed. Please try again."
                                }
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                        
                        override fun onFailure(
                            call: Call<com.example.vaxforsure.models.ApiResponse<com.example.vaxforsure.models.AuthResponse>>,
                            t: Throwable
                        ) {
                            isLoading = false
                            
                            // Detailed error handling
                            val errorMessage = when {
                                t.message?.contains("Failed to connect", ignoreCase = true) == true -> {
                                    "❌ Cannot connect to backend server!\n\n" +
                                    "Please check:\n" +
                                    "1. XAMPP Apache is running (GREEN)\n" +
                                    "2. Test in browser: http://localhost:8080/vaxforsure/api/auth/login.php\n" +
                                    "3. Backend URL: ${com.example.vaxforsure.api.RetrofitClient.getBaseUrl()}\n\n" +
                                    "Error: ${t.message}"
                                }
                                t.message?.contains("timeout", ignoreCase = true) == true -> {
                                    "⏱️ Connection timeout!\n\n" +
                                    "Please check:\n" +
                                    "1. XAMPP Apache is running\n" +
                                    "2. Your internet connection\n" +
                                    "3. Firewall settings"
                                }
                                t.message?.contains("Unable to resolve host", ignoreCase = true) == true -> {
                                    "🌐 Cannot resolve server address!\n\n" +
                                    "For Emulator: Use 10.0.2.2\n" +
                                    "For Physical Device: Use your computer's IP\n" +
                                    "Current URL: ${com.example.vaxforsure.api.RetrofitClient.getBaseUrl()}"
                                }
                                t.message?.contains("Connection refused", ignoreCase = true) == true -> {
                                    "🚫 Connection refused!\n\n" +
                                    "XAMPP Apache is not running.\n" +
                                    "Please start Apache in XAMPP Control Panel."
                                }
                                else -> {
                                    "❌ Network Error\n\n" +
                                    "Message: ${t.message ?: "Unknown error"}\n" +
                                    "URL: ${com.example.vaxforsure.api.RetrofitClient.getBaseUrl()}"
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
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DB6AC)),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            } else {
                Text("Sign In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text("  or  ", fontSize = 12.sp, color = Color(0xFF757575))
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = {
                try {
                    val signInIntent = GoogleSignInHelper.getSignInIntent(context)
                    googleSignInLauncher.launch(signInIntent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Google Sign-In error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            enabled = !isGoogleLoading
        ) {
            if (isGoogleLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color(0xFF4DB6AC)
                )
            } else {
                Icon(Icons.Filled.AccountCircle, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Continue with Google")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Don't have an account? ", color = Color(0xFF757575))
            Text(
                "Sign Up",
                color = Color(0xFF4DB6AC),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSignUp() }
            )
        }
    }
}
