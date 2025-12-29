package com.example.vaxforsure.screens.onboarding

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaxforsure.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun OTPVerificationScreen(
    onBack: () -> Unit,
    onVerified: () -> Unit
) {
    val context = LocalContext.current
    val otpLength = 6
    val otp = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(otpLength) { FocusRequester() }

    var timer by remember { mutableStateOf(60) }
    var isVerifying by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    
    // Get saved OTP email
    val otpEmail = remember { PreferenceManager.getOtpEmail(context) }

    /* Timer */
    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        }
    }

    fun handleVerify() {
        val otpCode = otp.joinToString("")
        if (otpCode.length != 6) {
            Toast.makeText(context, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            return
        }
        
        isVerifying = true
        
        // Simulate OTP verification (local only)
        scope.launch {
            delay(1000) // Simulate network delay
            
            // Get saved OTP from SharedPreferences
            val savedOtp = PreferenceManager.getOtpCode(context)
            
            if (otpCode == savedOtp) {
                PreferenceManager.clearOtpData(context)
                Toast.makeText(context, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
                onVerified()
            } else {
                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
            }
            
            isVerifying = false
        }
    }

    fun handleResend() {
        for (i in 0 until otpLength) otp[i] = ""
        timer = 60
        focusRequesters[0].requestFocus()
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
                text = "Verify OTP",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFE0F7FA), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Check",
                    tint = Color(0xFF4DB6AC),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Verification Code",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We've sent a 6-digit code to your email. Please enter it below.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* OTP Inputs */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                otp.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            if (it.length <= 1 && it.all(Char::isDigit)) {
                                otp[index] = it
                                if (it.isNotEmpty() && index < otpLength - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(48.dp)
                            .height(56.dp)
                            .focusRequester(focusRequesters[index])
                            .onPreviewKeyEvent { event: KeyEvent ->
                                if (
                                    event.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_DEL &&
                                    value.isEmpty() &&
                                    index > 0
                                ) {
                                    focusRequesters[index - 1].requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (timer > 0) {
                Text("Resend code in ${timer}s")
            } else {
                Text(
                    text = "Resend Code",
                    color = Color(0xFF4DB6AC),
                    modifier = Modifier.clickable { handleResend() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { handleVerify() },
                enabled = !isVerifying,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4DB6AC)
                )
            ) {
                if (isVerifying) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Verify")
                }
            }
        }
    }
}
