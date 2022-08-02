package agg.karun.karun_homeexcercise.ui.home

import agg.karun.karun_homeexcercise.ui.data.HomeRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModelFactory for HomeViewModel
 * @Return HomeViewModel
 * Author By : Karun
 **/
class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                HomeRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}