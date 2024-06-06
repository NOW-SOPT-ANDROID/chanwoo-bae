package com.sopt.now.domain.usecase.regex

import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): String {
        return when {
            !isLengthValid(password) -> "비밀번호는 8자에서 12자 사이여야 합니다."
            !containsAlphabet(password) -> "비밀번호는 적어도 하나의 알파벳을 포함해야 합니다."
            !containsDigit(password) -> "비밀번호는 적어도 하나의 숫자를 포함해야 합니다."
            !containsSpecialCharacter(password) -> "비밀번호는 적어도 하나의 특수 문자를 포함해야 합니다."
            else -> PASSWORD_CORRECT
        }
    }

    private fun containsDigit(password: String): Boolean =
        password.any { it.isDigit() }


    private fun containsAlphabet(password: String): Boolean =
        password.any { it.isLetter() }


    private fun containsSpecialCharacter(password: String) =
        password.any { it.toString().matches(SPECIAL_CHARACTER_REGEX) }


    private fun isLengthValid(password: String): Boolean =
        password.length in MIN_LENGTH..MAX_LENGTH


    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 12
        const val PASSWORD_CORRECT = "유효한 비밀번호"
        private const val SPECIAL_REGEX_PATTERN = "[^a-zA-Z0-9]"
        private val SPECIAL_CHARACTER_REGEX = Regex(SPECIAL_REGEX_PATTERN)
    }
}
