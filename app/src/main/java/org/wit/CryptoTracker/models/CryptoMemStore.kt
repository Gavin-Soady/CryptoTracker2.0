package org.wit.CryptoTracker.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class CryptoMemStore : CryptoStore {

    val cryptos = ArrayList<CryptoModel>()

    override fun findAll(): List<CryptoModel> {
        return cryptos
    }

    override fun create(crypto: CryptoModel) {
        crypto.id = getId()
        cryptos.add(crypto)
        logAll()
    }

    override fun update(crypto: CryptoModel) {
        var foundcrypto: CryptoModel? = cryptos.find { p -> p.id == crypto.id }
        if (foundcrypto != null) {
            foundcrypto.title = crypto.title
            foundcrypto.description = crypto.description
            logAll()
        }
    }

    private fun logAll() {
        cryptos.forEach { i("$it") }
    }
}