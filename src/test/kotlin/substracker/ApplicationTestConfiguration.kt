package substracker

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import substracker.test.web.TestWebClient
import substracker.test.web.WebStubServer
import substracker.test.stubs.TelegramApiStub

@Configuration
@ComponentScan
open class ApplicationTestConfiguration {

    @Bean
    open fun tomcatServletWebServerFactory() = TomcatServletWebServerFactory()

    @Bean
    open fun testWebClient(testRestTemplate: TestRestTemplate) = TestWebClient(testRestTemplate)

    @Bean
    open fun telegramApiStub(stubServer: WebStubServer) = TelegramApiStub(stubServer)

}