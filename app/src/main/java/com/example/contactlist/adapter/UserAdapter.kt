package com.example.contactlist.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.R
import com.example.contactlist.activity.UpdateData
import com.example.contactlist.entityclass.UserModel
import kotlin.Int

class UserAdapter(
    var context: Context,
    list: List<UserModel>,
    deleteItemClicklistner: DeleteItemClicklistner
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder?>() {
    var list: List<UserModel>
    var deleteItemClicklistner: DeleteItemClicklistner

    init {
        this.list = list
        this.deleteItemClicklistner = deleteItemClicklistner
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_layout, parent, false)
        )
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.phone.setText(list[position].phoneno)
        holder.name.setText(list[position].name)
        holder.address.setText(list[position].address)
        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateData::class.java)
            intent.putExtra("id", (list[position].key))
            Log.d("idKey",(list[position].key.toString()))
            intent.putExtra("name", (list[position].name))
            intent.putExtra("address",(list[position].address))
            intent.putExtra("phoneno", (list[position].phoneno))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            deleteItemClicklistner.onItemDelete(
                position,
                list[position].key
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var address: TextView
        var phone: TextView
        var update: Button
        var delete: Button

        init {
            phone = itemView.findViewById(R.id.phone)
            address = itemView.findViewById(R.id.address)
            name = itemView.findViewById(R.id.name)
            update = itemView.findViewById(R.id.updateId)
            delete = itemView.findViewById(R.id.deleteId)
        }
    }

    interface DeleteItemClicklistner {
        fun onItemDelete(position: Int, id: Int)
    }
}
