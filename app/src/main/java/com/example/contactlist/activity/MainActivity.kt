package com.example.contactlist.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.databaseclass.DatabaseClass
import com.example.contactlist.R
import com.example.contactlist.entityclass.UserModel


class MainActivity : AppCompatActivity() {

    var name = " "
    var phoneNumber = " "
    var list = mutableListOf<UserModel>()
    var firstTime: Boolean = true


    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val contact = findViewById<ImageView>(R.id.loadContacts)
        contact.visibility = View.GONE
        val saveBtn = findViewById<Button>(R.id.btnSaveData)
        // saveBtn.setOnClickListener { saveData() }
        val fetchBtn = findViewById<Button>(R.id.btnFetchData)
        fetchBtn.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    GetData::class.java
                )
            )
        }
        loadContacts()
    }

    private fun loadContacts() {
        var builder = StringBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
        } else {
            builder = getContacts()
            val listContacts = findViewById<TextView>(R.id.listContacts)
            listContacts.text = builder.toString()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            }
        }
    }

    @SuppressLint("Range")
    private fun getContacts(): StringBuilder {
        val builder = StringBuilder()
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null
        )

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    phoneNumber = (cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    )).toInt().toString()

                    if (phoneNumber > 0.toString()) {
                        val cursorPhone = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(id),
                            null
                        )

                        if (cursorPhone != null) {
                            if (cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    var phoneNumValue = cursorPhone.getString(
                                        cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                    )
                                    phoneNumber = phoneNumValue
                                    builder.append("Name: ").append(name)
                                        .append("\nPhone Number: ").append(
                                            phoneNumValue
                                        ).append("\n\n")
                                    Log.e("Name ===>", phoneNumValue);
                                    list.add(
                                        UserModel(
                                            name = name,
                                            phoneno = phoneNumValue,
                                            address = "a"
                                        )
                                    )
                                }
                            }
                        }
                        cursorPhone?.close()
                    }
                }
            } else {
                //   toast("No contacts available!")
            }
        }
        cursor?.close()
        Log.e("list ===>", list.toString());
        if (firstTime) {
            DatabaseClass.getDatabase(applicationContext)?.dao?.insertAllData(list)
            firstTime = false
        }
        startActivity(
            Intent(
                applicationContext,
                GetData::class.java
            )
        )
        return builder
    }

}