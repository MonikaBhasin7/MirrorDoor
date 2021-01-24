package com.example.base.utils


import timber.log.Timber

class AppLogger {

    companion object {

        private val TAG = "PW-"

        fun init() {
            //if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            //}
        }

        fun infoLog(tag:String?,infoText:String?){
            Timber.tag(TAG.plus(tag)).i(infoText)
        }

        fun infoLog(infoText: String?){
            Timber.tag(TAG).i(infoText)
        }

        fun debugLog(tag:String?,debugText:String?){
           Timber.tag(TAG.plus(tag)).d(debugText)
        }

        fun debugLog(debugText: String?){
           Timber.tag(TAG).d(debugText)
        }

        fun errorLog(tag:String?,errorText:String?){
            Timber.tag(TAG.plus(tag)).e(errorText)
        }

        fun errorLog(errorText: String?){
            Timber.tag(TAG).e(errorText)
        }

        fun warningLog(tag:String?,warningText:String?){
            Timber.tag(TAG.plus(tag)).w(warningText)
        }

        fun warningLog(warningText:String?){
            Timber.tag(TAG).w(warningText)
        }

        fun logCurrentMethodName(tag:String?){
            Timber.tag(TAG.plus(tag)).i(getCurrentMethodName())
        }

        /**
         *  get the name of current method name
         */
        private fun getCurrentMethodName():String{
            return Thread.currentThread().stackTrace[4].methodName
        }

    }
}