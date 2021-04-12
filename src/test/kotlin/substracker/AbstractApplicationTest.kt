package substracker

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.telegram.telegrambots.ApiContextInitializer
import org.testcontainers.containers.PostgreSQLContainer
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import substracker.test.config.WebServiceStubConfiguration
import substracker.test.stubs.TelegramApiStub
import substracker.test.utils.NetUtils
import java.time.Duration
import java.util.*
import javax.sql.DataSource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [ApplicationTestConfiguration::class, WebServiceStubConfiguration::class])
@ActiveProfiles("test")
abstract class AbstractApplicationTest : AbstractTestNGSpringContextTests() {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    protected lateinit var telegramApiStub: TelegramApiStub

    @BeforeSuite
    fun beforeSuite() {
        System.setProperty(
            "TEST_DB_URL",
            Postgres.jdbcUrl.replace(Postgres.databaseName, CoreDbName)
        )
        System.setProperty(
            "TEST_DB_USER",
            CoreDbUser
        )
        System.setProperty(
            "TEST_DB_PWD",
            CoreDbPassword
        )

        System.setProperty(
                WebServiceStubConfiguration.TEST_WEB_SERVICE_STUB_PORT_PROPERTY,
                NetUtils.getFreeRandomLocalPort().toString()
        )

        ApiContextInitializer.init()
    }

    @AfterSuite
    fun afterSuite() {
        Postgres.stop()
    }

    companion object {
        private const val RootDbUser = "root"
        private const val RootDbPassword = "root"

        private const val CoreDbName = "core"
        private const val CoreDbUser = "test"
        private val CoreDbPassword = UUID.randomUUID().toString().replace("-", "")

        private val Postgres: MyPostgresContainer = MyPostgresContainer()
            .withStartupTimeout(Duration.ofMinutes(1L))
            .withUsername(RootDbUser)
            .withPassword(RootDbPassword)

        init {
            Postgres.start()
            JdbcTemplate(Postgres.getRootDataSource())
                .execute("""
                    CREATE DATABASE "$CoreDbName";
                    CREATE USER "$CoreDbUser" WITH ENCRYPTED PASSWORD '$CoreDbPassword';
                    GRANT ALL PRIVILEGES ON DATABASE "$CoreDbName" to "$CoreDbUser";
                """.trimIndent())
        }

        private fun <SELF: PostgreSQLContainer<SELF>> PostgreSQLContainer<SELF>.getRootDataSource(): DataSource =
            dataSource<HikariDataSource> {
                org.springframework.boot.jdbc.DataSourceBuilder.create()
                    .type(com.zaxxer.hikari.HikariDataSource::class.java)
                    .driverClassName(driverClassName)
                    .url(jdbcUrl)
                    .username(RootDbUser)
                    .password(RootDbPassword)
                    .build()
            }.also {
                it.connectionTestQuery = "SELECT 1"
            }

        private inline fun <reified T> dataSource(dataSourceBuilder: () -> DataSource): T = dataSourceBuilder() as T
    }

    private class MyPostgresContainer : PostgreSQLContainer<MyPostgresContainer>()

}