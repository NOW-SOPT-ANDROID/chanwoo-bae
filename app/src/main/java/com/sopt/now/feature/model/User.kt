package com.sopt.now.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val password: String,
    val nickName: String,
    val mbti: String
) : Parcelable
