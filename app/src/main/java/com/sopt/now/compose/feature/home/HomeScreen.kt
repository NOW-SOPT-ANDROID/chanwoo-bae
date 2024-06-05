package com.sopt.now.compose.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.component.row.BirthRow
import com.sopt.now.compose.ui.component.row.FriendRowOnCard
import com.sopt.now.compose.ui.component.row.ProfileRow
import com.sopt.now.compose.ui.component.text.TitleWithDivider
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@OptIn(ExperimentalFoundationApi::class)
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
            val groupList = mockList.groupBy { it.date }

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

            groupList.forEach { (date, lists) ->
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .background(Color(0xFFE0E0E0))
                            .padding(horizontal = 6.dp)
                            .fillMaxWidth(),
                        text = date.toString(),
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }

                items(lists) { currentItem ->
                    FriendRowOnCard(data = currentItem)
                }
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
