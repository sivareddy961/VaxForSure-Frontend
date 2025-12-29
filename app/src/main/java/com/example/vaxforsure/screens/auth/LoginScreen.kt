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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

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
                
                // Simulate login (local only)
                scope.launch {
                    delay(1000) // Simulate network delay
                    isLoading = false
                    
                    // Save user session locally
                    PreferenceManager.saveUserSession(
                        context,
                        1,
                        "User",
                        email.trim(),
                        "",
                        "",
                        1
                    )
                    
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                    onLogin()
                }
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
            onClick = onGoogleLogin,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Icon(Icons.Filled.AccountCircle, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continue with Google")
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
