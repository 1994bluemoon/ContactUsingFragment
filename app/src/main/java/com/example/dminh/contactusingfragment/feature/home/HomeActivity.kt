package com.example.dminh.contactusingfragment.feature.home

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.dminh.contactusingfragment.R
import com.example.dminh.contactusingfragment.models.MyContact
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), ContactFragment.OnContactFragmentInteractionListener {

    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (isLandscape() || isTablet()){
            val fragment: Fragment? = supportFragmentManager.findFragmentByTag("contactDetail")
            if (fragment != null){
                flDetail.visibility = View.VISIBLE
            } else{
                flDetail.visibility = View.GONE
            }
        } else{
            flDetail.visibility = View.GONE
        }
        requestPermission()
    }

    override fun onFragmentInteraction(myContact: MyContact?) {
        if (isLandscape() || isTablet()){
            val contactDetailFragment = ContactDetailFragment.newInstance(myContact)
            val fragment: Fragment? = supportFragmentManager.findFragmentByTag("contactDetail")

            if  (fragment != null){
                supportFragmentManager.beginTransaction().remove(fragment).commit();
            }
             supportFragmentManager
                        .beginTransaction()
                        .add(flDetail.id, contactDetailFragment, "contactDetail")
                        .commit()
            flDetail.visibility = View.VISIBLE
        } else {

            val contactDetailFragment = ContactDetailFragment.newInstance(myContact)
            val fragment: Fragment? = supportFragmentManager.findFragmentByTag("contactDetail")
            if  (fragment != null){
                supportFragmentManager.beginTransaction().remove(fragment).commit();
            }
            supportFragmentManager
                    .beginTransaction()
                    .add(flDetail.id, contactDetailFragment, "contactDetail")
                    .commit()
            flContact.visibility = View.GONE
            flDetail.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if (isLandscape() || isTablet()){
            super.onBackPressed()
        } else{
            if  (flDetail.visibility == View.GONE){
                super.onBackPressed()
            } else{
                flContact.visibility = View.VISIBLE
                flDetail.visibility = View.GONE
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            MY_PERMISSIONS_REQUEST_READ_CONTACTS ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setFragment()
                } else {
                    requestPermission()
                }
        }
    }

    fun requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
                Toast.makeText(this, "explain", Toast.LENGTH_SHORT).show()
                //show the explain
            } else{

            }
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else{
            // permisson already granted
            setFragment()
        }
    }

    fun setFragment(){
        val contactFragment = ContactFragment()
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag("contact")
        if (fragment == null){
            supportFragmentManager
                    .beginTransaction()
                    .add(flContact.id, contactFragment, "contact")
                    .commit()
        }
    }

    fun isTablet(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun isLandscape() : Boolean{
        return  when {
            this@HomeActivity.resources.configuration.orientation ==  Configuration.ORIENTATION_LANDSCAPE -> true
            else -> false
        }
    }
}
