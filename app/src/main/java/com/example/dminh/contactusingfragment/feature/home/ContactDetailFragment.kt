package com.example.dminh.contactusingfragment.feature.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dminh.contactusingfragment.R
import com.example.dminh.contactusingfragment.models.MyContact
import kotlinx.android.synthetic.main.fragment_contact_detail.view.*

private const val ARG_PARAM1 = "param1"

class ContactDetailFragment : Fragment() {
    private var myContact: MyContact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myContact = it.get(ARG_PARAM1) as MyContact
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_contact_detail, container, false)

        view.tvContactName.text = myContact?.name
        view.tvContactNumber.text = myContact?.phone

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(myContact: MyContact?) =
                ContactDetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, myContact)
                    }
                }
    }
}
