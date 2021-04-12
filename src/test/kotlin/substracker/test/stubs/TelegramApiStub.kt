package substracker.test.stubs

import com.github.tomakehurst.wiremock.client.WireMock
import substracker.test.web.WebStubServer

class TelegramApiStub(private val stubServer: WebStubServer) {

    fun stubResponse(path: String, response: String): TelegramApiStub {
        stubServer.stubRequest {
            WireMock.stubFor(
                    WireMock.post(WireMock.urlMatching(path))
                            .willReturn(WireMock.ok(response))
            )
        }
        return this
    }

}