package tn.esprit.projectgainup.dtos

// ResetPasswordRequest.kt
data class ResetPasswordRequest(
    val resetToken: String,
    val newPassword: String
)
