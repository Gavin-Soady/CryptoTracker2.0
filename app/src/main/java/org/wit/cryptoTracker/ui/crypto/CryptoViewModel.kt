package org.wit.cryptoTracker.ui.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.cryptoTracker.models.CryptoManager
import org.wit.cryptoTracker.models.CryptoModel


class CryptoViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addCrypto(crypto: CryptoModel) {
        status.value = try {
            CryptoManager.create(crypto)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}