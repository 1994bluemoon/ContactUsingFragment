package com.example.dminh.contactusingfragment.feature.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.dminh.contactusingfragment.R
import com.example.dminh.contactusingfragment.models.MyContact
import com.example.dminh.contactusingfragment.repository.ReadContactRepo
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : Fragment(), IClickedEvent {
    private val homeViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ReadContactRepo(this@ContactFragment.context)
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repo) as T
            }
        })[HomeViewModel::class.java]
    }
    private var contactAdapter: ContactAdapter? = null
    private var listener: OnContactFragmentInteractionListener? = null

    override fun itemClicked(myContact: MyContact?) {
        listener?.onFragmentInteraction(myContact)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        rvContact.layoutManager = LinearLayoutManager(this@ContactFragment.context, LinearLayout.VERTICAL, false)

        homeViewModel.myContacts.observe(this, Observer {
            contactAdapter = ContactAdapter(it, itemClicked = this@ContactFragment)
            rvContact.adapter = contactAdapter
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnContactFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnContactFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnContactFragmentInteractionListener {
        fun onFragmentInteraction(myContact: MyContact?)
    }
}

