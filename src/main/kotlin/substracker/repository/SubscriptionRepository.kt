package substracker.repository

import substracker.db.Tables.SUBSCRIPTION
import substracker.model.Subscription
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class SubscriptionRepository(private val context: DSLContext) {
    open fun selectAll(): List<Subscription> {
        return context.selectFrom(SUBSCRIPTION).fetchInto(Subscription::class.java)
    }
    open fun selectByUserId(userId: Int): List<Subscription> {
        return context.selectFrom(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(userId)).fetchInto(Subscription::class.java)
    }
    open fun add(userId: Int, title: String, cost: Int) {
        context.insertInto(SUBSCRIPTION, SUBSCRIPTION.USER_ID, SUBSCRIPTION.TITLE, SUBSCRIPTION.COST)
               .values(userId, title, cost).execute()
    }
    open fun delete(userId: Int, title: String) {
        context.deleteFrom(SUBSCRIPTION).where(SUBSCRIPTION.USER_ID.eq(userId).and(SUBSCRIPTION.TITLE.eq(title))).execute()
    }
    open fun update(userId: Int, title: String, cost: Int) {
        context.update(SUBSCRIPTION).set(SUBSCRIPTION.COST, cost)
               .where(SUBSCRIPTION.USER_ID.eq(userId).and(SUBSCRIPTION.TITLE.eq(title))).execute()
    }
}