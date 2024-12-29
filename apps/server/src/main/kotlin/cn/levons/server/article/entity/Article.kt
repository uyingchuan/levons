package cn.levons.server.article.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

@Entity
@Table(name = "l_article", schema = "levons_db")
data class Article(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  var id: Long = 0,

  @NotNull
  @Column(name = "author_id", nullable = false)
  var authorId: Long? = null,

  @Column(name = "title", nullable = false)
  var title: String,

  @Column(name = "summary", nullable = false)
  var summary: String,

  @Lob
  @Column(name = "content", nullable = false)
  var content: String,

  @ColumnDefault("0")
  @Column(name = "view_count", nullable = false)
  var viewCount: Int = 0,

  @ColumnDefault("0")
  @Column(name = "like_count", nullable = false)
  var likeCount: Int = 0,

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "create_time", nullable = false)
  var createTime: Instant = Instant.now(),

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "update_time", nullable = false)
  var updateTime: Instant = Instant.now()
)
