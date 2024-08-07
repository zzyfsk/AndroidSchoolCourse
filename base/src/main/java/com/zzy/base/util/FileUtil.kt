package com.zzy.base.util

import android.content.Context
import java.io.File

class FileUtil (val context: Context){
     fun saveFile(fileName: FileName,fileContent: String) {
        val route = context.getDir("history", Context.MODE_PRIVATE)
        val file = File(route,fileName.fileName)
        file.writeText(fileContent)
    }

     fun readFile(fileName: String): File {
        val file = File(context.getDir("history", Context.MODE_PRIVATE),fileName)
        return file
    }

     fun readFiles(): List<FileName> {
         val fileList = mutableListOf<FileName>()
         val route = context.getDir("history", Context.MODE_PRIVATE)
         route.listFiles()?.forEach {
             fileList.add(FileName(it.name))
         }
        return fileList
    }
}

data class FileName (val fileName:String){
    override fun toString(): String {
        return super.toString()
    }
}
