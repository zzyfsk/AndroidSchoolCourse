package com.zzy.androidschoolcourse.net.socket.game

import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketGame
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.net.socket.bean.GameSocketState
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ServiceGame {
    private lateinit var server: ServerGame
    private lateinit var controller: ClientGame
    private lateinit var client: ClientGame

    fun serverStart() {
        server = ServerGame()
        server.start()
    }

    fun controllerStart(ip:String,port:Int,onMessage:(String)->Unit){
        controller = ClientGame()
        controller.start(ip, port){ type,message->
            when(type){
                GameSocketState.Function -> TODO()
                GameSocketState.Message -> {
                    onMessage(message)
                }
                GameSocketState.Right -> TODO()
                GameSocketState.Exit -> {}
            }
        }
        controller.setRight(GameRight.Command)
    }

    fun clientStart(ip:String,port:Int,onMessage: (String) -> Unit){
        client = ClientGame()
        client.start(ip, port){type,message->
            when(type){
                GameSocketState.Function -> TODO()
                GameSocketState.Message -> {
                    onMessage(message)
                }
                GameSocketState.Right -> TODO()
                GameSocketState.Exit -> TODO()
            }

        }
    }

    fun sendGameState(gameState:TwentyFourGameState,right: GameRight){
        if (right == GameRight.Client){
            client.sendMessage(BeanSocketGame(
                GameSocketState.Message,
                Json.encodeToString(gameState),
                GameRight.Client
            ))
        }else if (right == GameRight.Command){
            controller.sendMessage(BeanSocketGame(
                GameSocketState.Message,
                Json.encodeToString(gameState),
                GameRight.Command
            ))
        }
    }
}