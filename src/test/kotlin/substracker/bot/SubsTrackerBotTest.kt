package substracker.bot

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.objects.Update
import org.testng.Assert
import org.testng.annotations.Test
import substracker.AbstractApplicationTest

class SubsTrackerBotTest : AbstractApplicationTest() {

    @Autowired
    private lateinit var bot: SubsTrackerBot

    @Test
    fun `should send 'pong' message when 'ping' command received`() {
        // given
        val update = makeUpdateWithText("/ping")

        telegramApiStub.stubSendMessageOk()

        // when
        bot.onUpdateReceived(update)

        // then
        Assert.assertTrue(telegramApiStub.hasSendMessageRequest("pong"))
    }

    private fun makeUpdateWithText(text: String): Update {
        return ObjectMapper()
            .readValue<Update>(
                """{ "message": { "text": "$text", "chat": { "id": "11111111"} } }""",
                Update::class.java
            )
    }

}