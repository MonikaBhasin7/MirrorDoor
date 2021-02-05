package ui.observers

import androidx.lifecycle.Observer


abstract class LoadingObserver:Observer<Boolean>{

    abstract fun onLoadingStarted()
    abstract fun onLoadingStoped()

    override fun onChanged(t: Boolean?) {
         if(t!=null){
             if(t){
                 onLoadingStarted()
             }else{
                 onLoadingStoped()
             }
         }
    }
}