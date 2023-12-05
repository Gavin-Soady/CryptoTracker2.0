package org.wit.cryptoTracker.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.cryptoTracker.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "cryptos.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<CryptoModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class CryptoJSONStore(private val context: Context) : CryptoStore {

    var cryptos = mutableListOf<CryptoModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<CryptoModel> {
        logAll()
        return cryptos
    }

     override fun findById(id:Long) : CryptoModel? {
        val foundPlacemark: CryptoModel? = cryptos.find { it.id == id }
        return foundPlacemark
    }

    override fun create(crypto: CryptoModel) {
        crypto.id = generateRandomId()
        cryptos.add(crypto)
        serialize()
    }


    override fun update(crypto: CryptoModel) {
        val cryptoList = findAll() as ArrayList<CryptoModel>
        var foundcrypto: CryptoModel? = cryptoList.find { p -> p.id == crypto.id }
        if (foundcrypto != null) {
            foundcrypto.title = crypto.title
            foundcrypto.description = crypto.description
            foundcrypto.value = crypto.value
            foundcrypto.amount = crypto.amount
            foundcrypto.total = crypto.total
            foundcrypto.image = crypto.image

            logAll()
        }
        serialize()
    }

    override fun delete(crypto: CryptoModel) {
        cryptos.remove(crypto)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(cryptos, listType)
        write(context, JSON_FILE, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        cryptos = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        cryptos.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}