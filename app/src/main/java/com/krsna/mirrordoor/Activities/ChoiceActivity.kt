package com.krsna.mirrordoor.Activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.example.base.ui.BaseActivity
import com.krsna.mirrordoor.Fragments.ChoiceFragment
import com.krsna.mirrordoor.Fragments.AddCompanyFragment
import com.krsna.mirrordoor.Fragments.ShowCompanyFragment
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.R

class ChoiceActivity : BaseActivity(), Callbacks.OpenFragments {

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var toolbar: Toolbar
    override fun getLayoutId(): Int {
        return R.layout.activity_choice
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = findViewById(R.id.tag_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "MirrorDoor"
        enableUpButton()

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_content, ChoiceFragment.newInstance())
        //fragmentTransaction.addToBackStack("RegisterCompany")
        fragmentTransaction.commit()
    }

    override fun openAddCompanyFragment() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_content, AddCompanyFragment.newInstance())
        fragmentTransaction.addToBackStack("RegisterCompany")
        fragmentTransaction.commit()
    }

    override fun openShowCompanyFragment() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_content, ShowCompanyFragment.newInstance())
        fragmentTransaction.addToBackStack("ShowCompanyFragment")
        fragmentTransaction.commit()
    }

}