package cn.levons.server.account.repository

import cn.levons.server.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
  fun findByUid(uid: Long): User?
  fun findByUsername(username: String): User?
  fun findByEmail(email: String): User?
  fun existsByUsername(username: String): Boolean
  fun existsByEmail(email: String): Boolean
}
