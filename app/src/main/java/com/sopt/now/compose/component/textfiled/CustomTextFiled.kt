package com.sopt.now.compose.component.textfiled

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun CustomTextField(
    hint: String
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        label = { Text(hint) },
        singleLine = true
    )
}

@Composable
fun CustomTextFieldWithTitle(
    title: String,
    hint: String
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            value = text,
            onValueChange = { text = it },
            label = { Text(hint) },
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFiledPreview() {
    NOWSOPTAndroidTheme {
        CustomTextFieldWithTitle("ID", "이름을 입력해주세요")
    }
}
