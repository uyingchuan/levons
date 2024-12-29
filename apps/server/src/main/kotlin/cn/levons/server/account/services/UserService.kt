package cn.levons.server.account.services

import cn.levons.server.account.dto.CreateAccountParams
import cn.levons.server.account.entity.User
import cn.levons.server.account.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {
  fun findByUid(uid: Long): User? {
    return userRepository.findByUid(uid)
  }

  fun existsByEmail(email: String): Boolean {
    return userRepository.existsByUsername(email)
  }

  fun existsByUsername(username: String): Boolean {
    return userRepository.existsByUsername(username)
  }

  fun findByEmail(email: String): User? {
    return userRepository.findByEmail(email)
  }

  fun findByUsername(username: String): User? {
    return userRepository.findByUsername(username)
  }

  @Transactional
  fun createAccount(params: CreateAccountParams): User {
    val count = userRepository.count()
    val user = User(
      username = params.username,
      password = params.password,
      email = params.email,
      roles = params.roles,
      nickname = "用户_${count + 1}",
    )
    return userRepository.save(user)
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
