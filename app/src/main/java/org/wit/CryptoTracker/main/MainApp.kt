package org.wit.CryptoTracker.main

import android.app.Application
import org.wit.CryptoTracker.models.CryptoMemStore
import org.wit.CryptoTracker.models.CryptoJSONStore
import org.wit.CryptoTracker.models.CryptoStore
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