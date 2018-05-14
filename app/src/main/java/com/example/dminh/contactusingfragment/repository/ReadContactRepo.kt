package com.example.dminh.contactusingfragment.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.dminh.contactusingfragment.models.MyContact
import java.util.ArrayList

class ReadContactRepo(context: Context?){
    val context: Context? = context

    fun readContact() : MutableLiveData<List<MyContact>>{
        var contacts = ArrayList<MyContact>()
        val resolver = context?.getContentResolver()
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)!!

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                var phoneNumber: String? = null

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    val pCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(contactId), null)!!

                    while (pCur.moveToNext()) {
                        val phoneType = pCur.getInt(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.TYPE))
                        phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        when (phoneType) {
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> Log.e(name + "(mobile number)", phoneNumber)
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> Log.e(name + "(home number)", phoneNumber)
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> Log.e(name + "(work number)", phoneNumber)
                            ContactsContract.CommonDataKinds.Phone.TYPE_OTHER -> Log.e(name + "(other number)", phoneNumber)
                            else -> {
                            }
                        }
                    }
                    pCur.close()
                }
                val contact = MyContact(name, phoneNumber)
                contacts.add(contact)
            }
        } else
            Log.d("contact", "data not found")
        cursor.close()
        var results = MutableLiveData<List<MyContact>>()
        results.value = contacts
        return results
    }
}