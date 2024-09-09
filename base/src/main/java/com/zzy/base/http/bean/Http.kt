package com.zzy.base.http.bean

object Http {
    const val ip = "116.198.234.250"
    private const val port = "10001"
    const val HTTP = "http://$ip:$port"
    const val HTTPS = "https://$ip:$port"
}

object HttpInformation{
    const val `-1` = "连接超时"
    const val `101` = "账号或密码错误"
    const val `102` = ""
}