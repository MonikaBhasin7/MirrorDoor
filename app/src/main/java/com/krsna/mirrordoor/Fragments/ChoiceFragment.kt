package com.krsna.mirrordoor.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.databinding.FragmentChoiceBinding


class ChoiceFragment : Fragment(), View.OnClickListener {

    private lateinit var dataBinding: FragmentChoiceBinding
    private lateinit var openFragmentCallbackListener: Callbacks.OpenFragments
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        openFragmentCallbackListener = context as Callbacks.OpenFragments
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_choice, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataBinding.cvAddCompany.setOnClickListener(this)
    }

    companion object {
       fun newInstance() = ChoiceFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.cv_add_company -> {
                openFragmentCallbackListener.openAddCompanyFragment()
            }

            R.id.cv_show_company -> {
                openFragmentCallbackListener.openShowCompanyFragment()
            }
        }
    }
}