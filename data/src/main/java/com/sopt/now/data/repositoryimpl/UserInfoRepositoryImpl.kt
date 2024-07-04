package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.local.SharedPreferenceDataSource
import com.sopt.now.data.mapper.toUserEntity
import com.sopt.now.data_local.dto.UserDto
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : UserInfoRepository {
    override fun saveUserInfo(user: UserEntity) {
        sharedPreferenceDataSource.saveUserInfo(
            userDto = UserDto(
                id = user.id,
                password = user.password.toString(),
                nickName = user.nickName,
                phone = user.phone
            )
        )
    }

    override fun saveCheckLogin(isChecked: Int) {
        sharedPreferenceDataSource.memberId = isChecked
    }

    override fun getUserInfo(): UserEntity {
        return sharedPreferenceDataSource.getUserInfo().toUserEntity()
    }

    override fun getCheckLogin(): Int {
        return sharedPreferenceDataSource.memberId
    }

    override fun clear() {
        sharedPreferenceDataSource.clear()
    }
}
