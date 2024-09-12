package org.benedetto.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Cat(
    @SerializedName("id") val id: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("width") val width: Int = 0,
    @SerializedName("height") val height: Int = 0
) : Parcelable