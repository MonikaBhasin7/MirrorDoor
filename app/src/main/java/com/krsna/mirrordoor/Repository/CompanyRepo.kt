package com.krsna.mirrordoor.Repository

import com.example.base.models.BaseResponse
import com.example.base.utils.AppLogger
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.Model.ShowCompanyPayload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

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

    private var collectionReference : CollectionReference? = null
    private var query : Query ? = null
    private var isFirst = true
    fun showCompanies(showCompanyPayload: ShowCompanyPayload, callback: Callbacks.ShowCompanyCallback) {
        val companyList: ArrayList<Company> = arrayListOf()
        query = null
        collectionReference = null
        collectionReference = fireStoreDatabase.collection("Company")
        isFirst = true
        
        CoroutineScope(Dispatchers.IO).launch {
            addReviewFilter(showCompanyPayload.getReview())
            addTypeFilter("private")

            val callReference = if(isFirst) {
                getCollectionReference()
            } else getQueryReference()
            callReference?.get()?.addOnSuccessListener { response ->
                for (item in response) {
                    val company = Company().setCompany(item.getString("company_name"))
                        .setWebsite(item.getString("website"))
                        .setType(item.getString("type"))
                        .setReviews(item.getLong("reviews")?.toInt())
                    companyList.add(company)
                }
                callback.showCompanySuccess(companyList)
            }?.addOnFailureListener { exception ->
                callback.showCompanyFailure(exception.toString())
            }
        }
    }
    private fun addReviewFilter(review: Int? ) {
        if (isFirst) {
            if (review != -1) {
                query = collectionReference?.whereEqualTo("reviews", 3)!!
                isFirst = false
            }
        } else query?.whereEqualTo("reviews", 3)

    }

    private fun addTypeFilter(type: String?) {
        if (isFirst) {
            if (type != null) {
                query = collectionReference?.whereEqualTo("type", type)!!
                isFirst = false
            }
        } else query?.whereEqualTo("type", type)

    }

    private fun getCollectionReference() : CollectionReference? {
        return collectionReference
    }

    private fun getQueryReference() : Query? {
        return query
    }
}