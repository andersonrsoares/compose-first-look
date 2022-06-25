package br.com.anderson.composefirstlook

import okio.buffer
import okio.source
import javax.microedition.khronos.opengles.GL10
import kotlin.reflect.KClass
import com.google.gson.Gson

object ApiUtil {

    fun loadfile(fileName: String): String = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")?.source()?.buffer()?.readString(Charsets.UTF_8) ?: ""

    fun <T> loadfile(resourceName: String, classOfT: Class<T>): T {
       return Gson().fromJson(loadfile(resourceName), classOfT)
    }
}
