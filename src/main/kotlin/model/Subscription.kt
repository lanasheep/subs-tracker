import java.util.UUID

data class Subscription(
    val id: UUID,
    val title: String,
    val cost: Double
)