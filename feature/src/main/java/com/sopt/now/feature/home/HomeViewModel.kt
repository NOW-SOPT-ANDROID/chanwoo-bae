package com.sopt.now.feature.home

import androidx.lifecycle.ViewModel
import com.sopt.now.feature.R
import com.sopt.now.feature.model.HomeSealedItem
import com.sopt.now.feature.util.DateUtils
import com.sopt.now.feature.util.DateUtils.today
import com.sopt.now.feature.util.DateUtils.yesterday
import java.time.LocalDate

class HomeViewModel : ViewModel() {

    val mockProfileList = listOf<HomeSealedItem.MyProfile>(
        HomeSealedItem.MyProfile(
            myProfileImg = R.drawable.ic_sign_up_profile_person,
            myName = "배찬우",
            myDescription = "상태메시지 +"
        )
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
            date = LocalDate.of(2024, 4, 20)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_my_page_profile_3x,
            name = "유정현",
            selfDescription = "안녕안녕",
            date = LocalDate.of(2023, 10, 28)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "김언지",
            selfDescription = "말 뒤지게 안들음",
            date = LocalDate.of(2024, 4, 21)
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
            date = LocalDate.of(2024, 4, 17)
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
            date = LocalDate.of(2024, 4, 17)
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
            date = LocalDate.of(2024, 3, 24)
        ),
        HomeSealedItem.Friend(
            profileImage = R.drawable.ic_ghost_black_24,
            name = "손명지",
            selfDescription = "안녕하세 요띵 지에 오",
            date = LocalDate.of(2024, 4, 21)
        )
    )

    fun filterAndSortBirthList(birthList: List<HomeSealedItem.Friend>): List<HomeSealedItem.Friend> =
        birthList.filter { friendList ->
            friendList.date == today || friendList.date == yesterday
        }.sortedBy { friendList ->
            DateUtils.getDateOrder(friendList.date)
        }
}
