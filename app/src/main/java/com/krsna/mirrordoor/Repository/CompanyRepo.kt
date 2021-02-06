package com.krsna.mirrordoor.Repository

import com.example.base.models.BaseResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.Model.ShowCompanyPayload

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

    fun showCompanies(showCompanyPayload: ShowCompanyPayload, callback: Callbacks.ShowCompanyCallback) {
        val companyList : ArrayList<Company> = arrayListOf()
        fireStoreDatabase.collection("Company")
            .whereEqualTo("reviews", showCompanyPayload.getReview())
            .get()
            .addOnSuccessListener { response ->
                for(item in response) {
                    val company = Company().setCompany(item.getString("company_name"))
                        .setWebsite(item.getString("website"))
                        .setType(item.getString("type"))
                        .setReviews(item.getLong("reviews")?.toInt())
                    companyList.add(company)
                }
                callback.showCompanySuccess(companyList)
            }
            .addOnFailureListener { exception ->
                callback.showCompanyFailure(exception.toString())
            }
    }
}