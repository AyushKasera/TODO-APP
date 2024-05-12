package com.example.todolist

import android.content.DialogInterface
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.superStudio.todolist.R

class MainActivity : AppCompatActivity() {

    lateinit var item:EditText
    lateinit var add:Button
    lateinit var listView:ListView

    var itemList=ArrayList<String>()
    var fileHelper=FileHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        item=findViewById(R.id.editText)
        add=findViewById(R.id.button)
        listView=findViewById(R.id.list)

        itemList=fileHelper.readData(this@MainActivity)
        val arrayAdapter=ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        listView.adapter=arrayAdapter


        add.setOnClickListener {
            val itemName:String=item.text.toString()
            if(itemName==""){
                Toast.makeText(this@MainActivity,"Please Enter TODO!",Toast.LENGTH_SHORT).show()
            }
            else
            {
                itemList.add(itemName)
                item.setText("")
                fileHelper.WriteData(itemList,applicationContext)
                arrayAdapter.notifyDataSetChanged()
            }
        }
        listView.setOnItemClickListener { adapterView, view, position, l ->
            val alert=AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do You Want To Delete This?")
            alert.setCancelable(false)
            alert.setNegativeButton("NO",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.WriteData(itemList,applicationContext)
            })
            alert.create().show()
        }
    }
}