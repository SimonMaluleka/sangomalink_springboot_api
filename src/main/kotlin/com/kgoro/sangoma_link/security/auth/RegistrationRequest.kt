package com.kgoro.sangoma_link.security.auth
import com.kgoro.sangoma_link.user.enums.UserType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class RegistrationRequest(
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not well formatted")
    val email: String,
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be a minimum of 8 characters")
    val password: String,
    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    val firstname: String,
    @NotEmpty(message = "lastname is mandatory")
    @NotBlank(message = "lastname is mandatory")
    val lastname: String,
    @NotEmpty(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number is mandatory")
    @Email(message = "Phone number is not well formatted")
    val phoneNumber: String,
    val userType: UserType = UserType.Customer,
    val profileImageUrl: String = ""
)
