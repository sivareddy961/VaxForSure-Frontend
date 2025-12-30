// Copy this EXACT code to ApiConstants.kt
// File: app/src/main/java/com/example/vaxforsure/utils/ApiConstants.kt

package com.example.vaxforsure.utils

object ApiConstants {
    // Base URL for XAMPP backend
    // Port 8080 is confirmed running on your system
    
    // For Android Emulator: use 10.0.2.2 to access localhost
    const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
    
    // For Physical Device: Use your computer's IP address
    // Your current IP: 10.148.199.69
    // Uncomment the line below and comment the emulator line above when testing on physical device:
    // const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
    
    // API Endpoints
    object Auth {
        const val REGISTER = "auth/register.php"
        const val LOGIN = "auth/login.php"
        const val FORGOT_PASSWORD = "auth/forgot_password.php"
        const val RESET_PASSWORD = "auth/reset_password.php"
        const val VERIFY_OTP = "auth/verify_otp.php"
    }
}

