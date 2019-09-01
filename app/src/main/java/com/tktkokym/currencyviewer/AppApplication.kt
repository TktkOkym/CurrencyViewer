package com.tktkokym.currencyviewer

import android.app.Application
import android.os.StrictMode
import com.tktkokym.currencyviewer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork() // or .detectAll() for all detectable problems
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@AppApplication)
            modules(appModule)
        }
    }
}