package com.zzy.component.background

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import kotlin.math.sin


@Composable
fun CanvasWave(
    modifier: Modifier,
    width: Float
) {
    val deltaXAnim = rememberInfiniteTransition(label = "anime")
    val dx by deltaXAnim.animateFloat(
        initialValue = 0f,
        targetValue = width,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing =  LinearEasing)
        ), label = ""
    )

    Canvas(modifier = modifier,
        onDraw = {
                drawWaveGraph(
                    width=width,
                    centerY = 100f,
                    amplitude = 100f,
                    dx = dx
                )
        })
}

fun DrawScope.drawWaveGraph(
    width: Float,
    centerY: Float,
    amplitude: Float,
    dx:Float
) {
    val path = Path()
    for (x in 0..width.toInt()*2) {
        val y = (centerY - amplitude * sin(((x-dx)/width)*(2*Math.PI))).toFloat()
        if (x == 0) {
            path.moveTo(0f, y)
        } else {
            path.lineTo(x.toFloat(), y)
        }
        drawRect(color = Color.Black, topLeft = Offset(x.toFloat(), 0f),size = Size(1f,y))
    }
    drawLine(color = Color.Red, start = Offset(0f,0f), end = Offset(width,0f))
    drawLine(color = Color.Red, start = Offset(0f,amplitude*2), end = Offset(width,amplitude*2))
    drawPath(path, color = Color.Black, style = Stroke(width = 2f))
}
