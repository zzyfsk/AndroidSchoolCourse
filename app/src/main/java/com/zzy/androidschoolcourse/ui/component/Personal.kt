package com.zzy.androidschoolcourse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zzy.androidschoolcourse.ui.theme.ResIcon
import com.zzy.base.http.bean.UserDetailHttp

@Composable
fun PersonalPage(modifier: Modifier = Modifier,user: UserDetailHttp,score:Int) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = ResIcon.nonUserAvatar), contentDescription = "avatar")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        ) {
            
        }
    }
}