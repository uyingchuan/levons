package cn.levons.server.account.services

import cn.dev33.satoken.stp.StpUtil
import cn.levons.server.account.dto.CreateAccountParams
import cn.levons.server.account.dto.LoginRequest
import cn.levons.server.account.dto.RegisterUserRequest
import cn.levons.server.account.entity.User
import cn.levons.server.common.dto.BaseResponse
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class AuthService(private val userService: UserService) {
  fun registerUser(request: RegisterUserRequest): BaseResponse<Long> {
    if (userService.existsByEmail(request.email)) {
      return BaseResponse.error(code = "100001", message = "邮箱已注册")
    }

    val username = request.username ?: "user_${generateUsername(8)}"
    if (userService.existsByUsername(username)) {
      return BaseResponse.error(code = "100002", message = "用户名已存在")
    }

    val hashedPassword = hashPassword(request.password)
    val user = userService.createAccount(
      CreateAccountParams(
        email = request.email,
        username = username,
        password = hashedPassword,
        roles = "user"
      )
    )

    return BaseResponse.success(data = user.uid)
  }

  fun login(request: LoginRequest): BaseResponse<User> {
    val user = when {
      request.account.contains("@") -> userService.findByEmail(request.account)
      else -> userService.findByUsername(request.account)
    } ?: return BaseResponse.error(code = "100002", message = "账号不存在")

    if (!authenticate(request.password, user.password)) {
      return BaseResponse.error(code = "100001", message = "账号或密码错误")
    }

    StpUtil.login(user.uid)

    return BaseResponse.success(data = user)
  }

  fun generateUsername(length: Int): String {
    val chars = "abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ0123456789"

    return (1..length)
      .map { chars.random() }
      .joinToString("")
  }


  private fun authenticate(password: String, hashedPassword: String): Boolean {
    return verifyPassword(password, hashedPassword)
  }

  private fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
  }

  private fun verifyPassword(password: String, hashedPassword: String): Boolean {
    return BCrypt.checkpw(password, hashedPassword)
  }
}
