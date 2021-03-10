import generated.jooq.Tables.SUBSCRIPTION
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import model.Subscription

@Repository
open class SubscriptionRepository(private val context: DSLContext) {
    open fun selectAll(): List<Subscription> = context.selectFrom(SUBSCRIPTION).fetch()
}