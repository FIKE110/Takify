package com.chat.fortunechatbackendwithspring

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/web")
class JTEWebController {

    @GetMapping
    fun index():String{
        return "index"
    }

    @GetMapping("/chat")
    fun chat():String{
        return "chat"
    }
}