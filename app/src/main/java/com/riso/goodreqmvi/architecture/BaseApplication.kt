package com.riso.goodreqmvi.architecture

import android.app.Application
import com.riso.goodreqmvi.architecture.AppActivityManager
import timber.log.Timber
import javax.inject.Inject


class BaseApplication : Application() {

    companion object{
        lateinit var component: AppComponent
    }

    @Inject
    lateinit var appActivityManager: AppActivityManager

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
        appActivityManager = component.provideAppActivityManager()
        appActivityManager.registerActivityLifecycleHelper(this)
        Timber.plant(Timber.DebugTree())
    }
}