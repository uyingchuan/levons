package cn.levons.server.article.controller

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.levons.server.article.dto.CreateArticleRequest
import cn.levons.server.article.service.ArticleService
import cn.levons.server.common.dto.BaseResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController(private val articleService: ArticleService) {

  @SaCheckLogin
  @PostMapping("/create")
  fun createArticle(@RequestBody @Valid request: CreateArticleRequest): BaseResponse<Long> {
    return articleService.createArticle(request)
  }
}
