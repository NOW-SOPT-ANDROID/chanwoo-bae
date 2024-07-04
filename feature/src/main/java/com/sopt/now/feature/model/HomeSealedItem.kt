package com.sopt.now.feature.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

sealed class HomeSealedItem {
    data class TitleLine(
        val title: String
    ) : HomeSealedItem()

    @Parcelize
    data class MyProfile(
        @DrawableRes val myProfileImg: Int,
        val myName: String,
        val myDescription: String
    ) : HomeSealedItem(), Parcelable

    @Parcelize
    data class Friend(
        @DrawableRes val profileImage: Int,
        val name: String,
        val date: LocalDate,
        val selfDescription: String
    ) : HomeSealedItem(), Parcelable
}
