package agg.karun.karun_homeexcercise.database

import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import agg.karun.karun_homeexcercise.ui.data.model.ProsConsTypeConverter
import android.content.Context
import androidx.room.*

/**
 * Application Database
 * Creates database for the application
 * Author By : Karun
 **/

@TypeConverters(ProsConsTypeConverter::class)
@Database(entities = [CarDetails::class], version = 1, exportSchema = true)
abstract class AppDataBase: RoomDatabase() {
    abstract fun carsDao(): CarsDao

    companion object {
        private lateinit var dbInstance: AppDataBase

        /**
         * @Method
         * @param context
         * @return Instance of database object
         * */
        fun getAppDbInstance(context: Context): AppDataBase {
            if (!::dbInstance.isInitialized) {
                dbInstance =
                    Room.databaseBuilder(context, AppDataBase::class.java, "car_db").build()
            }
            return dbInstance
        }
    }
}