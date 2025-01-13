package com.chat.fortunechatbackendwithspring.service

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CloudinaryService(private val cloudinary: Cloudinary) {


    @Value("\${cloudinary.profile-image.width}")
    private lateinit var profileImageWidth:Integer
    @Value("\${cloudinary.profile-image.height}")
    private lateinit var profileImageHeight:Integer

    fun uploadFile(file: MultipartFile): String {
        val cloudinaryRes= cloudinary.uploader().upload(
            file.bytes, mapOf(Pair("folder","profile")
                //Pair("transformation", listOf(
                //mapOf("width" to profileImageWidth, "height" to profileImageHeight, "crop" to "fill")
            )
        )

        return cloudinaryRes.get("url") as String
    }
}