package com.example.vaxforsure.models

import com.google.gson.annotations.SerializedName

// Generic API Response Wrapper
data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
)

// Login Request
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// Register Request
data class RegisterRequest(
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)

// Google Login Request
data class GoogleLoginRequest(
    @SerializedName("googleId") val googleId: String,
    @SerializedName("email") val email: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("phone") val phone: String? = null
)

// User Model
data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email_verified") val emailVerified: Int
)

// Auth Response (contains user data)
data class AuthResponse(
    @SerializedName("user") val user: User
)

// Add Child Request
data class AddChildRequest(
    @SerializedName("userId") val userId: Int,
    @SerializedName("parentName") val parentName: String,
    @SerializedName("name") val name: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String
)

// Child Response
data class ChildResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("parent_name") val parentName: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String
)

// Add Child Response
data class AddChildResponse(
    @SerializedName("child") val child: ChildResponse
)

// Update Health Details Request
data class UpdateHealthDetailsRequest(
    @SerializedName("childId") val childId: Int,
    @SerializedName("birthWeight") val birthWeight: Double? = null,
    @SerializedName("birthHeight") val birthHeight: Double? = null,
    @SerializedName("bloodGroup") val bloodGroup: String? = null,
    @SerializedName("allergies") val allergies: String? = null,
    @SerializedName("medicalConditions") val medicalConditions: String? = null
)

// Mark Vaccine Completed Request
data class MarkVaccineCompletedRequest(
    @SerializedName("childId") val childId: Int,
    @SerializedName("vaccineName") val vaccineName: String,
    @SerializedName("dateAdministered") val dateAdministered: String,
    @SerializedName("healthcareFacility") val healthcareFacility: String,
    @SerializedName("batchLotNumber") val batchLotNumber: String? = null,
    @SerializedName("notes") val notes: String? = null
)

// Vaccination Response
data class VaccinationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("child_id") val childId: Int,
    @SerializedName("vaccine_name") val vaccineName: String,
    @SerializedName("dose_number") val doseNumber: Int,
    @SerializedName("completed_date") val completedDate: String?,
    @SerializedName("status") val status: String,
    @SerializedName("healthcare_facility") val healthcareFacility: String?,
    @SerializedName("batch_lot_number") val batchLotNumber: String?,
    @SerializedName("notes") val notes: String?
)

// Mark Vaccine Completed Response
data class MarkVaccineCompletedResponse(
    @SerializedName("vaccination") val vaccination: VaccinationResponse
)

// Get Vaccinations Response
data class GetVaccinationsResponse(
    @SerializedName("vaccinations") val vaccinations: List<VaccinationResponse>
)

// Delete Vaccination Request
data class DeleteVaccinationRequest(
    @SerializedName("vaccinationId") val vaccinationId: Int
)

// Forgot Password Request
data class ForgotPasswordRequest(
    @SerializedName("email") val email: String
)

// Verify OTP Request
data class VerifyOtpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("otp") val otp: String
)

// Reset Password Request
data class ResetPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("otp") val otp: String,
    @SerializedName("newPassword") val newPassword: String
)

