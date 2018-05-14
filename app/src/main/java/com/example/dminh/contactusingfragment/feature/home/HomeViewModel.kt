package com.example.dminh.contactusingfragment.feature.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dminh.contactusingfragment.models.MyContact
import com.example.dminh.contactusingfragment.repository.ReadContactRepo

class HomeViewModel(readContactRepo: ReadContactRepo) : ViewModel(){
    var myContacts: MutableLiveData<List<MyContact>> = MutableLiveData()
    init {
        myContacts = readContactRepo.readContact()
    }
}