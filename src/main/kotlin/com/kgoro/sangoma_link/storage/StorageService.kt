package com.kgoro.sangoma_link.storage

import com.kgoro.sangoma_link.exception.StorageException
import com.kgoro.sangoma_link.user.UserRepository
import com.kgoro.sangoma_link.user.UserService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.Optional

@Service
class StorageService (
    @param:Value("\${spring.application.file.uploads.media-output-path")
    val storageLocation: String,
    val userRepository: UserRepository
){
    lateinit var rootLocation: Path

    @PostConstruct
    fun init(){
        rootLocation = Paths.get(storageLocation)

        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException){
            throw StorageException("Could not initialize storage location: $e")
        }
    }

    fun store(file: MultipartFile, filename: String, userId: Long): String {
        if(file.isEmpty){
            throw StorageException("Cannot save an empty file")
        }

        val fileExtension = StringUtils.getFilenameExtension(file.originalFilename)
        val finalFilename = "$filename.$fileExtension"

        val storageLocationPath = rootLocation.resolve(Paths.get(finalFilename))
            .normalize()
            .toAbsolutePath()

        print(storageLocationPath)

        if(!storageLocationPath.parent.equals((rootLocation.toAbsolutePath()))){
            throw StorageException("Cannot store file outside specified media directory")
        }

        try {
            val inputStream: InputStream = file.inputStream

            Files.copy(inputStream, storageLocationPath, StandardCopyOption.REPLACE_EXISTING)
            userRepository.saveUserProfileImageMetadata(finalFilename, userId)
        } catch (e: IOException){
            throw StorageException("Failed to store file: $e")
        }

        return finalFilename
    }

    fun loadResource(filename: String): Optional<Resource> {
        try {
            val fileStoragePath = rootLocation.resolve(filename)
            val resource = UrlResource(fileStoragePath.toUri())

            if(resource.exists() && resource.isReadable){
                return Optional.of(resource)
            }

            return Optional.empty()
        } catch (e: MalformedURLException){
            println("Could not read file: $filename $e")
            return Optional.empty()
        }
    }
}