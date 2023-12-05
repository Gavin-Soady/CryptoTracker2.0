package org.wit.cryptoTracker.main

import android.app.Application
import org.wit.cryptoTracker.models.CryptoJSONStore
import org.wit.cryptoTracker.models.CryptoStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    //val cryptos = ArrayList<CryptoModel>()
    //val cryptos = CryptoMemStore()

    lateinit var cryptos: CryptoStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        cryptos = CryptoJSONStore(applicationContext)

        i("CryptoTracker started")
//        cryptos.add(CryptoModel("One", "About one..."))
//        cryptos.add(CryptoModel("Two", "About two..."))
//        cryptos.add(CryptoModel("Three", "About three..."))
    }
}