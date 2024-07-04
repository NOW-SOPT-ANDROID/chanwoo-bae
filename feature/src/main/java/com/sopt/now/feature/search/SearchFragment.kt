package com.sopt.now.feature.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.sopt.now.core_ui.base.BindingFragment
import com.sopt.now.core_ui.util.fragment.snackBar
import com.sopt.now.core_ui.util.fragment.toast
import com.sopt.now.core_ui.util.fragment.viewLifeCycle
import com.sopt.now.core_ui.util.fragment.viewLifeCycleScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()

    private var _reqresAdapter: ReqresAdapter? = null
    private val reqresAdapter: ReqresAdapter
        get() = requireNotNull(_reqresAdapter)

    override fun initView() {
        initReqresRecyclerView()
        initObserveReqresState()
    }

    private fun initReqresRecyclerView() {
        _reqresAdapter = ReqresAdapter().apply {
            submitList(emptyList())
        }
        binding.rvReqres.adapter = reqresAdapter
    }

    private fun initObserveReqresState() {
        viewModel.reqresUserState.flowWithLifecycle(viewLifeCycle).onEach { state ->
            when (state) {
                is UiState.Success -> reqresAdapter.submitList(state.data)
                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                is UiState.Loading -> toast("로딩중")
            }
        }.launchIn(viewLifeCycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _reqresAdapter = null
    }
}
