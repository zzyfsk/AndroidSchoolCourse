package com.zzy.base.util

import android.content.Context
import java.io.File

class FileUtil (val context: Context){
     fun saveFile(fileName: FileName,fileContent: String) {
        val route = context.filesDir
        val file = File(route,fileName.fileName)
        file.writeText(fileContent)
    }

     fun readFile(fileName: String): File {
        val file = File(context.filesDir,fileName)
        return file
    }

     fun readFiles(): List<FileName> {
         val fileList = mutableListOf<FileName>()
         val route = context.filesDir
         route.listFiles()?.forEach {
             fileList.add(FileName(it.name))
         }
        return fileList
    }
}

data class FileName (val fileName:String)
