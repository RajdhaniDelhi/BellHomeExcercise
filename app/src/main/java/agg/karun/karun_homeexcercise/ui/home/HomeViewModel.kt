package agg.karun.karun_homeexcercise.ui.home

import agg.karun.karun_homeexcercise.ui.data.HomeRepository
import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import agg.karun.karun_homeexcercise.ui.data.model.CarModel
import agg.karun.karun_homeexcercise.ui.data.model.CarProsConsModel
import agg.karun.karun_homeexcercise.util.*
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.lang.reflect.Type

/**
 * ViewModel used for HomeFragment
 * to handle data
 * Author By : Karun
 **/
class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val carDetails = MutableLiveData<MutableList<CarDetails>>()
    private val carMakeLiveData = MutableLiveData<MutableList<String>>()
    private val carModelLiveData = MutableLiveData<MutableList<String>>()

    /*@Method
    * @Returns LiveData object of CarList
    * */
    fun getCarsList(): LiveData<MutableList<CarDetails>> {
        return carDetails
    }

    /*@Method
    * Read Car List from Database and places value into liveData
    * */
    fun getCarsListFromDB(context: Context, carMake: String = "", carModel: String = "") {
        viewModelScope.launch {
            val result: Deferred<MutableList<CarDetails>> =
                CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
                    homeRepository.getCarListDataFromDB(context, carMake, carModel)
                }
            result.await().also {
                if (it.isEmpty() && carMake.isEmpty() && carModel.isEmpty()) {
                    carDetails.value = getCarListFromAssets(context)
                } else {
                    carDetails.value = it
                }
                if (carMake.isEmpty() && carModel.isEmpty()) {
                    getCarMakeListFromDB()
                    getCarModelListForMake()
                }
            }
        }
    }


    /* * @Method
     * Read Car List from asset and stores list in app Database
     * @param context
     * */
    private suspend fun getCarListFromAssets(context: Context): MutableList<CarDetails> {

        // Getting json data from the car_list json file in assets
        val listAssets: Deferred<MutableList<CarDetails>> =
            CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {

                val json =
                    Utils.readFileFromAssets(context, CAR_CONST)    //read JSON data From asset file

                val listType: Type = object :
                    TypeToken<ArrayList<CarModel>>() {}.type // create Type of response as per json data
                val carModel = GsonBuilder().create().fromJson<ArrayList<CarModel>>(
                    json,
                    listType
                ) // convert json data to typed model

                val carsData = ArrayList<CarDetails>()

                for (car in carModel.indices) {
                    val carPros = ArrayList<CarProsConsModel>()
                    carPros.add(CarProsConsModel(type = PROS, value = carModel[car].prosList))
                    carPros.add(CarProsConsModel(type = CONS, value = carModel[car].consList))
                    carsData.add(
                        CarDetails(
                            carModel[car].model!!,
                            carModel[car].make,
                            carModel[car].customerPrice,
                            carModel[car].rating,
                            Utils.getCarImageByIndex(car),
                            carPros,
                            viewType = PARENT,
                            false
                        )
                    )
                }

                //Insert data in App_DB
                homeRepository.insertCarListIntoDb(carsData)

                return@async carsData
            }

        return listAssets.await()
    }

    /*@Method
    * @Returns LiveData object of make
    * */
    fun getCarMakeLiveData(): LiveData<MutableList<String>> {
        return carMakeLiveData
    }

    /*@Method
    * @Returns LiveData object of model
    * */
    fun getCarModelLiveData(): LiveData<MutableList<String>> {
        return carModelLiveData
    }

    /*
    * Method to get Model and Make List for filter from database
    * and places value into corresponding liveData object
    * @param context
    * */
    private fun getCarMakeListFromDB() {
        viewModelScope.launch {
            val carMakeListDeferred = viewModelScope.async(Dispatchers.IO) {
                homeRepository.getCarMakeFromDB()
            }
            carMakeLiveData.value = carMakeListDeferred.await()
        }

    }

    /*
    * Method to get Make List for selected Model to filter
    * @param context
    * */
    fun getCarModelListForMake(make: String = "") {
        viewModelScope.launch {
            val carModelListDeferred = viewModelScope.async(Dispatchers.IO) {
                homeRepository.getCarModelFromDB(make)
            }
            carModelLiveData.value = carModelListDeferred.await()
        }

    }
}