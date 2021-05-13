package substracker.model

data class Subscription(
    val id: Int,
    val userId: Int,
    val title: String,
    val cost: Int
)