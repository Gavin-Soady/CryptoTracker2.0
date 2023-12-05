package org.wit.cryptoTracker.models

interface CryptoStore {
    //abstract val crypto: CryptoModel

    fun findAll(): List<CryptoModel>
    fun create(crypto: CryptoModel)
    fun update(crypto: CryptoModel)
    fun delete(crypto: CryptoModel)
    fun findById(id:Long) : CryptoModel?

}