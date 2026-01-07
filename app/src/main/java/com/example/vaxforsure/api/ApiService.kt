package com.example.vaxforsure.api

import com.example.vaxforsure.models.*
import com.example.vaxforsure.utils.ApiConstants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST(ApiConstants.Auth.LOGIN)
    fun login(@Body request: LoginRequest): Call<ApiResponse<AuthResponse>>
    
    @POST(ApiConstants.Auth.REGISTER)
    fun register(@Body request: RegisterRequest): Call<ApiResponse<AuthResponse>>
    
    @POST(ApiConstants.Auth.GOOGLE_LOGIN)
    fun googleLogin(@Body request: GoogleLoginRequest): Call<ApiResponse<AuthResponse>>
    
    @POST(ApiConstants.Children.ADD_CHILD)
    fun addChild(@Body request: AddChildRequest): Call<ApiResponse<AddChildResponse>>
    
    @POST(ApiConstants.Children.UPDATE_HEALTH_DETAILS)
    fun updateHealthDetails(@Body request: UpdateHealthDetailsRequest): Call<ApiResponse<Any>>
    
    @POST(ApiConstants.Vaccinations.MARK_COMPLETED)
    fun markVaccineCompleted(@Body request: MarkVaccineCompletedRequest): Call<ApiResponse<MarkVaccineCompletedResponse>>
    
    @GET(ApiConstants.Vaccinations.GET_VACCINATIONS)
    fun getVaccinations(
        @Query("childId") childId: Int? = null,
        @Query("userId") userId: Int? = null,
        @Query("status") status: String = "all"
    ): Call<ApiResponse<GetVaccinationsResponse>>
    
    @GET(ApiConstants.Vaccinations.GET_VACCINATION_RECORDS)
    fun getVaccinationRecords(@Query("userId") userId: Int): Call<ApiResponse<Any>>
    
    @GET(ApiConstants.Vaccinations.GET_VACCINATION_STATUS)
    fun getVaccinationStatus(
        @Query("childId") childId: Int,
        @Query("vaccineName") vaccineName: String
    ): Call<ApiResponse<Any>>
    
    @GET(ApiConstants.Children.GET_CHILDREN)
    fun getChildren(@Query("userId") userId: Int): Call<ApiResponse<Any>>
    
    @POST(ApiConstants.Vaccinations.DELETE_VACCINATION)
    fun deleteVaccination(@Body request: DeleteVaccinationRequest): Call<ApiResponse<Any>>
    
    @POST(ApiConstants.Auth.FORGOT_PASSWORD)
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ApiResponse<Any>>
    
    @POST(ApiConstants.Auth.VERIFY_OTP)
    fun verifyOtp(@Body request: VerifyOtpRequest): Call<ApiResponse<Any>>
    
    @POST(ApiConstants.Auth.RESET_PASSWORD)
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ApiResponse<Any>>
}

