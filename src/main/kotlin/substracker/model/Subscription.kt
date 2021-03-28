package substracker.model

import java.util.UUID

data class Subscription(
    val id: UUID,
    val user_id: Int,
    val title: String,
    val cost: Double
)