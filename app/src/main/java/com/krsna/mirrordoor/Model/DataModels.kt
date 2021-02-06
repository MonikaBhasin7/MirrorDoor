package com.krsna.mirrordoor.Model

class Company {
    var company_name: String? = null
    var website: String? = null
    var type: String? = null
    var reviews: Int? = null

    fun setCompany(companyName: String?) : Company{
        this.company_name = companyName
        return this
    }
    fun setWebsite(website: String?) : Company{
        this.website = website
        return this
    }
    fun setType(type: String?) : Company{
        this.type = type
        return this
    }
    fun setReviews(reviews: Int?) : Company{
        this.reviews = reviews
        return this
    }
}