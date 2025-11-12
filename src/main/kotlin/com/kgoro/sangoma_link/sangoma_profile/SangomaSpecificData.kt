package com.kgoro.sangoma_link.sangoma_profile

data class SangomaSpecificData(
    val healingSpecialty: String,
    val biography: String? = null,
    val yearsOfExperience: Long,
    val traditionalLineage: String? = null,
    val languagesSpoken: List<String>? = null,

)
