package managers

import com.example.base.utils.AppLogger
import com.example.base.utils.AppLogger.Companion.errorLog
import com.google.gson.*
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Modifier
import java.util.ArrayList

/**
 *  A layer over GSON for data parsing
 */
class DataManager private constructor() {
    val gsonBuilder: Gson

    fun getJSONObject(payload: String?): JSONObject? {
        var payloadObject: JSONObject? = null
        try {
            payloadObject = JSONObject(payload!!)
        } catch (e: JSONException) {
            e.printStackTrace()
            errorLog(e.message)
        }
        return payloadObject
    }

    fun <T> getMap(`object`: T): Map<String, Any> {
        val type =
            object : TypeToken<Map<String?, Any?>?>() {}.type
        return gsonBuilder.fromJson(
            gsonBuilder.toJson(`object`),
            type
        )
    }

    companion object {
        @Volatile
        private var dataManager: DataManager? = null

        @JvmStatic
        val instance: DataManager
            get() {
                if (dataManager == null) {
                    synchronized(DataManager::class.java) {
                        if (dataManager == null) {
                            dataManager = DataManager()
                        }
                    }
                }
                return dataManager!!
            }
    }

    init {
        gsonBuilder = GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapterFactory(CustomizedObjectTypeAdapter.FACTORY)
            .create()
    }

    /**
     *  Custom object adapter for Gson
     */
    class CustomizedObjectTypeAdapter : TypeAdapter<Any?>() {
        private val delegate =
            Gson().getAdapter(Any::class.java)

        @Throws(IOException::class)
        override fun write(
            out: JsonWriter,
            value: Any?
        ) {
            AppLogger.infoLog("Serializer write called")
            delegate.write(out, value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Any? {
            AppLogger.infoLog("Serializer read called")
            val token = `in`.peek()
            return when (token) {
                JsonToken.BEGIN_ARRAY -> {
                    AppLogger.debugLog("Serializer got an array to read")
                    val list: MutableList<Any?> =
                        ArrayList()
                    `in`.beginArray()
                    while (`in`.hasNext()) {
                        list.add(read(`in`))
                    }
                    `in`.endArray()
                    list
                }
                JsonToken.BEGIN_OBJECT -> {
                    AppLogger.debugLog("Serializer got an object to read")
                    val map: MutableMap<String, Any?> =
                        LinkedTreeMap()
                    `in`.beginObject()
                    while (`in`.hasNext()) {
                        map[`in`.nextName()] = read(`in`)
                    }
                    `in`.endObject()
                    map
                }
                JsonToken.STRING -> {
                    AppLogger.debugLog("Serializer got a string to read")
                    `in`.nextString()
                }
                JsonToken.NUMBER -> {
                    //return in.nextDouble();
                    val n = `in`.nextString()
                    AppLogger.debugLog("Serializer got a number to read:$n")
                    if (n.indexOf('.') != -1) {
                        AppLogger.debugLog("It is a double so returning with decimal")
                        return n.toDouble()
                    }
                    AppLogger.debugLog("Man you saved your ass, additional logic prevented int getting converted to float:$n")
                    n.toLong()
                }
                JsonToken.BOOLEAN -> {
                    AppLogger.debugLog("Serializer got a boolean to read")
                    `in`.nextBoolean()
                }
                JsonToken.NULL -> {
                    AppLogger.debugLog("Serializer got a null to read")
                    `in`.nextNull()
                    null
                }
                else -> throw IllegalStateException()
            }
        }

        companion object {
            @JvmField
            val FACTORY: TypeAdapterFactory = object : TypeAdapterFactory {
                override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
                    return if (MutableMap::class.java.isAssignableFrom(type.rawType)) {
                       CustomizedObjectTypeAdapter() as TypeAdapter<T>
                    } else null
                }
            }
        }
    }
}