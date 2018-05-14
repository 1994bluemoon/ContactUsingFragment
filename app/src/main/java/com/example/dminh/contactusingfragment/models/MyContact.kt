package com.example.dminh.contactusingfragment.models

import java.io.Serializable

data class MyContact(var name: String? = null,
                     var phone: String? = null,
                     var email: String? = null) : Serializable