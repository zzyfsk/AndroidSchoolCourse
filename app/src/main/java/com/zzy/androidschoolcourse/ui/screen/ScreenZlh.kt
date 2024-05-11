package com.zzy.androidschoolcourse.ui.screen



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
class ScreenZlh : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                    .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    Column {
                        CircularImageButton(size = 200.dp,imageId = ResourceConstants.MINUS_IMAGE_ID)
                        CircularImageButton(size = 200.dp,imageId = ResourceConstants.DIVIDE_IMAGE_ID)
                    }
                    Column {
                        CircularImageButton(size = 200.dp,imageId = ResourceConstants.MINUS_IMAGE_ID)
                        CircularImageButton(size = 200.dp,imageId = ResourceConstants.DIVIDE_IMAGE_ID)
                    }
                }
            }
            Row(modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularImageButton(size = 100.dp,imageId = ResourceConstants.ADD_IMAGE_ID)
                CircularImageButton(size = 100.dp,imageId = ResourceConstants.DEL_IMAGE_ID)
                CircularImageButton(size = 100.dp,imageId = ResourceConstants.MINUS_IMAGE_ID)
                CircularImageButton(size = 100.dp,imageId = ResourceConstants.DIVIDE_IMAGE_ID)
            }
            Row(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {
                CircularImageButton(imageId = ResourceConstants.ADD_IMAGE_ID)
                CircularImageButton(imageId = ResourceConstants.ADD_IMAGE_ID)
                CircularImageButton(imageId = ResourceConstants.ADD_IMAGE_ID)
            }
        }
    }


}