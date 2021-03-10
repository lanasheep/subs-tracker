import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class SubsTrackerBot : TelegramLongPollingBot() {

    override fun getBotToken(): String = ""

    override fun getBotUsername(): String = ""

    override fun onUpdateReceived(update: Update?) {
        if (update != null && update.hasMessage() && update.getMessage().hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            when (messageText) {
                "/ping" -> execute(SendMessage().setChatId(chatId).setText("pong"))
                else -> execute(SendMessage().setChatId(chatId).setText("undefined command"))
            }
        }
    }
}