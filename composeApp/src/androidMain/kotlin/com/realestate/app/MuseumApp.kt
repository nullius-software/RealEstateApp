package com.realestate.app

import android.app.Application
import com.realestate.app.di.initKoin
import com.realestate.app.viewModel.DetailViewModel
import com.realestate.app.viewModel.ListViewModel
import org.koin.dsl.module

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { ListViewModel(get()) }
                    factory { DetailViewModel(get()) }
                }
            )
        )
    }
}
