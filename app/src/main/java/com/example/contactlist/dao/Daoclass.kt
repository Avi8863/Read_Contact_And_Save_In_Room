package com.example.contactlist.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactlist.entityclass.UserModel

@Dao
interface Daoclass {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllData(model:  List<UserModel>?)

    //Select All Data
    @Query("select * from  user")
    fun getAllData(): List<UserModel?>?

    //DELETE DATA
    @Query("delete from user where `key`= :id")
    fun deleteData(id: Int)

    //Update Data

    //Update Data
    @Query("update user SET name= :name ,address =:address, phoneno =:phoneno where `key`= :key")
    fun updateData(name: String?, phoneno: String?, address: String?, key: Int)

}