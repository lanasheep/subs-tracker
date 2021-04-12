package substracker.test.web

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import java.time.ZonedDateTime

class TestWebClient(val restTemplate: TestRestTemplate) {

    inline fun <reified T> postForTracedEntity(
            uri: String,
            request: Any? = null
    ): TracedResponse<T> = postForTracedEntity(uri, emptyMap(), request)

    inline fun <reified T> postForTracedEntity(
            uri: String,
            headers: Map<String, String>,
            request: Any? = null
    ): TracedResponse<T> = TracedResponse(
            ZonedDateTime.now(),
            postForEntity(uri, headers, request),
            ZonedDateTime.now()
    )

    inline fun <reified T> postForEntity(
            uri: String,
            request: Any? = null
    ): ResponseEntity<T> = postForEntity(uri, emptyMap(), request)

    inline fun <reified T> postForEntity(
            uri: String,
            headers: Map<String, String>,
            request: Any? = null
    ): ResponseEntity<T> {
        val httpHeaders = HttpHeaders()
        headers.forEach { header, value ->
            httpHeaders.add(header, value)
        }
        val httpEntity = HttpEntity(request, httpHeaders)
        return restTemplate.postForEntity(uri, httpEntity, T::class.java)
    }

}