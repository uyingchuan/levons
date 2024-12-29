package cn.levons.server.account.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

@Entity
@Table(name = "l_user", schema = "levons_db")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "uid", nullable = false)
  var uid: Long = 0,

  @Column(name = "username", nullable = false)
  var username: String,

  @Column(name = "email", nullable = false)
  var email: String,

  @Column(name = "nickname", nullable = false)
  var nickname: String,

  @Column(name = "password", nullable = false)
  var password: String,

  @ColumnDefault("'user'")
  @Column(name = "roles", nullable = false)
  var roles: String,

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "create_time")
  var createTime: Instant = Instant.now(),

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "update_time")
  var updateTime: Instant = Instant.now(),
)
