package org.wit.cryptoTracker.ui.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.cryptoTracker.main.MainApp
import org.wit.cryptoTracker.models.CryptoManager
import org.wit.cryptoTracker.models.CryptoModel
import org.wit.cryptoTracker.models.CryptoStore
import org.wit.cryptoTracker.views.crypto.CryptoView


class CryptoViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    private val view: CryptoFragment
        get() {
            TODO()
        }

    val observableStatus: LiveData<Boolean>
        get() = status


    fun addCrypto(crypto: CryptoModel) {
        status.value = try {
            CryptoStore.create(crypto)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}