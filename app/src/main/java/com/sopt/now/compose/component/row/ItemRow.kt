package com.sopt.now.compose.component.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sopt.now.compose.feature.model.HomeSealedItem
import com.sopt.now.compose.feature.model.ReqresEntity
import com.sopt.now.compose.feature.util.DateUtils
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun FriendRowOnCard(
    modifier: Modifier = Modifier,
    data: HomeSealedItem.Friend,
    content: @Composable RowScope.() -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f),
                painter = painterResource(id = data.profileImage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier
                    .padding(horizontal = 16.dp),
                text = data.name,
                fontSize = 14.sp,
                color = Color(0xFF0E0E0E)
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                text = data.selfDescription,
                fontSize = 12.sp,
                color = Color(0xFF0E0E0E),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF60D5A4),
                        shape = RoundedCornerShape(50.dp)
                    ) // 외곽선 추가
                    .padding(horizontal = 12.dp, vertical = 4.dp) // 외곽선과 텍스트 사이 간격 조정
            )
        }
    }
}

@Composable
fun BirthRow(
    modifier: Modifier = Modifier,
    data: HomeSealedItem.Friend,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .aspectRatio(1f),
            painter = painterResource(id = data.profileImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier
                .padding(horizontal = 16.dp),
            text = data.name,
            fontSize = 14.sp,
            color = Color(0xFF0E0E0E)
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = DateUtils.getDateString(data.date),
            fontSize = 12.sp,
            color = Color(0xFF0E0E0E),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE47178),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ReqresDummyRow(
    data: ReqresEntity
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = data.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .aspectRatio(1f)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "${data.firstName} ${data.lastName}",
            fontSize = 14.sp,
            color = Color(0xFF0E0E0E)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data.email,
            fontSize = 12.sp,
            color = Color(0xFF0E0E0E),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE47178),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ProfileRow(
    modifier: Modifier = Modifier,
    data: HomeSealedItem.MyProfile
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = modifier
                .size(64.dp)
                .clip(CircleShape)
                .aspectRatio(1f),
            painter = painterResource(id = data.myProfileImg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier
                .padding(horizontal = 16.dp),
            text = data.myName,
            fontSize = 16.sp,
            color = Color(0xFF0E0E0E)
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = data.myDescription,
            fontSize = 12.sp,
            color = Color(0xFF0E0E0E),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewHome() {
    NOWSOPTAndroidTheme {
//        FriendRowOnCard()
    }
}
