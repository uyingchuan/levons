package cn.levons.server.common.impl

import cn.dev33.satoken.stp.StpInterface
import cn.dev33.satoken.stp.StpUtil
import cn.levons.server.account.services.UserService
import cn.levons.server.common.exception.ServiceException
import org.springframework.stereotype.Component


@Component
class StpInterfaceImpl(
  private val userService: UserService
) : StpInterface {
  override fun getPermissionList(o: Any, s: String): List<String> {
    return arrayListOf()
  }

  override fun getRoleList(o: Any, lognType: String): MutableList<String>? {
    val loginId = StpUtil.getLoginIdAsLong()
    val user = userService.findByUid(loginId)
      ?: throw ServiceException("获取登录用户数据失败")
    val list: MutableList<String> = ArrayList()
    list.add(user.roles)
    return list
  }
}
