package com.krsna.mirrordoor.Activities

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.base.ui.BaseActivity
import com.krsna.mirrordoor.Fragments.ChoiceFragment
import com.krsna.mirrordoor.Fragments.AddCompanyFragment
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.R

class ChoiceActivity : BaseActivity(), Callbacks.OpenFragments {

    private lateinit var fragmentTransaction: FragmentTransaction
    override fun getLayoutId(): Int {
        return R.layout.activity_choice
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    }

}