package com.krsna.mirrordoor.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.base.models.BaseResponse
import com.example.base.ui.BaseFragment
import com.example.base.ui.BaseViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.krsna.mirrordoor.Common.FireStore
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.Repository.CompanyRepo
import com.krsna.mirrordoor.ViewModel.CompanyVM
import com.krsna.mirrordoor.databinding.FragmentRegisterCompaniesBinding
import managers.PreferencesManager
import ui.observers.ApiObserver

class RegisterCompanyFragment : Fragment(), View.OnClickListener  {

    private lateinit var dataBinding: FragmentRegisterCompaniesBinding
    lateinit var firebaseFirestore: FirebaseFirestore

    private val companyViewModel: CompanyVM by lazy {
        ViewModelProviders.of(activity!!, BaseViewModelFactory {
            CompanyVM(
                    CompanyRepo(FireStore.getInstance()),
                    activity!!.application
            )
        }).get(CompanyVM::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.btnAdd.setOnClickListener(this)
        firebaseFirestore = FireStore.getInstance()
        companyViewModel.addCompanyResponse.observe(activity!!, auditReportObserver)
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
        if(dataBinding.etCompanyName.text.isNullOrEmpty() || dataBinding.etWebsiteName.text.isNullOrEmpty() || dataBinding.etTypeName.text.isNullOrEmpty()) {
            Toast.makeText(activity?.applicationContext, "Please add all information", Toast.LENGTH_SHORT).show()
            return
        }
        val company = Company(dataBinding.etCompanyName.text.toString(),
                dataBinding.etWebsiteName.text.toString(),
                dataBinding.etTypeName.text.toString()
        )
        companyViewModel.addCompany(company)

    }

    private val auditReportObserver = object : ApiObserver<BaseResponse>() {
        override fun onSuccess(data: BaseResponse) {
            Toast.makeText(activity, data.message, Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(errorMessage: String) {
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}