package com.realestate.app

import com.realestate.app.data.MuseumRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
}
