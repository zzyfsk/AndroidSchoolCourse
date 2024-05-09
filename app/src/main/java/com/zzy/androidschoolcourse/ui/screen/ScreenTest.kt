package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.R

class ScreenTest:Screen {
    @Composable
    override fun Content() {
        Column (modifier = Modifier.fillMaxSize()){
            Text("This is Test Screen")
            Row (modifier = Modifier.weight(1f)){
                ButtonYunSuan(id = R.drawable.add, onClick = false)
                ButtonYunSuan(id = R.drawable.minus, onClick = false)
                ButtonYunSuan(id = R.drawable.multiply, onClick = false)
            }
        }
    }
    @Composable
    fun ButtonYunSuan(id:Int,onClick:Boolean){
        Button(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = id), contentDescription = "")
        }
    }
}


