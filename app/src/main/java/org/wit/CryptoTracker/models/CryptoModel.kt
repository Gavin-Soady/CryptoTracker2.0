package org.wit.CryptoTracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoModel(var id: Long = 0,
                       var title: String = "",
                       var description: String = "") : Parcelable