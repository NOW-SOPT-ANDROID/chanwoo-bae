package com.sopt.now.domain.usecase.regex

import javax.inject.Inject

class MbtiValidationUseCase @Inject constructor() {
    operator fun invoke(mbti: String): Boolean = mbtiRegex.matches(mbti)

    companion object {
        private const val MBTI_REGEX_PATTERN = "^[E|Iei][S|Nsn][T|Ftf][J|Pjp]$"
        private val mbtiRegex = Regex(MBTI_REGEX_PATTERN)
    }
}
