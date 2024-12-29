package cn.levons.server.account.dto


data class CreateAccountParams(
  val username: String,
  val email: String,
  val password: String,
  val roles: String
)
