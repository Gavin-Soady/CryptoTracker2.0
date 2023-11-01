package org.wit.CryptoTracker.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoModel(   var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var image: Uri = Uri.EMPTY,
                          var amount: Long = 0,
                          var value: Long = 0,
                          var total: Long = 0) : Parcelable
