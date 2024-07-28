package com.zzy.androidschoolcourse.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

/**
 *  依赖voyager
 */

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current


     NavigationBarItem(selected = tabNavigator.current.key == tab.key,
         onClick = { tabNavigator.current = tab },
         icon = { 
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Icon(painter = tab.options.icon!!, contentDescription = tab.options.title)
                 Text(text = tab.options.title)
             }
         
         })
}



