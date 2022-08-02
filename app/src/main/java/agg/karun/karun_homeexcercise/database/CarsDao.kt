package agg.karun.karun_homeexcercise.database

import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object
 * car Dao to read and write data into database
 * Author By : Karun
 **/
@Dao
interface CarsDao {

    /*To insert car list into application database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCarList(carDetails: List<CarDetails>)

    /*Get list of Cars as per make and model filter from application database*/
    @Query("SELECT * FROM carDetails where make LIKE '%' || :make || '%' AND model LIKE '%' || :model || '%'")
    fun getCarList(make : String, model : String) :MutableList<CarDetails>

    /*Get list of make for filter from application database*/
    @Query("SELECT make FROM carDetails ORDER BY make ASC")
    fun getCarMakeList() :MutableList<String>

    /*Get list of model for filter from application database*/
    @Query("SELECT model FROM carDetails where make LIKE '%' || :carMake || '%'")
    fun getCarModelList(carMake: String) :MutableList<String>
}