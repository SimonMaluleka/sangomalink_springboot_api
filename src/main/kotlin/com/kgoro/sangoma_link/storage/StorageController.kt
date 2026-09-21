package com.kgoro.sangoma_link.storage

import com.kgoro.sangoma_link.security.UserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Optional
import java.util.UUID

@RestController
@RequestMapping("/storage")
class StorageController(
    val storageService: StorageService
) {
    @GetMapping
    fun getMedia(filename: String): Optional<Resource>{
        return storageService.loadResource(filename)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadMedia(@AuthenticationPrincipal principal: UserPrincipal, @RequestParam("file") file: MultipartFile): ResponseEntity<MediaMetadataResponse> {
        print("upload endpoint called")

        val log = LoggerFactory.getLogger(javaClass)
        log.info("upload endpoint called by principalId=${principal.id}")

        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(
                MediaMetadataResponse(url = "", createdAt = LocalDateTime.now(ZoneOffset.UTC), updatedAt = null)
            )
        }

        return try {
            // generate a unique id for the stored media, keep owner id separate
            val mediaId = UUID.randomUUID().toString()
            val ownerId = principal.id // keep type consistent with your service (UUID/Long)

            // storageService.store should save the file and return a public URL or path
            val savedMediaUrl = storageService.store(file, mediaId, ownerId)

            val response = MediaMetadataResponse(
                url = savedMediaUrl,
                createdAt = LocalDateTime.now(ZoneOffset.UTC),
                updatedAt = null
            )

            // return 201 Created with body (Location header optional)
            ResponseEntity.created(URI.create(savedMediaUrl)).body(response)
        } catch (ex: Exception) {
            log.error("Failed to store media", ex)
            ResponseEntity.status(500).body(
                MediaMetadataResponse(url = "", createdAt = LocalDateTime.now(ZoneOffset.UTC), updatedAt = null)
            )
        }
    }
}