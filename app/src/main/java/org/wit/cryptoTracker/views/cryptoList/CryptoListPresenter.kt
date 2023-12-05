package org.wit.cryptoTracker.views.cryptoList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.cryptoTracker.activities.CryptoMapsActivity
import org.wit.cryptoTracker.main.MainApp
import org.wit.cryptoTracker.models.CryptoModel
import org.wit.cryptoTracker.views.crypto.CryptoView

class CryptoListPresenter(val view: CryptoListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getCryptos() = app.cryptos.findAll()

    fun doAddCrypto() {
        val launcherIntent = Intent(view, CryptoView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditCrypto(crypto: CryptoModel, pos: Int) {
        val launcherIntent = Intent(view, CryptoView::class.java)
        launcherIntent.putExtra("crypto_edit", crypto)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowCryptosMap() {
        val launcherIntent = Intent(view, CryptoMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }
    }
}