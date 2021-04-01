package substracker.repository

import substracker.db.Tables.SUBSCRIPTION
import substracker.model.Subscription
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
open class SubscriptionRepository(private val context: DSLContext) {
    open fun selectAll(): List<Subscription> {
        return context.selectFrom(SUBSCRIPTION).fetchInto(Subscription::class.java)
    }
    open fun selectByUserId(userId: Int): List<Subscription> {
        return context.selectFrom(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(userId)).fetchInto(Subscription::class.java)
    }
}