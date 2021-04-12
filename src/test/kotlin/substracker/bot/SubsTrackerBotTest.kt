package substracker.bot

import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.objects.Update
import org.testng.annotations.Test
import substracker.AbstractApplicationTest

class SubsTrackerBotTest : AbstractApplicationTest() {

    @Autowired
    private lateinit var bot: SubsTrackerBot

    @Test
    fun `should send 'pong' message when 'ping' command received`() {
        // given
        val update = Update()

        telegramApiStub.stubResponse(".*", "{}")

        // when
        bot.onUpdateReceived(update)

        // then

    }

}