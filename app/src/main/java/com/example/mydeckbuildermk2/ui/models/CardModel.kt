package com.example.mydeckbuildermk2.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class CardModel  (var Id: Long = 0,
                       var name:  String = "",
                       var type: String = "",
                       var quantity: Int = 1
                       ) : Parcelable

