package com.sopt.now.compose.component.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextRowPair(
    modifier: Modifier = Modifier,
    name1: String,
    name2: String,
    fontSize: TextUnit = 20.sp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name1,
            fontSize = fontSize,
            color = Color(0xFF0E0E0E)
        )
        Text(
            text = name2,
            fontSize = fontSize,
            color = Color(0xFFA3A3A6)

        )
    }
}
