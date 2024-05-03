package com.sopt.now.compose.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.component.row.BirthRow
import com.sopt.now.compose.component.row.FriendRowOnCard
import com.sopt.now.compose.component.row.ProfileRow
import com.sopt.now.compose.component.text.TitleWithDivider
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val mockList = viewModel.mockBirthList

            item {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                ProfileRow(data = viewModel.mockProfileList)
                TitleWithDivider(title = stringResource(R.string.home_birth_friend))
            }

            items(viewModel.filterAndSortBirthList(mockList)) { item ->
                BirthRow(data = item)
            }
            item {
                TitleWithDivider(title = stringResource(R.string.home_firends))
            }
            itemsIndexed(mockList) { index, item ->
                FriendRowOnCard(data = item)
                if (index == mockList.lastIndex) Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewHome() {
    NOWSOPTAndroidTheme {
        HomeScreen()
    }
}
