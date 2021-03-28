package substracker.model

import java.util.UUID

data class User(
    val id: UUID,
    val login: String,
)