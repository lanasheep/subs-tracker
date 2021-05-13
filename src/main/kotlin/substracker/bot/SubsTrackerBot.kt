package substracker.bot

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import substracker.repository.SubscriptionRepository
import substracker.repository.UserRepository

@Component
class SubsTrackerBot(@Value("\${bot.token}") private val token: String,
                     @Value("\${bot.username}") private val username: String,
                     @Value("\${telegram.url}") telegramUrl: String,
                     private val subscriptionRepository: SubscriptionRepository,
                     private val userRepository: UserRepository) : TelegramLongPollingBot(defaultBotOptions(telegramUrl)) {

    override fun getBotToken(): String = token

    override fun getBotUsername(): String = username

    override fun onUpdateReceived(update: Update?) {
        if (update != null && update.hasMessage() && update.getMessage().hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            val userId = update.message.from.id
            val login = update.message.from.userName
            when {
                messageText.trim() == "/register" -> {
                    userRepository.add(userId, login)
                    execute(SendMessage().setChatId(chatId).setText("ok"))
                }
                messageText.startsWith("/add") -> {
                    val title = messageText.substringAfter(" ").substringBefore(" ")
                    val cost = messageText.substringAfter(" ").substringAfter(" ").toInt()
                    subscriptionRepository.add(userId, title, cost)
                    execute(SendMessage().setChatId(chatId).setText("successful!"))
                }
                messageText.startsWith("/delete") -> {
                    val title = messageText.substringAfter(" ")
                    subscriptionRepository.delete(userId, title)
                    execute(SendMessage().setChatId(chatId).setText("successful!"))
                }
                messageText.startsWith("/update") -> {
                    val title = messageText.substringAfter(" ").substringBefore(" ")
                    val cost = messageText.substringAfter(" ").substringAfter(" ").toInt()
                    subscriptionRepository.update(userId, title, cost)
                    execute(SendMessage().setChatId(chatId).setText("successful!"))
                }
                messageText.startsWith("/list") -> {
                    val list = subscriptionRepository.selectAll()
                    val ans = list.map { "${it.title} ${it.cost}" }.joinToString(separator = "\n")
                    execute(SendMessage().setChatId(chatId).setText(ans))
                }
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