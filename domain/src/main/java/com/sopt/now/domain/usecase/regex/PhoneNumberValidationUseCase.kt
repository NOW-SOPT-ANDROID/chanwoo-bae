package com.sopt.now.domain.usecase.regex

import javax.inject.Inject

class PhoneNumberValidationUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String): Boolean = phoneNumberRegex.matches(phoneNumber)

    companion object {
        private const val PHONE_NUMBER_PATTERN = "^010-[0-9]{4}-[0-9]{4}$"
        private val phoneNumberRegex = Regex(PHONE_NUMBER_PATTERN)
    }
}
