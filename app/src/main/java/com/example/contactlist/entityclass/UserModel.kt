package com.example.contactlist.entityclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserModel(
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "phoneno")
    var phoneno: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null
)