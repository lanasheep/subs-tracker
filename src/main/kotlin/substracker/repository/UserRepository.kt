package substracker.repository

import substracker.db.Tables.USER
import substracker.model.User
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class UserRepository(private val context: DSLContext) {
    open fun selectAll(): List<User> {
        return context.selectFrom(USER).fetchInto(User::class.java)
    }
}