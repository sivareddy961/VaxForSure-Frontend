package com.example.vaxforsure.api

import com.example.vaxforsure.models.*
import com.example.vaxforsure.utils.ApiConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(ApiConstants.Auth.LOGIN)
    fun login(@Body request: LoginRequest): Call<ApiResponse<AuthResponse>>
    
    @POST(ApiConstants.Auth.REGISTER)
    fun register(@Body request: RegisterRequest): Call<ApiResponse<AuthResponse>>
}

