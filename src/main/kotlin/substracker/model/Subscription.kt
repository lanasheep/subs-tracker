package substracker.model

import java.util.UUID

data class Subscription(
    val id: UUID,
    val userId: Int,
    val title: String,
    val cost: Double
)