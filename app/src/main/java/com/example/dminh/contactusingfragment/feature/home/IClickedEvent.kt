package com.example.dminh.contactusingfragment.feature.home

import com.example.dminh.contactusingfragment.models.MyContact

interface IClickedEvent{
    fun itemClicked(myContact: MyContact?)
}