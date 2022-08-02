package agg.karun.karun_homeexcercise.ui.data

import agg.karun.karun_homeexcercise.database.AppDataBase
import agg.karun.karun_homeexcercise.database.CarsDao
import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import android.content.Context

/**
 * Repository used for operations on data
 * Author By : Karun
 **/
class HomeRepository {

    private lateinit var carsDao: CarsDao

    /*
    * Method to get cars List from Application DB
    * @param context , make and model
    * @return MutableList<CarDetails>
    * */
    fun getCarListDataFromDB(
        context: Context,
        make: String,
        model: String
    ): MutableList<CarDetails> {
        carsDao = AppDataBase.getAppDbInstance(context).carsDao()
        return carsDao.getCarList(make, model)
    }


    /*
    * Method to insert cars list into database taken from asset
    * @param carsData
    * */
    fun insertCarListIntoDb(carsData: ArrayList<CarDetails>) {
        carsDao.insertCarList(carsData)
    }

    /*
    * Method to get Make List for filter
    * @param context and request
    * @return MutableList<String>
    * */
    fun getCarMakeFromDB(): (MutableList<String>) {
        return carsDao.getCarMakeList()
    }

    /*
    * Method to get Model List for filter
    * @param context and request
    * @return MutableList<String>
    * */
    fun getCarModelFromDB(make: String): (MutableList<String>) {
        return carsDao.getCarModelList(make)
    }
}