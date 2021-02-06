package com.krsna.mirrordoor.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.models.BaseResponse
import com.example.base.ui.BaseViewModelFactory
import com.krsna.mirrordoor.Adapter.ShowCompanyListAdapter
import com.krsna.mirrordoor.Common.FireStore
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.Repository.CompanyRepo
import com.krsna.mirrordoor.ViewModel.CompanyVM
import com.krsna.mirrordoor.databinding.FragmentShowCompanyBinding
import ui.observers.ApiObserver


class ShowCompanyFragment : Fragment(), Callbacks.ShowCompanyListAdapterListener {

    lateinit var dataBinding: FragmentShowCompanyBinding
    lateinit var showCompanyListAdapter: ShowCompanyListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    var mCompanyList : MutableList<Company> = mutableListOf()
    private val companyViewModel: CompanyVM by lazy {
        ViewModelProviders.of(activity!!, BaseViewModelFactory {
            CompanyVM(
                CompanyRepo(FireStore.getInstance()),
                activity!!.application
            )
        }).get(CompanyVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_company, container, false)
        onInit()
        return dataBinding.root
    }

    private fun onInit() {
        companyViewModel.refreshLiveData()
        companyViewModel.showCompanyResponse.observe(activity!!, showCompanyObserver)
        linearLayoutManager = LinearLayoutManager(activity)
        dataBinding.rvShowCompany.layoutManager = linearLayoutManager
        showCompanyListAdapter =
            ShowCompanyListAdapter(
                activity!!,
                mCompanyList, this
            )
        dataBinding.rvShowCompany.adapter = showCompanyListAdapter

        companyViewModel.showCompanies()
    }

    private val showCompanyObserver = object : ApiObserver<List<Company>>() {
        override fun onSuccess(data: List<Company>) {
            mCompanyList.clear()
            mCompanyList.addAll(data)
            showCompanyListAdapter.notifyDataSetChanged()
        }

        override fun onFailure(errorMessage: String) {
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = ShowCompanyFragment()
    }
}