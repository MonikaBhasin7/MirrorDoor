package managers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.base.utils.AppLogger

class PreferencesManager {

    private val PREF_NAME = "com.parviom.pwcore.PREFERENCES_FILE_KEY"

    private constructor(context: Context?){
        sharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        @Volatile
        private var preferencesManager: PreferencesManager? = null
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context?): PreferencesManager? {
            if (preferencesManager == null) {
                synchronized(PreferencesManager::class.java) {
                    if (preferencesManager == null) {
                        preferencesManager = PreferencesManager(context)
                    }
                }
            }
            return preferencesManager
        }
    }

        fun setValueForKey(key: String?, value: String?) {
            try {
                sharedPreferences.edit().putString(key, value).commit()
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot write in SharedPreferences: $e")
            }

        }

        fun setValueForKey(key: String, value: Boolean?) {
            try {
                sharedPreferences.edit().putBoolean(key, value!!).apply()
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot write in SharedPreferences: $e")
            }

        }

        fun setValueForKey(key: String, value: Int) {
            try {
                sharedPreferences.edit().putInt(key, value).commit()
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot write in SharedPreferences: $e")
            }

        }

        fun setValueForKey(key: String, value: Long) {
            try {
                sharedPreferences.edit().putLong(key, value).commit()
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot write in SharedPreferences: $e")
            }

        }

        fun setValueForKey(key: String, value: Set<String>) {
            try {
                sharedPreferences.edit().putStringSet(key, value).commit()
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot write in SharedPreferences: $e")
            }

        }

        fun getStringForKey(key: String): String? {
            var returnValue: String? = null
            try {
                returnValue = sharedPreferences.getString(key, null)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }

        fun getBooleanForKey(key: String): Boolean? {
            var returnValue: Boolean? = false
            try {
                returnValue = sharedPreferences.getBoolean(key, false)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }


        // added for moengage config
        fun getBooleanForKey(key: String, defaultValue: Boolean): Boolean? {
            var returnValue: Boolean? = false
            try {
                returnValue = sharedPreferences.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }

        fun getIntForKey(key: String): Int {
            var returnValue = 0
            try {
                returnValue = sharedPreferences.getInt(key, 0)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }

        fun getLongForKey(key: String): Long {
            var returnValue: Long = 0
            try {
                returnValue = sharedPreferences.getLong(key, -1)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }

        fun getStringSetForKey(key: String): Set<String>? {
            var returnValue: Set<String>? = null
            try {
                returnValue = sharedPreferences.getStringSet(key, null)
            } catch (e: Exception) {
                AppLogger.errorLog("Cannot read from SharedPreferences: $e")
            }

            return returnValue
        }

        fun deleteValueForKey(key: String) {
            val editor = sharedPreferences.edit()
            editor.remove(key)
            editor.apply()
        }

    fun clearAll(){
        sharedPreferences.edit().clear().apply()
    }
}