package com.kgoro.sangoma_link.user.booking

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class BookingCancellationRequest (
    @NotBlank
    @NotEmpty
    val cancellationReason: String
)