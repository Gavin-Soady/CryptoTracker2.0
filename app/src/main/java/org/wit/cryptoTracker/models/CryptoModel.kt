package org.wit.cryptoTracker.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoModel(   var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var image: Uri = Uri.EMPTY,
                          var amount: Double = 0.00,
                          var value: Double = 0.00,
                          var total: Double = 0.00,
                          var lat: Double = 0.00,
                          var lng: Double = 0.00,
                          var zoom: Float = 0F ) : Parcelable
@Parcelize
data class LocationModel(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable