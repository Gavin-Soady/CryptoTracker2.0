package org.wit.cryptoTracker.ui.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.cryptoTracker.models.CryptoModel


class CryptoViewModel : ViewModel() {

    private val donationsList = MutableLiveData<List<CryptoModel>>()

    val observableCryptoList: LiveData<List<CryptoModel>>
        get() = donationsList

    init {
        load()
    }

  fun load() {
    //    donationsList.value = CryptoManager.findAll()
    }
}