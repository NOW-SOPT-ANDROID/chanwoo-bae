package com.sopt.now.compose.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
