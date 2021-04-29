package substracker.test.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.MatchResult
import com.github.tomakehurst.wiremock.matching.ValueMatcher
import org.eclipse.jetty.util.BlockingArrayQueue
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import substracker.test.web.WebStubServer

class TelegramApiStub(private val stubServer: WebStubServer) {

    private val matchedRequests = BlockingArrayQueue<String>()

    private val sendMessageRequests = BlockingArrayQueue<SendMessage>()

    fun stubSendMessageOk(): TelegramApiStub {
        val requestMatcher = ValueMatcher<Request> {
            val requestBody = it.bodyAsString
            matchedRequests.add(requestBody)
            sendMessageRequests.add(parseSendMessageRequest(requestBody))
            MatchResult.exactMatch()
        }
        val request =
            post(urlMatching("/telegram/sendmessage"))
                .andMatching(requestMatcher)
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("""{ "ok": "true" }""")
                )
        stubServer.stubRequest {
            it.stubFor(request)
        }
        return this
    }

    fun getMatcherRequestsCount(): Int = matchedRequests.count()

    fun hasSendMessageRequest(expectedText: String): Boolean {
        return sendMessageRequests.any { it.text == expectedText }
    }

    companion object {

        fun parseSendMessageRequest(text: String): SendMessage {
            return ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(text, SendMessage::class.java)
        }

    }

}