package com.example.vaxforsure.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

object ConnectionHelper {
    /**
     * Check if device has internet connection
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
    
    /**
     * Show connection error message
     */
    fun showConnectionError(context: Context, throwable: Throwable) {
        val errorMessage = when {
            throwable.message?.contains("Failed to connect") == true -> {
                "Cannot connect to backend server.\n\n" +
                "Please ensure:\n" +
                "1. XAMPP Apache is running (GREEN)\n" +
                "2. XAMPP MySQL is running (GREEN)\n" +
                "3. Test URL in browser:\n" +
                "   ${ApiConstants.BASE_URL}auth/login.php\n\n" +
                "Current URL: ${ApiConstants.BASE_URL}"
            }
            throwable.message?.contains("timeout") == true -> {
                "Connection timeout.\n\n" +
                "Please check:\n" +
                "1. XAMPP Apache is running\n" +
                "2. Your internet connection\n" +
                "3. Firewall settings"
            }
            throwable.message?.contains("Unable to resolve host") == true -> {
                "Cannot resolve server address.\n\n" +
                "For Emulator: Use 10.0.2.2\n" +
                "For Physical Device: Use your computer's IP\n" +
                "Current: ${ApiConstants.BASE_URL}"
            }
            else -> {
                "Network error: ${throwable.message ?: "Unknown error"}\n\n" +
                "Backend URL: ${ApiConstants.BASE_URL}"
            }
        }
        
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}

