package com.krsna.mirrordoor.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_on_showcompanyfragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> {
                FilterFragment.newInstance()
                    .show(childFragmentManager, null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = ShowCompanyFragment()
    }
}