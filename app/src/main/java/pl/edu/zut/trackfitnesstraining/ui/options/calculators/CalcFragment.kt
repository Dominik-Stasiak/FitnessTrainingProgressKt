package pl.edu.zut.trackfitnesstraining.ui.options.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.databinding.FragmentCalcBinding

@AndroidEntryPoint
class CalcFragment : Fragment() {

    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!
    private lateinit var calcViewModel: CalcViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calcViewModel = ViewModelProvider(this)[CalcViewModel::class.java]
        _binding = FragmentCalcBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sliderORM_weight = binding.seekORMWeight
        sliderORM_weight.addOnChangeListener { _, value, _ ->
            calcViewModel.ormWeight.value = value.toDouble()
        }

        val sliderORM_rep = binding.seekORMRep
        sliderORM_rep.addOnChangeListener { _, value, _ ->
            calcViewModel.ormRep.value = value.toInt()
        }

        calcViewModel.ormRep.observe(viewLifecycleOwner) {
            binding.textORMRepU.text = "$it ${getString(R.string.powtorzen)}"
            calcViewModel.oneRepMax()
        }
        calcViewModel.ormWeight.observe(viewLifecycleOwner) {
            binding.textORMWeightU.text = "${getString(R.string.ciezar)} = $it kg"
            calcViewModel.oneRepMax()
        }
        calcViewModel.ormRes.observe(viewLifecycleOwner) {
            binding.textORMRes.text = "${it.toInt()} kg"
        }

        val sliderBMR_weight = binding.seekBMRWeight
        sliderBMR_weight.addOnChangeListener { _, value, _ ->
            calcViewModel.bmrWeight.value = value.toDouble()
        }

        val sliderBMR_height = binding.seekBMRHeight
        sliderBMR_height.addOnChangeListener { _, value, _ ->
            calcViewModel.bmrHeight.value = value.toInt()
        }

        val sliderBMR_age = binding.seekBMRAge
        sliderBMR_age.addOnChangeListener { _, value, _ ->
            calcViewModel.bmrAge.value = value.toInt()
        }

        val sliderBMR_activity = binding.seekBMRActivity
        sliderBMR_activity.addOnChangeListener { _, value, _ ->
            calcViewModel.bmrActivity.value = value.toDouble()
        }

        binding.radioGroupSex.setOnCheckedChangeListener { _, checkedId ->
            calcViewModel.bmrSex.value = checkedId == binding.radioButtonMen.id
            calcViewModel.BMR()
        }

        calcViewModel.bmrWeight.observe(viewLifecycleOwner) {
            binding.textBMRWeightU.text = "${getString(R.string.waga)} = $it kg"
            calcViewModel.BMR()
        }
        calcViewModel.bmrHeight.observe(viewLifecycleOwner) {
            binding.textBMRHeightU.text = "${getString(R.string.wzrost)} = $it cm"
            calcViewModel.BMR()
        }
        calcViewModel.bmrAge.observe(viewLifecycleOwner) {
            calcViewModel.BMR()
        }
        calcViewModel.bmrRes.observe(viewLifecycleOwner) {
            binding.textBMRRes.text = "${it.toInt()} kcal"
        }
        calcViewModel.bmrActivity.observe(viewLifecycleOwner) {
            calcViewModel.BMR()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}