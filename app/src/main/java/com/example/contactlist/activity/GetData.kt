package com.example.contactlist.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.databaseclass.DatabaseClass
import com.example.contactlist.R
import com.example.contactlist.adapter.UserAdapter
import com.example.contactlist.entityclass.UserModel

class GetData : AppCompatActivity() {
    var recyclerview: RecyclerView? = null
    private var list: List<UserModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)
        recyclerview = findViewById(R.id.recyclerview)
    }

    override fun onResume() {
        super.onResume()
        data
    }

    private val data: Unit
        private get() {
            list = ArrayList<UserModel>()
            list = DatabaseClass.getDatabase(applicationContext)?.dao?.getAllData() as List<UserModel>?
            recyclerview!!.adapter =
                UserAdapter(applicationContext, list!!, object :
                    UserAdapter.DeleteItemClicklistner {
                    override fun onItemDelete(position: Int, id: Int) {
                        DatabaseClass.getDatabase(applicationContext)?.dao?.deleteData(id)
                        data
                    }
                })
        }
}