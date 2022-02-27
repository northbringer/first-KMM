package ru.northbringer.firstkmm

import com.squareup.sqldelight.db.SqlDriver
import ru.northbringer.firstkmm.db.AppDatabase


expect class Platform() {
    val platform: String
}

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class AccountDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())

    private val dbQuery = database.appDatabaseQueries

    fun createNewAccount(login: String, password: String) {
        dbQuery.insertAccount(login, password)
    }

    fun deleteAllAccounts() {
        dbQuery.deleteAllAccounts()
    }

    fun getAllAccounts(): List<Account?> {
        return dbQuery.selectAllAccounts().executeAsList()
    }

    fun getAccountByLogin(login: String): Account? {
        return dbQuery.selectAccountByLogin(login).executeAsOneOrNull()
    }

}