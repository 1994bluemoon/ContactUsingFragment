package com.example.dminh.contactusingfragment.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.example.dminh.contactusingfragment.models.MyContact
import java.util.ArrayList

class ReadContactRepo(private val context: Context?){

    fun readContact() : MutableLiveData<List<MyContact>>{
        val contacts = ArrayList<MyContact>()
        val resolver = context?.contentResolver
        val cursor = resolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)!!

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val phoneNumber: String? = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = MyContact(name, phoneNumber)
                contacts.add(contact)
            }
        } else{
            Log.d("contact", "data not found")
            Toast.makeText(context, "data not found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        val results = MutableLiveData<List<MyContact>>()
        results.value = contacts
        return results
    }
}