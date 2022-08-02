package agg.karun.karun_homeexcercise.util

import agg.karun.karun_homeexcercise.R
import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Utility class
 * Author By : Karun
 **/
class Utils {
    companion object {
        /**
         * @Method
         * @param index
         * @return Int
         * */
        fun getCarImageByIndex(index: Int): Int {
            return when (index) {
                0 -> R.drawable.range_rover
                1 -> R.drawable.alpine_roadster
                2 -> R.drawable.bmw
                3 -> R.drawable.mercedez_benz_glc
                else -> android.R.drawable.ic_menu_close_clear_cancel
            }
        }

        fun readFileFromAssets(context: Context, fileName: String): String {
            return try {
                val stream: InputStream = context.assets.open(fileName)

                val size: Int = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                val charset: Charset = Charsets.UTF_8
                String(buffer, charset)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }
    }
}