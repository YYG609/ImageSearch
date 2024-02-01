package com.android.imagesearch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedItem(
    val thumbnail: String,
    val source: String,
    val time: String,
) : Parcelable
