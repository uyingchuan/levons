package cn.levons.server.common.dto

import org.springframework.data.domain.Page

open class PageResponse<T>(
    val pageSize: Int,
    val pageNum: Int,
    val total: Long,
    val list: List<T>
) {
    companion object {
        fun <T> from(page: Page<*>, list: List<T>): PageResponse<T> =
            PageResponse(
                pageSize = page.size,
                pageNum = page.number + 1,
                total = page.totalElements,
                list = list
            )
    }
}

open class PageRequest(
  val pageNum: Int = 1,
  val pageSize: Int = 10,
  val sortOrder: Int? = -1,
  val sortField: String? = "createTime",
  val filters: Map<String, List<FilterMeta>>? = null,
)
