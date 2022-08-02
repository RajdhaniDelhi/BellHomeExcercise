package agg.karun.karun_homeexcercise.ui.data.model

import agg.karun.karun_homeexcercise.util.PARENT
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Table for car details
 * Author By : Karun
 **/
@Entity(tableName = "carDetails")
data class CarDetails(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "model")
    var model: String,

    @ColumnInfo(name = "make")
    var make: String? = null,

    @ColumnInfo(name = "price")
    var price: Int? = 0,

    @ColumnInfo(name = "rating")
    val ratings: Int? = 0,

    @ColumnInfo(name = "image")
    var image: Int = 0,

    @ColumnInfo(name = "prosCons")
    var prosCons: ArrayList<CarProsConsModel> = arrayListOf(),

    @ColumnInfo(name = "viewType")
    var viewType: Int = PARENT,

    @ColumnInfo(name = "expanded")
    var expanded: Boolean = false
)