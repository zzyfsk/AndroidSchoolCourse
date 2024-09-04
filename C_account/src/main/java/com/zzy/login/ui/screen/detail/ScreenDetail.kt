package com.zzy.login.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.base.koin.account.AccountViewModel
import org.koin.androidx.compose.koinViewModel

class ScreenDetail(val id: Long) : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            DetailViewModel()
        }
        val accountViewModel: AccountViewModel = koinViewModel()

        LaunchedEffect(key1 = id) {
            viewModel.init(accountViewModel = accountViewModel, id = id)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            UserInformation(user = viewModel.user)
        }
    }
    
    @Composable
    fun UserInformation(user: UserDetailHttp){

    }
}