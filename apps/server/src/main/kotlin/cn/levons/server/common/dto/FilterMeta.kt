package cn.levons.server.common.dto

data class FilterMeta(
    var matchMode: String? = "contains",
    var operator: String? = "AND",
    var value: Any? = null,
)
