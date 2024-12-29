package cn.levons.server.common.util

import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringPath
import cn.levons.server.common.dto.FilterMeta
import cn.levons.server.common.exception.ServiceException


class QuerydslUtils {
    companion object {
        fun createPredicate(filter: Map<String, List<FilterMeta>>?, entity: EntityPathBase<*>): BooleanExpression {
            var predicates: BooleanExpression? = null
            if (filter != null) {
                for ((field, metaList) in filter) {
                    for (meta in metaList) {
                        if (meta.value === null) continue

                        val path = getFieldPath(field, entity)
                        val predicate = buildPredicate(meta.matchMode, path, meta.value)
                        predicates = if (predicates == null) predicate else
                            when (meta.operator) {
                                "or" -> predicates.or(predicate)
                                else -> predicates.and(predicate)
                            }
                    }
                }
            }
            return predicates ?: Expressions.TRUE
        }

        private fun getFieldPath(field: String, entity: EntityPathBase<*>): Path<*> {
            try {
                val qClass = Class.forName(entity::class.qualifiedName)
                val path = qClass.getDeclaredField(field).get(entity)
                return path as Path<*>
            } catch (ex: Exception) {
                throw ServiceException("字段不存在：$field")
            }
        }

        private fun buildPredicate(mode: String?, path: Path<*>, value: Any?): BooleanExpression {
            return when (mode) {
                "startsWith" -> (path as StringPath).startsWith(value as String)
                "endsWith" -> (path as StringPath).endsWith(value as String)
                "contains" -> (path as StringPath).contains(value as String)
                "notContains" -> (path as StringPath).notLike("%${value as String}%")
                "equals" -> (path as StringPath).eq(value as String)
                "notEquals" -> (path as StringPath).ne(value as String)
                else -> throw ServiceException("不支持的筛选类型：$mode")
            }
        }
    }
}
