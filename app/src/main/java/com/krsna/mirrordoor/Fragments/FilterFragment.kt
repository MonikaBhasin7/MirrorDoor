package com.krsna.mirrordoor.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.base.ui.BaseBottomSheetDialogFragment
import com.example.base.ui.BaseViewModelFactory
import com.krsna.mirrordoor.Common.FireStore
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.Repository.CompanyRepo
import com.krsna.mirrordoor.ViewModel.CompanyVM
import com.krsna.mirrordoor.databinding.FragmentFilterBinding

class FilterFragment : BaseBottomSheetDialogFragment(), View.OnClickListener {

    lateinit var dataBinding: FragmentFilterBinding

    private val companyViewModel: CompanyVM by lazy {
        ViewModelProviders.of(activity!!, BaseViewModelFactory {
            CompanyVM(
                CompanyRepo(FireStore.getInstance()),
                activity!!.application
            )
        }).get(CompanyVM::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_filter
    }

    override fun onInitViews(view: View) {
        dataBinding = DataBindingUtil.bind(childView)!!
        dataBinding.cvReviews.setOnClickListener(this)
        dataBinding.btnSubmit.setOnClickListener(this)
    }


    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cv_reviews -> {
                showReviewMenuPopup(dataBinding.cvReviews, dataBinding.txtReviews, R.menu.base)
            }
            R.id.btn_submit -> {
                companyViewModel.filterReview = dataBinding.txtReviews.text.toString()
                //companyViewModel.showCompanies()
                companyViewModel.showCompaniesInRealTime()
                dismiss()
            }
        }
    }

    private fun showReviewMenuPopup(view: View, textView: TextView, reviewsList: Int) {
        val popupMenu =
            PopupMenu(activity!!, view)
        popupMenu.menu.add("1")
        popupMenu.menu.add("2")
        popupMenu.menu.add("3")
        popupMenu.menu.add("4")
        popupMenu.menu.add("5")
        popupMenu.menuInflater.inflate(reviewsList, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            dataBinding.txtReviews.text = item.title
            false
        }
        popupMenu.show()
    }
}