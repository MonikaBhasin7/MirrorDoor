package ui.observers

import androidx.lifecycle.Observer
import com.example.base.models.ApiResponseWrapper
abstract class ApiObserver<T> : Observer<ApiResponseWrapper<T>>{

    abstract fun onSuccess(data:T)
    abstract fun onFailure(errorMessage:String)

    override fun onChanged(t: ApiResponseWrapper<T>?) {

        if(t!=null){
            if(t.errorMessage!=null){
               onFailure(t.errorMessage!!)
            }else{
                onSuccess(t.data!!)
            }
        }else{
              onFailure("Null Api Response wrapper")
        }
    }
}