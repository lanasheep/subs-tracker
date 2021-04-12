package substracker.test.config

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import substracker.test.web.WebStubServer
import substracker.test.utils.NetUtils

@Configuration
open class WebServiceStubConfiguration {

    companion object {
        const val TEST_WEB_SERVICE_STUB_PORT_PROPERTY = "testServiceStubPort"
    }

    @Bean(destroyMethod = "stop")
    open fun wireMockServer(): WireMockServer {
        val port = System.getProperty(TEST_WEB_SERVICE_STUB_PORT_PROPERTY)?.toInt() ?: NetUtils.getFreeRandomLocalPort()
        val server = WireMockServer(port)
        server.start()
        return server
    }

    @Bean
    open fun webStubServer(wireMockServer: WireMockServer) = WebStubServer(wireMockServer)

}