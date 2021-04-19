package substracker.bot

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

@Component
class SubsTrackerBot(@Value("\${bot.token}") private val token: String,
                     @Value("\${bot.username}") private val username: String,
                     @Value("\${telegram.url}") telegramUrl: String) : TelegramLongPollingBot(defaultBotOptions(telegramUrl)) {

    override fun getBotToken(): String = token

    override fun getBotUsername(): String = username

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

    override fun clearWebhook() {
        try {
            super.clearWebhook()
        } catch (e: TelegramApiRequestException) {
            // ignore this
        }

    }

    companion object {

        fun defaultBotOptions(telegramUrl: String): DefaultBotOptions {
            val options = DefaultBotOptions()
            options.baseUrl = telegramUrl
            return options
        }

    }
}