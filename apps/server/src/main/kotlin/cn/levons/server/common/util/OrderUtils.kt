package cn.levons.server.common.util

import cn.levons.server.common.dto.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class OrderUtils {
    companion object {
        fun of(pageRequest: PageRequest): Pageable {
            return org.springframework.data.domain.PageRequest.of(
                pageRequest.pageNum - 1,
                pageRequest.pageSize,
                Sort.by(
                    if (pageRequest.sortOrder == 1) Sort.Direction.ASC else Sort.Direction.DESC,
                    pageRequest.sortField
                ),
            )
        }
    }
}
