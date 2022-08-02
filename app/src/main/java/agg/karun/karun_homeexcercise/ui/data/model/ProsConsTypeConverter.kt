package agg.karun.karun_homeexcercise.ui.data.model

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * @Converter of list to string and string to list using GSON
 * Author By : Karun
 **/
class ProsConsTypeConverter() {
    @TypeConverter
    fun fromString(value: String): ArrayList<CarProsConsModel> {
        val type = object : TypeToken<ArrayList<CarProsConsModel>>() {}.type
        return GsonBuilder().create().fromJson(value, type)
    }

    @TypeConverter
    fun fromArray(list: ArrayList<CarProsConsModel>): String {
        return GsonBuilder().create().toJson(list)
    }
}
