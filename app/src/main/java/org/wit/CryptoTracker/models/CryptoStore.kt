package org.wit.CryptoTracker.models

interface CryptoStore {
    fun findAll(): List<CryptoModel>
    fun create(crypto: CryptoModel)
    fun update(crypto: CryptoModel)
}