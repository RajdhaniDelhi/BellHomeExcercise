package agg.karun.karun_homeexcercise.ui.home

import agg.karun.karun_homeexcercise.R
import agg.karun.karun_homeexcercise.databinding.FragmentHomeBinding
import agg.karun.karun_homeexcercise.ui.data.model.CarDetails
import agg.karun.karun_homeexcercise.ui.home.adapter.CarListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Fragment used for Home Page(GUIDOMIA)
 * to display car list
 * and filter car list
 * Author By : Karun
 **/
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var carListAdapter: CarListAdapter
    private lateinit var makeAdapter: ArrayAdapter<String>
    private lateinit var modelAdapter: ArrayAdapter<String>
    private var carDetailsList = ArrayList<CarDetails>()
    private var carMakeList = ArrayList<String>()
    private var carModelList: ArrayList<String> = arrayListOf()

    private var noMakeListDemonstrate = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HomeViewModelFactory())[HomeViewModel::class.java]

        viewModel.getCarsListFromDB(requireContext())
        binding.carRecycler.layoutManager = LinearLayoutManager(requireContext())

        dataObserver()

        setSpinnerAdapter()
        setCarAdapter()
    }

    private fun dataObserver() {
        /* Getting observer of carDetails List */
        viewModel.getCarsList().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                carDetailsList.clear()
                carDetailsList.addAll(it)
                carListAdapter.notifyDataSetChanged()
            }
        }

        /* Getting observer of Make List */
        viewModel.getCarMakeLiveData().observe(viewLifecycleOwner) {
            carMakeList.clear()
            carMakeList.add(requireContext().getString(R.string.make_spinner_default))
            carMakeList.addAll(it)
            makeAdapter.notifyDataSetChanged()
            if (noMakeListDemonstrate) {
                binding.make.performClick()
                noMakeListDemonstrate = false
            }
        }

        /* Getting observer of Model List */
        viewModel.getCarModelLiveData().observe(viewLifecycleOwner) {
            carModelList.clear()
            carModelList.add(requireContext().getString(R.string.model_spinner_default))
            carModelList.addAll(it)
            modelAdapter.notifyDataSetChanged()
            binding.model.setSelection(0)
        }
    }

    /*
    * @Method create object of carMakeListAdapter and carModelListAdapter
    * and sets both adapter to corresponding spinner
    * */
    private fun setSpinnerAdapter() {
        carMakeList.add(requireContext().getString(R.string.make_spinner_default))
        carModelList.add(requireContext().getString(R.string.model_spinner_default))
        makeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.filter_dropdown_item, carMakeList
        )

        binding.make.adapter = makeAdapter
        binding.make.setSelection(0)

        modelAdapter = ArrayAdapter(
            requireContext(),
            R.layout.filter_dropdown_item, carModelList
        )
        binding.model.adapter = modelAdapter
        binding.model.setSelection(0)

        spinnerItemSelectedListener()
    }

    private fun spinnerItemSelectedListener(){
        binding.make.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                val selectedMake = if (i == 0) "" else carMakeList[i]
                viewModel.getCarModelListForMake(selectedMake)
                viewModel.getCarsListFromDB(requireContext(), selectedMake)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.model.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                val selectedModel = if (i == 0) "" else carModelList[i]
                val selectedMake =
                    if (binding.make.selectedItemPosition == 0) "" else carMakeList[binding.make.selectedItemPosition]
                viewModel.getCarsListFromDB(requireContext(), selectedMake, selectedModel)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    /*
    * @Method create object of CarListAdapter(RecyclerView Adapter)
    * and sets adapter to recycler view
    * */
    private fun setCarAdapter() {
        carListAdapter = CarListAdapter(carDetailsList)
        binding.carRecycler.adapter = carListAdapter
    }
}