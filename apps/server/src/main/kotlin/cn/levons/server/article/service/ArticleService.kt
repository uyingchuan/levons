package cn.levons.server.article.service

import cn.dev33.satoken.stp.StpUtil
import cn.levons.server.article.dto.CreateArticleRequest
import cn.levons.server.article.entity.Article
import cn.levons.server.article.repository.ArticleRepository
import cn.levons.server.common.dto.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(private val articleRepository: ArticleRepository) {
  @Transactional
  fun createArticle(request: CreateArticleRequest): BaseResponse<Long> {
    val uid = StpUtil.getLoginIdAsLong()

    val summaryRegex = Regex("([*#\\[\\](!)(-)+]|</?[^>]+>)")
    val summary = request.content
      .replace(summaryRegex, "")
      .replace(Regex("\\s+"), "")
      .take(100)

    val article = Article(
      authorId = uid,
      title = request.title,
      summary = summary,
      content = request.content,
    )
    val result = articleRepository.save(article)
    return BaseResponse.success(result.id)
  }
}
