package com.krsna.mirrordoor.Repository

import android.widget.Toast
import com.example.base.models.BaseResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company

class CompanyRepo(private val fireStoreDatabase: FirebaseFirestore) {

    fun addCompany(company: Company, callback: Callbacks.AddCompanyCallback) {
        company.company_name?.let {
            fireStoreDatabase
                .collection("Company")
                .document(it)
                .set(company)
                .addOnSuccessListener {
                    callback.companyAddedSuccess(BaseResponse("Company Added"))
                }
                .addOnFailureListener {
                    callback.companyAddedFailure("Not Added")
                }
        }
    }
}