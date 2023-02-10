package com.example.contactlist.databaseclass

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactlist.dao.Daoclass
import com.example.contactlist.entityclass.UserModel

@Database(entities = [UserModel::class], version = 2)
abstract class DatabaseClass : RoomDatabase() {
    abstract val dao: Daoclass?

    companion object {
        private var instance: DatabaseClass? = null
        fun getDatabase(context: Context?): DatabaseClass? {
            if (instance == null) {
                synchronized(DatabaseClass::class.java) {
                    instance = Room.databaseBuilder(
                        context!!,
                        DatabaseClass::class.java, "DATABASE"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}
