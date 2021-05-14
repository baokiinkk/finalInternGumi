package com.example.finalinterngumi

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.multidex.MultiDex
import com.example.finalinterngumi.di.RepositoryDi
import com.example.finalinterngumi.di.apiModule
import com.example.finalinterngumi.di.blankViewmodelDi
import com.example.finalinterngumi.di.retrofitModule

class GumiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GumiApplication)
            modules(listOf(
                RepositoryDi,
                blankViewmodelDi,
                apiModule,
                retrofitModule
            ))
        }
    }

}