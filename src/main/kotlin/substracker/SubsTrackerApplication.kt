package substracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.springframework.boot.runApplication

@SpringBootApplication
open class SubsTrackerApplication

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    runApplication<SubsTrackerApplication>(*args)
}