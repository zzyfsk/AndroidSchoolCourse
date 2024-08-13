package com.zzy.component.flow.message

class EventMessage {
    var key: Int
    var message:Any? = null
    private var messageMap:HashMap<String,Any?> = HashMap()

    constructor(key:Int,message:Any?){
        this.key = key
        this.message = message
    }
    constructor(key:Int){
        this.key = key
    }

    fun put(key:String,message: Any?){
        messageMap[key] = message
    }

    operator fun <T> get(key:String):T?{
        try {
            return messageMap[key] as T?
        }   catch (e : ClassCastException){
            e.printStackTrace()
        }
        return null
    }
}