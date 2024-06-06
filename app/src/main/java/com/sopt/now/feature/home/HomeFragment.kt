package com.sopt.now.feature.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.setScrollTopOnReselect
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.feature.home.adapter.HomeMultiAdapter
import com.sopt.now.feature.model.HomeSealedItem
import com.sopt.now.feature.util.KeyStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun initView() {
        initMultiRecyclerView()
        scrollRecyclerViewToTop()
    }

    private fun scrollRecyclerViewToTop() {
        setScrollTopOnReselect(
            R.id.fragment_home,
            R.id.bnv_home,
            binding.rvFriends
        )
    }

    private fun initMultiRecyclerView() {
        val homeAdapter = HomeMultiAdapter(
            onProfileClicked = { navigateToMyPageFragment() },
            onFriendClicked = ::navigateToHomeDetailFragment
        ).apply {
            setFriendList(getHomeSealedItemList())
        }

        binding.rvFriends.run {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToMyPageFragment() {
        findNavController().navigate(
            R.id.action_fragment_home_to_fragment_my_page
        )
    }

    private fun navigateToHomeDetailFragment(friend: HomeSealedItem.Friend) {
        findNavController().navigate(
            R.id.action_fragment_home_to_fragment_home_detail,
            bundleOf(KeyStorage.USER_INPUT to friend)
        )
    }

    private fun getHomeSealedItemList(): MutableList<HomeSealedItem> {
        val homeItemsList = mutableListOf<HomeSealedItem>()

        homeItemsList.addAll(viewModel.mockProfileList)
        addBirthList(homeItemsList)
        addFriendList(homeItemsList)

        return homeItemsList
    }

    private fun addBirthList(homeItemsList: MutableList<HomeSealedItem>) {
        homeItemsList.add(HomeSealedItem.TitleLine("생일인 친구"))
        val filteredBirthList = viewModel.filterAndSortBirthList(viewModel.mockBirthList)
        homeItemsList.addAll(filteredBirthList)
    }

    private fun addFriendList(homeItemsList: MutableList<HomeSealedItem>) {
        homeItemsList.add(HomeSealedItem.TitleLine("친구"))
        homeItemsList.addAll(viewModel.mockBirthList)
    }
}
