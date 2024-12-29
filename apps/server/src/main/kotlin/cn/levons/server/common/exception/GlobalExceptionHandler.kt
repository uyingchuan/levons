package cn.levons.server.common.exception

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.stp.StpUtil
import cn.levons.server.common.dto.BaseResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 处理参数验证失败异常
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(e: MethodArgumentNotValidException): BaseResponse<Nothing> {
        val message = e.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.info("[参数验证失败] {}", message)
        return BaseResponse.error(message = message)
    }

    // 处理请求方式异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodException(e: HttpRequestMethodNotSupportedException): ResponseEntity<BaseResponse<Nothing>> {
        log.info("[请求方式错误]", e)
        return ResponseEntity.status(405).body(BaseResponse.error(message = "Method Not Allowed"))
    }

    // 处理业务逻辑异常
    @ExceptionHandler(ServiceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: ServiceException): BaseResponse<Nothing> {
        log.info("[业务处理失败] {}，操作者ID：{}", e, StpUtil.getLoginIdDefaultNull())
        return BaseResponse.error(message = e.message ?: "业务处理失败")
    }

    // 处理权限检验未通过的异常
    @ExceptionHandler(NotPermissionException::class)
    fun handleNotPermissionException(
        e: NotPermissionException?,
        request: HttpServletRequest?
    ): ResponseEntity<BaseResponse<Nothing>> {
        log.info("[权限校验失败] 用户没有操作权限，操作者ID：{}", StpUtil.getLoginIdDefaultNull())
        return ResponseEntity.status(403).body(BaseResponse.error(message = "无权限"))
    }

    // 处理角色检验未通过的异常
    @ExceptionHandler(NotRoleException::class)
    fun handleNotRoleException(
        e: NotRoleException?,
        request: HttpServletRequest?
    ): ResponseEntity<BaseResponse<Nothing>> {
        log.info("[角色校验失败] 用户没有操作权限，操作者ID：{}", StpUtil.getLoginIdDefaultNull())
        return ResponseEntity.status(403).body(BaseResponse.error(message = "无权限"))
    }

    // 处理登录鉴权未通过的异常
    @ExceptionHandler(NotLoginException::class)
    fun handleNotLoginException(
        e: NotLoginException?,
        request: HttpServletRequest?
    ): ResponseEntity<BaseResponse<Nothing>> {
        return ResponseEntity.status(401).body(BaseResponse.error(message = "未登录"))
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(e: Exception): BaseResponse<Nothing> {
        log.error("系统异常", e)
        return BaseResponse.error(message = "服务器内部错误")
    }
}
