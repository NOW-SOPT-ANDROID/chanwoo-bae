package com.sopt.now.feature.util

object KeyStorage {
    const val USER_INPUT = "user_input_list"
    const val TOTAL_PRESSED_TIME = 2000
}

object StringResources {
    const val SIGN_UP_REQUIRED = "회원가입을 먼저 진행해주세요."
    const val ID_PASSWORD_REQUIRED = "아이디와 비밀번호를 입력해주세요."
    const val INVALID_ID = "아이디가 일치하지 않습니다."
    const val INVALID_PASSWORD = "비밀번호가 일치하지 않습니다."
    const val ID_ERROR_MESSAGE = "아이디는 6자 이상 10자 이하로 입력해주세요."
    const val PASSWORD_ERROR_MESSAGE = "비밀번호는 8자 이상 12자 이하로 입력해주세요."
    const val NICKNAME_ERROR_MESSAGE = "닉네임은 1자 이상 입력해주세요."
    const val MBTI_ERROR_MESSAGE = "MBTI 양식에 맞게 입력해주세요."
}

object DateKeyStorage {
    const val TODAY = 0
    const val YESTERDAY = 1
    const val NOTHING = 2
}
