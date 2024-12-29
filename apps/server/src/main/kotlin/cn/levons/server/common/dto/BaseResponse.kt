package cn.levons.server.common.dto

class BaseResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null): BaseResponse<T> =
            BaseResponse(
                success = true,
                code = "000000",
                message = "success",
                data = data
            )

        fun <T> error(code: String = "111111", message: String): BaseResponse<T> =
            BaseResponse(
                success = false,
                code = code,
                message = message,
                data = null
            )
    }
}

