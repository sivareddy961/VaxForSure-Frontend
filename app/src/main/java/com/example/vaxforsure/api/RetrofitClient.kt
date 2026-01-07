package com.example.vaxforsure.api

import com.example.vaxforsure.utils.ApiConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Singleton instance
    private var retrofitInstance: Retrofit? = null
    private var apiServiceInstance: ApiService? = null
    
    // HTTP Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // OkHttpClient with optimized settings
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS) // Increased timeout
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(true)
        .followSslRedirects(true)
        .build()
    
    // Get Retrofit instance
    private fun getRetrofit(): Retrofit {
        if (retrofitInstance == null) {
            // Ensure base URL ends with /
            val baseUrl = if (ApiConstants.BASE_URL.endsWith("/")) {
                ApiConstants.BASE_URL
            } else {
                "${ApiConstants.BASE_URL}/"
            }
            
            // Create Gson with lenient mode to handle minor JSON formatting issues
            val gson = GsonBuilder()
                .setLenient()
                .create()
            
            retrofitInstance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofitInstance!!
    }
    
    // Get API Service instance
    val apiService: ApiService
        get() {
            if (apiServiceInstance == null) {
                apiServiceInstance = getRetrofit().create(ApiService::class.java)
            }
            return apiServiceInstance!!
        }
    
    /**
     * Get the base URL being used
     */
    fun getBaseUrl(): String {
        return ApiConstants.BASE_URL
    }
    
    /**
     * Reset Retrofit instance (useful for testing or URL changes)
     */
    fun reset() {
        retrofitInstance = null
        apiServiceInstance = null
    }
}

