package tn.esprit.projectgainup.dtos

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String
)

