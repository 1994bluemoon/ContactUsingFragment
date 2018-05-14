package com.example.dminh.contactusingfragment.feature.home

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dminh.contactusingfragment.R
import com.example.dminh.contactusingfragment.models.MyContact
import kotlinx.android.synthetic.main.item_contact.view.*

data class ContactAdapter(var contacts: List<MyContact>? = null, val itemClicked: IClickedEvent? = null) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindData(contacts?.get(position))
        holder.item.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                itemClicked?.itemClicked(contacts?.get(position))
            }
        })
    }

    class ViewHolder(var item: View) : RecyclerView.ViewHolder(item) {
        fun bindData(contact: MyContact?){
            item.tvContactName.text = contact?.name
            item.tvContactNumber.text = contact?.phone
        }
    }
}