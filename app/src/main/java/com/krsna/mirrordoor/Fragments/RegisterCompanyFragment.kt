package com.krsna.mirrordoor.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.base.ui.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.krsna.mirrordoor.Common.FireStore
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.databinding.FragmentRegisterCompaniesBinding

class RegisterCompanyFragment : Fragment(), View.OnClickListener  {

    private lateinit var dataBinding: FragmentRegisterCompaniesBinding
    lateinit var firebaseFirestore: FirebaseFirestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.btnAdd.setOnClickListener(this)
        firebaseFirestore = FireStore.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register_companies, container, false)
        return dataBinding.root
    }


    companion object {
        fun newInstance() = RegisterCompanyFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.btn_add -> {
                addCompanyData()
            }
        }
    }

    private fun addCompanyData() {
        val company = Company(dataBinding.etCompanyName.text.toString(),
                dataBinding.etWebsiteName.text.toString(),
                dataBinding.etTypeName.text.toString()
        )
        firebaseFirestore
                .collection("Company")
                .document(dataBinding.etCompanyName.text.toString())
                .set(company)
                .addOnSuccessListener {
                    //showToast("Company Added")
                }
                .addOnFailureListener {
                    //showToast("Company Not Added")
                }
    }
}