package cn.levons.server.article.repository

import cn.levons.server.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long>, QuerydslPredicateExecutor<Article> {
}
