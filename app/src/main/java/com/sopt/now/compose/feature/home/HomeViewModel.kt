package com.sopt.now.compose.feature.home

import androidx.lifecycle.ViewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.model.HomeSealedItem
import com.sopt.now.compose.feature.util.DateUtils
import com.sopt.now.compose.feature.util.DateUtils.today
import com.sopt.now.compose.feature.util.DateUtils.yesterday
import java.time.LocalDate

class HomeViewModel : ViewModel() {

    val mockProfileList = HomeSealedItem.MyProfile(
        myProfileImg = R.drawable.ic_sign_up_profile_person,
        myName = "배찬우",
        myDescription = "상태메시지 +"
    )

    val mockBirthList = listOf<HomeSealedItem.Friend>(
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "주효은",
            selfDescription = "안녕 난 쿼캬야",
            date = LocalDate.of(2024, 10, 28)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_home_ghost_fill_green,
            name = "김명석",
            selfDescription = "컴포즈는 나에게",
            date = LocalDate.of(2024, 10, 28)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_my_page_profile_3x,
            name = "유정현",
            selfDescription = "안녕안녕",
            date = LocalDate.of(2024, 10, 28)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "김언지",
            selfDescription = "ㅁㅁㅁㅁ",
            date = LocalDate.of(2024, 4, 20)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "박동민",
            selfDescription = "꼼짝마 손들어zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
            date = LocalDate.of(2001, 10, 28)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "이유빈",
            selfDescription = "ㅁㅁㅁㅁ",
            date = LocalDate.of(2024, 4, 19)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "우상 욱함",
            selfDescription = "손흠민 닮음",
            date = LocalDate.of(2024, 4, 18)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "배로로",
            selfDescription = "안녕 난 케로로야",
            date = LocalDate.of(2024, 4, 18)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "파트장",
            selfDescription = "강남 오면 밥사줌",
            date = LocalDate.of(2024, 4, 18)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "최준서",
            selfDescription = "노래 불러주세요",
            date = LocalDate.of(2024, 4, 18)
        )
    )

    fun filterAndSortBirthList(birthList: List<HomeSealedItem.Friend>): List<HomeSealedItem.Friend> =
        birthList.filter { friendList ->
            friendList.date == today || friendList.date == yesterday
        }.sortedBy { friendList ->
            DateUtils.getDateOrder(friendList.date)
        }
}
