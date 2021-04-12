package substracker.test.web

import org.springframework.http.ResponseEntity
import java.time.ZonedDateTime

data class TracedResponse<T>(
        val beginTime: ZonedDateTime,
        val entity: ResponseEntity<T>,
        val endTime: ZonedDateTime
)
