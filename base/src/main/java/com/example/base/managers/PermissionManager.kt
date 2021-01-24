package managers

import android.content.Context

class PermissionManager private constructor(context: Context?) {

    private val mContext:Context? = context

    companion object{

        @Volatile
        private var permissionManager: PermissionManager? = null

        fun getInstance(context: Context?): PermissionManager? {
            if (permissionManager == null) {
                synchronized(PreferencesManager::class.java) {
                    if (permissionManager == null) {
                        permissionManager = PermissionManager(context)
                    }
                }
            }
            return permissionManager
        }
    }


}