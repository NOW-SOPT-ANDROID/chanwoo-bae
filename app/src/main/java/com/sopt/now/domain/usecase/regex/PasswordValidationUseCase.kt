package com.sopt.now.domain.usecase.regex

import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean = passwordRegex.matches(password)

    companion object {
        private const val PASSWORD_REGEX_PATTERN =
            "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z\\d]).{8,12}"

        private val passwordRegex = Regex(PASSWORD_REGEX_PATTERN)
    }
}
