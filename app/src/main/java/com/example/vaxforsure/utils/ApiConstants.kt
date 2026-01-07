package com.example.vaxforsure.utils

object ApiConstants {
    // Base URL for XAMPP backend (port 8080)
    // ✅ Backend confirmed working on port 8080!
    
    // Updated to use physical device IP address
    // ⚠️ IMPORTANT: Port 8080 is REQUIRED - Apache is running on port 8080!
    const val BASE_URL = "http://10.148.199.69:8080/vaxforsure/api/"
    
    // For Android Emulator: use 10.0.2.2 to access localhost
    // Uncomment the line below and comment the physical device line above when testing on emulator:
    // const val BASE_URL = "http://10.0.2.2:8080/vaxforsure/api/"
    
    // API Endpoints
    object Auth {
        const val REGISTER = "auth/register.php"
        const val LOGIN = "auth/login.php"
        const val GOOGLE_LOGIN = "auth/google_login.php"
        const val FORGOT_PASSWORD = "auth/forgot_password.php"
        const val RESET_PASSWORD = "auth/reset_password.php"
        const val VERIFY_OTP = "auth/verify_otp.php"
    }
    
    object Children {
        const val ADD_CHILD = "children/add_child.php"
        const val UPDATE_HEALTH_DETAILS = "children/update_health_details.php"
        const val GET_CHILDREN = "children/get_children.php"
    }
    
    object Vaccinations {
        const val MARK_COMPLETED = "vaccinations/mark_completed.php"
        const val GET_VACCINATIONS = "vaccinations/get_vaccinations.php"
        const val GET_VACCINATION_RECORDS = "vaccinations/get_vaccination_records.php"
        const val GET_VACCINATION_STATUS = "vaccinations/get_vaccination_status.php"
        const val DELETE_VACCINATION = "vaccinations/delete_vaccination.php"
    }
}

