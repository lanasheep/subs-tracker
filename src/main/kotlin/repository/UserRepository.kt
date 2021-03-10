import generated.jooq.Tables.USER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import model.User

@Repository
open class UserRepository(private val context: DSLContext) {
    open fun selectAll(): List<User> = context.selectFrom(USER).fetch()
}