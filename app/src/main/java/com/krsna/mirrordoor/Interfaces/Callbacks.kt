package com.krsna.mirrordoor.Interfaces

import com.example.base.models.BaseResponse

interface Callbacks {

    interface AddCompanyCallback {
        fun companyAddedSuccess(response: BaseResponse)
        fun companyAddedFailure(error: String?)
    }
}