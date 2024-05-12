package com.example.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {

    val FILENAME="listinfo.dat"

    fun WriteData(item:ArrayList<String>,context: Context)
    {
        val fos:FileOutputStream=context.openFileOutput(FILENAME,Context.MODE_PRIVATE)
        val oas=ObjectOutputStream(fos)
        oas.writeObject(item)
        oas.close()

    }
    fun readData(context: Context): ArrayList<String> {
        var itemList:ArrayList<String>
        itemList= ArrayList()
        try{

            var fis:FileInputStream=context.openFileInput(FILENAME)
            var ois=ObjectInputStream(fis)
            itemList= ois.readObject() as ArrayList<String>
        }
        catch (_:FileNotFoundException)
        {
        }
        return itemList
    }
}