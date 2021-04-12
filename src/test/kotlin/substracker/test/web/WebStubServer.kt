package substracker.test.web

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

class WebStubServer(private val wireMockServer: WireMockServer) {

    fun <T> stubRequest(stub: (WireMockServer) -> T) {
        WireMock.configureFor("localhost", wireMockServer.port())
        stub(wireMockServer)
    }

}