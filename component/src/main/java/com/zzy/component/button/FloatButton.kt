package com.zzy.component.button

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun FloatButton(
    //控制是否展开的参数，又调用者提供和控制
    isExpand: Boolean = false,
    //展开的内容 1
    expandContent1: @Composable (() -> Unit)? = null,
    //展开的内容 2
    expandContent2: @Composable (() -> Unit)? = null,
    //展开的内容 3
    expandContent3: @Composable (() -> Unit)? = null,
    //按钮本体的点击事件
    onClick: () -> Unit
) =
    BoxWithConstraints(
        //利用一个 fillMaxSize 的 BoxWithConstraints 作为父布局以获取宽高
        Modifier.fillMaxSize()
    ) {
        val density = LocalDensity.current
        //为了比较好的 UI 效果，希望按钮四周加一个 margin
        val margin = 10.dp
        //按钮本体的 Size
        val buttonSize = 60.dp
        // 圆形
        val shape = RoundedCornerShape(50)
        //用于确定按钮的 X 坐标
        var x by remember {
            with(density) {
                //初始位置的 X 坐标
                val initX = margin.roundToPx().toFloat()
                mutableFloatStateOf(initX)
            }
        }
        //用于应用到按钮 Offset 的动画 state
        val goToSideX by animateFloatAsState(
            targetValue = x,
            animationSpec = spring(stiffness = Spring.StiffnessHigh),
            label = "goToSideX"
        )
        //用于确定按钮的 Y 坐标，由于 Y 方向不需要吸边动画，所以直接将该值应用到按钮的 Offset
        var y by remember {
            with(density) {
                //初始位置的 Y 坐标
                val initY = 20.dp.roundToPx().toFloat()
                mutableFloatStateOf(initY)
            }
        }
        //封装一个方法对象处理吸边逻辑
        val goToSide = {
            with(density) {
                //由于按钮的坐标在左上角处，所以实际上判断按钮 X 方向上的中心是否小于整体布局的宽的一半
                x = if (x < ((maxWidth - buttonSize).roundToPx() / 2f)) {
                    //小于则吸附到左边，即 X 坐标为 0
                    0f
                } else {
                    //大于则吸附到右边，即 X 坐标为整体布局的宽减掉按钮宽度和 margin
                    (maxWidth - buttonSize - margin * 2).roundToPx().toFloat()
                }
            }
        }

        //构建按钮的 Modifier
        val dragModifier = Modifier
            .padding(margin)
            .size(buttonSize)
            //将 X 坐标与 Y 坐标应用到 Offset 中
            .offset { IntOffset(goToSideX.roundToInt(), y.roundToInt()) }
            //触摸事件
            .pointerInput(Unit) {
                //监听 Drag 事件
                detectDragGestures(
                    onDrag = { _, amount ->
                        //将 X 与 Y 方向上的偏移量加到 x，y 上
                        x += amount.x
                        y += amount.y
                    },
                    //拖动结束以及拖动被取消时调用吸边逻辑
                    onDragCancel = goToSide,
                    onDragEnd = goToSide
                )
            }
            .shadow(5.dp, shape)
            .clip(shape)
            .background(Color(0x4F4F4F66))
            .clickable { onClick() }
        //按钮本体样式，随意修改成你需要的样子
        Box(modifier = dragModifier, contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .padding(3.dp)
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9), shape)
            )
            Box(
                Modifier
                    .padding(6.dp)
                    .fillMaxSize()
                    .background(Color.White, shape)
            )
            Icon(imageVector = Icons.Default.Settings, contentDescription = "")
        }

        //因为展开内容是可选项，所以当三个展开内容都为空时候无需展开逻辑
        if (expandContent1 != null || expandContent2 != null || expandContent3 != null) {
            val expandDirection = with(density) {
                //根据当前 X 坐标与整体布局的宽度的关系决定展开的方向
                //用 1 和 -1 作为 factor 参与 UI 运算
                if (x < ((maxWidth - buttonSize).roundToPx() / 2f)) 1 else -1
            }
            //展开的透明度，负责展开和收起的透明度动画
            val expandContentAlpha by animateFloatAsState(targetValue = if (isExpand) 1f else 0f,
                label = ""
            )
            //内容 1 的位置变化，与按钮边界的相对 Offset
            val expandContent1Offset by animateIntOffsetAsState(
                targetValue =
                //展开状态
                if (isExpand) IntOffset(expandDirection * 100, 100)
                //收起状态
                else IntOffset.Zero
            )
            //同内容 1
            val expandContent2Offset by animateIntOffsetAsState(
                targetValue =
                if (isExpand) IntOffset(expandDirection * 100, 0)
                else IntOffset.Zero
            )
            //同内容 1
            val expandContent3Offset by animateIntOffsetAsState(
                targetValue =
                if (isExpand) IntOffset(expandDirection * 100, -100)
                else IntOffset.Zero
            )
            //根据展开方向计算展开内容相对的对象是按钮的左边界还是右边界
            val mainButtonBorderOffset = with(density) {
                val factor = if (x < ((maxWidth - buttonSize).roundToPx() / 2f)) 1f else 0f
                IntOffset(
                    //左边界的 X 坐标即为当前 X 坐标，右边界的 X 坐标为当前 X 坐标加上按钮宽度
                    x.roundToInt() + (buttonSize * factor).roundToPx(),
                    //Y 方向上固定为当前按钮 Y 方向上的中心
                    y.roundToInt() + (buttonSize / 2).roundToPx()
                )
            }
            if (expandContent1 != null)
            //展示内容 1
                Box(modifier = Modifier
                    //处理位置
                    .offset { expandContent1Offset + mainButtonBorderOffset }
                    //处理透明度
                    .alpha(expandContentAlpha)
                ) {
                    expandContent1()
                }

            if (expandContent2 != null)
            //同内容 1
                Box(modifier = Modifier
                    .offset { expandContent2Offset + mainButtonBorderOffset }
                    .alpha(expandContentAlpha)
                ) {
                    expandContent2()
                }

            if (expandContent3 != null)
            //同内容 1
                Box(modifier = Modifier
                    .offset { expandContent3Offset + mainButtonBorderOffset }
                    .alpha(expandContentAlpha)
                ) {
                    expandContent3()
                }
        }
    }
