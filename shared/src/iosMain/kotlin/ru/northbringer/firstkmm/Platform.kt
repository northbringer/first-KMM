package ru.northbringer.firstkmm

import com.squareup.sqldelight.db.SqlDriver
import platform.UIKit.UIDevice
import ru.northbringer.firstkmm.db.AppDatabase

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "app.db")
    }
}