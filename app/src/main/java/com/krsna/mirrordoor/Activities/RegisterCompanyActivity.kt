package com.krsna.mirrordoor.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.base.ui.BaseActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.*
import com.krsna.mirrordoor.Fragments.RegisterCompanyFragment
import com.krsna.mirrordoor.R

class RegisterCompanyActivity : BaseActivity() {

    lateinit var fragmentTransaction: FragmentTransaction
    override fun getLayoutId(): Int {
        return R.layout.activity_register_company
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_content, RegisterCompanyFragment.newInstance())
        //fragmentTransaction.addToBackStack("RegisterCompany")
        fragmentTransaction.commit()
    }
}