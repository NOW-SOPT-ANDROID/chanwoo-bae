package com.sopt.now.compose.component.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageTitle(title: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = title,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TitleWithDivider(title: String) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 3.dp),
        color = Color(0xFFE0E0E0)
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF7F7F7F)
    )
}
