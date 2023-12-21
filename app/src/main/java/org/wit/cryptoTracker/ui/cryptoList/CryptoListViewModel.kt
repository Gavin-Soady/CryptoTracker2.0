package org.wit.cryptoTracker.ui.cryptoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.cryptoTracker.adapters.CryptoAdapter
import org.wit.cryptoTracker.models.CryptoManager
import org.wit.cryptoTracker.models.CryptoModel
import org.wit.cryptoTracker.models.CryptoJSONStore
import org.wit.cryptoTracker.models.CryptoStore

class CryptoListViewModel : ViewModel() {

    private val cryptoList = MutableLiveData<List<CryptoModel>>()
    lateinit var cryptos: CryptoStore
    val observableCryptoList: LiveData<List<CryptoModel>>
        get() = cryptoList

    init {
        load()
    }

    fun load() {

       //cryptoList.value = CryptoManager.findAll()
        //cryptoList.value = cryptos.findAll()
    }
}