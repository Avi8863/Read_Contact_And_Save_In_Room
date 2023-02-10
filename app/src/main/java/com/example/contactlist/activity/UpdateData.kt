package com.example.contactlist.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.databaseclass.DatabaseClass
import com.example.contactlist.R

class UpdateData : AppCompatActivity() {
    var name: EditText? = null
    var phoneno: EditText? = null
    var address: EditText? = null
    var update: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        name = findViewById(R.id.name)
        phoneno = findViewById(R.id.phone)
        address = findViewById(R.id.address)
        update = findViewById(R.id.btn_update)


        //GET DATA
        name?.setText(intent.extras!!.getString("name"))
        address?.setText("a")
        phoneno?.setText(intent.extras!!.getString("phoneno"))
        val id = intent.extras!!.getInt("id")
        Log.d("idKey",id.toString())
        update?.setOnClickListener(View.OnClickListener {
            val name_txt = name?.getText().toString().trim { it <= ' ' }
            val phone_txt = phoneno?.getText().toString().trim { it <= ' ' }
            val address_txt = "a"
            if (name_txt == "" || phone_txt == "" || address_txt == "") {
                Toast.makeText(this@UpdateData, "All Field is required ....", Toast.LENGTH_SHORT)
                    .show()
            } else {
                    DatabaseClass.getDatabase(applicationContext)?.dao
                        ?.updateData(name_txt, phone_txt, address_txt, id!!.toInt())
                Log.d("idKey",name_txt+phone_txt+address_txt+id.toString())
                finish()
            }
        })
    }
}