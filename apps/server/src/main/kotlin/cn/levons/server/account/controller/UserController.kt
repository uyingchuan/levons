package cn.levons.server.account.controller

import cn.levons.server.account.dto.RegisterUserRequest
import cn.levons.server.account.entity.User
import cn.levons.server.account.services.UserService
import cn.levons.server.common.dto.BaseResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private var userService: UserService) {
}
