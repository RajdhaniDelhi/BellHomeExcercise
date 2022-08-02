package agg.karun.karun_homeexcercise.ui.data.model

import com.google.gson.annotations.SerializedName

/**
 * data class to parse JSON data
 * Author By : Karun
 **/
data class CarModel(
    @SerializedName("consList") var consList: ArrayList<String>? = null,
    @SerializedName("customerPrice") var customerPrice: Int? = null,
    @SerializedName("make") var make: String? = null,
    @SerializedName("marketPrice") var marketPrice: Int? = null,
    @SerializedName("model") var model: String? = null,
    @SerializedName("prosList") var prosList: ArrayList<String>? = null,
    @SerializedName("rating") var rating: Int? = null
)
