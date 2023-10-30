package org.wit.CryptoTracker.main

import android.app.Application
import org.wit.CryptoTracker.models.CryptoMemStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    //val cryptos = ArrayList<CryptoModel>()
    val cryptos = CryptoMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("CryptoTracker started")
//        cryptos.add(CryptoModel("One", "About one..."))
//        cryptos.add(CryptoModel("Two", "About two..."))
//        cryptos.add(CryptoModel("Three", "About three..."))
    }
}