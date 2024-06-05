package com.sopt.now.compose.ui.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.data.repository.AuthRepositoryImpl
import com.sopt.now.compose.data.repository.ReqresRepositoryImpl
import com.sopt.now.compose.domain.ReqresUseCase
import com.sopt.now.compose.feature.auth.SignUpViewModel
import com.sopt.now.compose.feature.mypage.MyPageViewModel
import com.sopt.now.compose.feature.search.SearchViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                val repository =
                    ReqresUseCase(ReqresRepositoryImpl(ApiFactory.ServicePool.userService))
                SearchViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                val repository =
                    AuthRepositoryImpl(ApiFactory.ServicePool.authService)
                SignUpViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MyPageViewModel::class.java) -> {
                val repository =
                    AuthRepositoryImpl(ApiFactory.ServicePool.authService)
                MyPageViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
