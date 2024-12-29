package cn.levons.server.common.config

import cn.dev33.satoken.config.SaTokenConfig
import cn.dev33.satoken.interceptor.SaInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class SaTokenConfigure : WebMvcConfigurer {
  @Autowired
  fun configSaToken(config: SaTokenConfig) {
    config.setTokenName("Bearer")
    config.setTimeout(30 * 24 * 60 * 60)
    config.setActiveTimeout(-1)
    config.setIsConcurrent(true)
    config.setIsShare(true)
    config.setTokenStyle("uuid")
    config.setIsLog(true)
  }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(SaInterceptor()).addPathPatterns("/**")
  }
}
