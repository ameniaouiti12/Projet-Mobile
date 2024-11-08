package tn.esprit.projectgainup.dtos

// GenericResponse.kt
data class GenericResponse(
    val success: Boolean,
    val message: String? = null,
    val exists: Boolean? = null
)



