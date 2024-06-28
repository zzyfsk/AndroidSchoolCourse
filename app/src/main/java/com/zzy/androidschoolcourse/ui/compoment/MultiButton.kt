package com.zzy.androidschoolcourse.ui.compoment

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberButton(
    modifier: Modifier = Modifier,
    number: Int = -1,
    numberUp: Int,
    numberDown: Int = 1,
    length: Int,
    fontSize: Int,
    backgroundColor: Color = Color.White,
    visible: Boolean = true,
    onClick: (Int) -> Unit
) {
    var touch by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (touch) (length + 10).dp else length.dp,
        label = ""
    )
    val alpha by animateFloatAsState(targetValue = if (visible) 100f else 0f, label = "")
    Column(
        modifier = modifier
            .size((length + 10).dp)
            .alpha(alpha = alpha),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(size)
                .height(size)
                .clip(RoundedCornerShape(10))
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(corner = CornerSize(10)),
                    clip = true
                )
                .background(color = backgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if (visible) {
                                Log.d("TAG", "NumberButton: onPress")
                                touch = true
                            }
//                            onClick(number)
                        },
                        onTap = {
                            if (visible) {
                                Log.d("TAG", "NumberButton: onTap")
                                touch = false
                                onClick(number)
                            }
                        },
                        onLongPress = {
                            if (visible) {
                                Log.d("TAG", "NumberButton: onLongPress")
                                touch = false
                            }
                        }
                    )
                    detectDragGestures(
                        onDrag = { _, _ ->
                            if (visible) {
                                touch = false
                                Log.d("TAG", "NumberButton: onDrag")
                            }
                        },
                        onDragStart = {
                            if (visible) {
                                touch = false
                                Log.d("TAG", "NumberButton: onDragStart")
                            }
                        },
                        onDragEnd = {
                            if (visible) {
                                touch = false
                                Log.d("TAG", "NumberButton: onDragEnd")
                            }
                        },
                        onDragCancel = {
                            if (visible) {
                                touch = false
                                Log.d("TAG", "NumberButton: onDragCancel")
                            }
                        }
                    )
//                    detectDragGesturesAfterLongPress(
//                        onDrag = { _, _ -> touch = false },
//                        onDragStart = { touch = false },
//                        onDragEnd = { touch = false },
//                        onDragCancel = { touch = false }
//                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (numberUp % numberDown == 0) {
                (numberUp / numberDown).toString().also {
                    Text(
                        modifier = Modifier,
                        text = it, fontSize = fontSize.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(800),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SymbolButton(
    modifier: Modifier = Modifier,
    number: Int = -1,
    imageId: Int,
    length: Dp,
    backgroundColor: Color = Color.White,
    symbolColor: Color = Color.Black,
    onClick: (Int) -> Unit
) {
    var touch by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (touch) (length + 5.dp) else length,
        label = ""
    )
    Column(
        modifier = modifier.size((length + 5.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(size)
                .height(size)
                .clip(RoundedCornerShape(100))
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(corner = CornerSize(100)),
                    clip = true
                )
                .background(color = backgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            touch = true
                        },
                        onTap = {
                            touch = false
                            onClick(number)
                        },
                        onLongPress = {
                            touch = false
                        }
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = imageId),
                contentDescription = "",
                tint = symbolColor
            )
        }
    }
}