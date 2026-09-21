package com.kgoro.sangoma_link.sangoma.profile

data class SpecificData(
    val healingSpecialty: String,
    val biography: String? = null,
    val yearsOfExperience: Long,
    val traditionalLineage: String? = null,
    val languagesSpoken: List<String>? = null,

    )