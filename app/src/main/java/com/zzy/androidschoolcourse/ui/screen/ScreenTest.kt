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
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.R

class ScreenTest:Screen {
    @Composable
    override fun Content() {
        val modifier = Modifier
        Column (modifier = modifier.fillMaxSize()){
            Text("This is Test Screen")
            Row (modifier = modifier.weight(1f)){
                ButtonYunSuan(modifier,id = R.drawable.add, onClick = false)
                ButtonYunSuan(modifier,id = R.drawable.minus, onClick = false)
                ButtonYunSuan(modifier,id = R.drawable.multiply, onClick = false)
            }
        }
    }

    @Composable
    fun ButtonYunSuan(modifier:Modifier = Modifier,id:Int,onClick:Boolean){
        Button(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = id), contentDescription = "")
        }
    }
}
