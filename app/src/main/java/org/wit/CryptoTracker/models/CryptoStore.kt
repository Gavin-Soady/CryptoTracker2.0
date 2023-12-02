package org.wit.CryptoTracker.models

import org.wit.CryptoTracker.models.CryptoModel

interface CryptoStore {
    //abstract val crypto: CryptoModel

    fun findAll(): List<CryptoModel>
    fun create(crypto: CryptoModel)
    fun update(crypto: CryptoModel)
    fun delete(crypto: CryptoModel)
    fun findById(id:Long) : CryptoModel?

}