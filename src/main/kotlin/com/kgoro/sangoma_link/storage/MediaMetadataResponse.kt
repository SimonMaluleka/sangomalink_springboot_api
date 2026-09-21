package com.kgoro.sangoma_link.storage

import java.time.LocalDateTime

class MediaMetadataResponse (
    val url: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)