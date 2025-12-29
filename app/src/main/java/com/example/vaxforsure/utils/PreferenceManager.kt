package com.example.vaxforsure.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREFS_NAME = "vaxforsure_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PHONE = "user_phone"
    private const val KEY_TOKEN = "token"
    private const val KEY_EMAIL_VERIFIED = "email_verified"
    private const val KEY_OTP_EMAIL = "otp_email"
    private const val KEY_OTP_CODE = "otp_code"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    // User Session Management
    fun saveUserSession(
        context: Context,
        userId: Int,
        userName: String,
        userEmail: String,
        userPhone: String?,
        token: String,
        emailVerified: Int
    ) {
        getPrefs(context).edit().apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, userName)
            putString(KEY_USER_EMAIL, userEmail)
            putString(KEY_USER_PHONE, userPhone ?: "")
            putString(KEY_TOKEN, token)
            putInt(KEY_EMAIL_VERIFIED, emailVerified)
            apply()
        }
    }
    
    fun getUserId(context: Context): Int {
        return getPrefs(context).getInt(KEY_USER_ID, 0)
    }
    
    fun getUserName(context: Context): String {
        return getPrefs(context).getString(KEY_USER_NAME, "") ?: ""
    }
    
    fun getUserEmail(context: Context): String {
        return getPrefs(context).getString(KEY_USER_EMAIL, "") ?: ""
    }
    
    fun getUserPhone(context: Context): String {
        return getPrefs(context).getString(KEY_USER_PHONE, "") ?: ""
    }
    
    fun getToken(context: Context): String {
        return getPrefs(context).getString(KEY_TOKEN, "") ?: ""
    }
    
    fun isEmailVerified(context: Context): Boolean {
        return getPrefs(context).getInt(KEY_EMAIL_VERIFIED, 0) == 1
    }
    
    fun isLoggedIn(context: Context): Boolean {
        return getUserId(context) > 0
    }
    
    fun clearSession(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
    
    // OTP Management
    fun saveOtpData(context: Context, email: String, otpCode: String) {
        getPrefs(context).edit().apply {
            putString(KEY_OTP_EMAIL, email)
            putString(KEY_OTP_CODE, otpCode)
            apply()
        }
    }
    
    fun getOtpEmail(context: Context): String {
        return getPrefs(context).getString(KEY_OTP_EMAIL, "") ?: ""
    }
    
    fun getOtpCode(context: Context): String {
        return getPrefs(context).getString(KEY_OTP_CODE, "") ?: ""
    }
    
    fun clearOtpData(context: Context) {
        getPrefs(context).edit().apply {
            remove(KEY_OTP_EMAIL)
            remove(KEY_OTP_CODE)
            apply()
        }
    }
}

