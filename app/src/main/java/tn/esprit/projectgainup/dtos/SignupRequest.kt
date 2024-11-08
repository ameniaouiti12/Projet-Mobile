package tn.esprit.projectgainup.dtos

// SignupRequest.kt
data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

