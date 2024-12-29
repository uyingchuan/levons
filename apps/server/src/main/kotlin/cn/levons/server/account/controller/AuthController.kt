package cn.levons.server.account.controller

import cn.levons.server.account.dto.LoginRequest
import cn.levons.server.account.dto.RegisterUserRequest
import cn.levons.server.account.entity.User
import cn.levons.server.account.services.AuthService
import cn.levons.server.common.dto.BaseResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private var authService: AuthService) {
  @PostMapping("/register")
  fun registerUser(@RequestBody @Valid request: RegisterUserRequest): BaseResponse<Long> {
    return authService.registerUser(request)
  }

  @PostMapping("/login")
  fun login(@RequestBody @Valid request: LoginRequest): BaseResponse<User> {
    return authService.login(request)
  }
}
