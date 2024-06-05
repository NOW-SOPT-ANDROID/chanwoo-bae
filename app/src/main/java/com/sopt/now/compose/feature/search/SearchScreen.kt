package com.sopt.now.compose.feature.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.ui.component.row.ReqresDummyRow
import com.sopt.now.compose.ui.core.view.UiState

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = viewModel()
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        viewModel.getReqresList(1)
    }

    val reqresListState by viewModel.getReqresListState.collectAsStateWithLifecycle(lifecycle)

    val reqresList = when (val reqresListState = reqresListState) {
        is UiState.Success -> reqresListState.data
        is UiState.Failure -> {
            Toast.makeText(
                context,
                reqresListState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            null
        }

        UiState.Loading -> {
            Toast.makeText(context, "로딩중", Toast.LENGTH_SHORT).show()
            null
        }
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (reqresList == null) {
                return@LazyColumn
            }
            items(reqresList) { reqresList ->
                ReqresDummyRow(data = reqresList)
            }
        }
    }
}
