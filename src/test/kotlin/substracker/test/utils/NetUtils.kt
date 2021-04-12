package substracker.test.utils

import java.net.ServerSocket

object NetUtils {

    fun getFreeRandomLocalPort(): Int = ServerSocket(0).localPort
}