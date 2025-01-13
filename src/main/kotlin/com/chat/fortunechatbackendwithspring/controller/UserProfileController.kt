package com.chat.fortunechatbackendwithspring.controller

import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.model.UserProfile
import com.chat.fortunechatbackendwithspring.model.UserSearchData
import com.chat.fortunechatbackendwithspring.service.CloudinaryService
import com.chat.fortunechatbackendwithspring.service.UserAndConversationService
import com.chat.fortunechatbackendwithspring.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/user")
class UserProfileController(
    private val userService: UserService,
    private val userAndConversationService: UserAndConversationService,
    private val cloudinaryService: CloudinaryService
) {

    @GetMapping("/profile")
    fun getProfile(@AuthenticationPrincipal user: User) : UserProfile {
        val currentUser=userService.getUser(user.getId())
        return UserProfile(username = currentUser.username, email = currentUser.getEmail()
        , displayName = currentUser.getDisplayName(), id = user.getId(), profileImageUrl =
                user.getProfileImageUrl()
        )
    }

    @GetMapping("/list")
    fun getAllUsers(@AuthenticationPrincipal user:User ,@RequestParam(defaultValue = "false") friendsWith:String):List<UserSearchData> {
        println("......")
        val listUserDisplayName=
            if(!friendsWith.equals("true")) {
                println("Running with friends service")
                userService.getAllUsers()
            }
                else{
                    println("Running without friends service")
                    userAndConversationService.getAllUsersThatAllNotYourFriends(user.getId())
                }
                    .filter {
                        it.getId()!=user.getId()
                    }
                    .map {
                        UserSearchData(id = it.getId() , displayName = it.getDisplayName(), profileImageUrl = it.getProfileImageUrl())}


        return listUserDisplayName;
    }

    @GetMapping("/profile/image")
    fun getProfileImage(@AuthenticationPrincipal user:User):RedirectView{
        val imageUrl=user.getProfileImageUrl()
        return RedirectView(imageUrl)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/profile/image")
    fun uploadNewProfileImage(@AuthenticationPrincipal user:User,@RequestParam("file") file: MultipartFile){
        val imageUrl=cloudinaryService.uploadFile(file)
        userService.setUserProfilePicture(user.getId(),imageUrl)
        println("profile image uploaded")
    }
}