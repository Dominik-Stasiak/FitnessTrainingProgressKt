package pl.edu.zut.trackfitnesstraining.ui.options.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.Measurment
import pl.edu.zut.trackfitnesstraining.data.model.User
import pl.edu.zut.trackfitnesstraining.data.model.Weight
import pl.edu.zut.trackfitnesstraining.databinding.FragmentMainBinding
import pl.edu.zut.trackfitnesstraining.ui.options.workout.WorkoutViewModel
import pl.edu.zut.trackfitnesstraining.util.UiResState
import pl.edu.zut.trackfitnesstraining.util.hide
import pl.edu.zut.trackfitnesstraining.util.show

@AndroidEntryPoint
class MainFragment : Fragment(), UserInfoPopUpFragment.SaveInfoBtnClickListener,
    UserWeightPopUpFragment.SaveWeightBtnClickListener,
    UserMeasurmentPopUpFragment.SaveMeasurmentBtnClickListener {
    private val TAG = "MAIN FRAGMENT"
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var popUpInfoFragment: UserInfoPopUpFragment
    private lateinit var popUpWeightFragment: UserWeightPopUpFragment
    private lateinit var popUpMeasurmentFragment: UserMeasurmentPopUpFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddWorkout.setOnClickListener {
            this.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToAddWorkoutFragment())
        }

        binding.buttonAddInfo.setOnClickListener {
            popUpInfoFragment = UserInfoPopUpFragment()
            popUpInfoFragment.setListener(this)
            popUpInfoFragment.show(
                childFragmentManager,
                "AddInfoPopUpFragment"
            )
        }

        binding.buttonAddWeight.setOnClickListener {
            popUpWeightFragment = UserWeightPopUpFragment()
            popUpWeightFragment.setListener(this)
            popUpWeightFragment.show(
                childFragmentManager,
                "AddWeightPopUpFragment"
            )
        }

        binding.buttonAddMeasurment.setOnClickListener {
            popUpMeasurmentFragment = UserMeasurmentPopUpFragment()
            popUpMeasurmentFragment.setListener(this)
            popUpMeasurmentFragment.show(
                childFragmentManager,
                "AddMeasurmentPopUpFragment"
            )
        }
        mainViewModel.getUserInfo()
        mainViewModel.getUserWeight()
        mainViewModel.getUserMeasurment()


        mainViewModel.userWeight.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiResState.Loading -> {
                    Log.e(TAG, "Loading")
                }
                is UiResState.Failure -> {
                    Log.e(TAG, state.error.toString())
                }
                is UiResState.Succes -> {
                    if (state.data.isEmpty())
                        binding.textWeight.text = getString(R.string.uzupelnij)
                    else{
                        val w = mainViewModel.getLatestWeight()
                        val weight = w?.weight
                        binding.textWeight.text =
                            "%.2f".format(weight) + "kg"
                    }

                }
            }
        }

        mainViewModel.userMeasurment.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiResState.Loading -> {
                    Log.e(TAG, "Loading")
                }
                is UiResState.Failure -> {
                    Log.e(TAG, state.error.toString())
                }
                is UiResState.Succes -> {
                    if (state.data.isEmpty())
                        binding.textFat.text = getString(R.string.uzupelnij)
                    else {
                        val m = mainViewModel.getLatestMeasurment()
                        val fat = (m!!.weist + m.hip - m.tigh) * 0.158
                        binding.textFat.text = "%.2f".format(fat) + "%"
                    }
                }
            }
        }
        workoutViewModel.getWorkout()
        workoutViewModel.workout.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiResState.Loading -> {
                    Log.e(TAG, "Loading")
                }
                is UiResState.Failure -> {
                    Log.e(TAG, state.error.toString())
                }
                is UiResState.Succes -> {
                    if (state.data.isEmpty()) {
                        binding.textKcal.text = getString(R.string.uzupelnij)
                        binding.textWorkout.text = getString(R.string.uzupelnij)
                    } else {
                        binding.textKcal.text = "${workoutViewModel.getKcal()} kcal"
                        binding.textWorkout.text =
                            "${getString(R.string.liczbaTren)} = ${workoutViewModel.getNumberOfWorkouts()}"
                    }
                }
            }
        }
    }


    override fun onSaveInfoTask(nick: String, age: String, height: String, weight: String) {
        val user =
            User(
                nick = nick,
                age = Integer.valueOf(age),
                height = Integer.valueOf(height),
                weight = Integer.valueOf(weight)
            )
        mainViewModel.addUserInfo(user)
        popUpInfoFragment.dismiss()
    }

    override fun onSaveMeasurmentTask(
        neck: String,
        chest: String,
        hip: String,
        weist: String,
        biceps: String,
        tigh: String,
        calv: String,
        stomach: String,
        date: String
    ) {
        val measurment =
            Measurment(
                neck = Integer.valueOf(neck),
                chest = Integer.valueOf(chest),
                hip = Integer.valueOf(hip),
                weist = Integer.valueOf(weist),
                biceps = Integer.valueOf(biceps),
                tigh = Integer.valueOf(tigh),
                calv = Integer.valueOf(calv),
                stomach = Integer.valueOf(stomach),
                date = date
            )
        mainViewModel.addUserMeasurment(measurment)
        popUpMeasurmentFragment.dismiss()
    }

    override fun onSaveWeightTask(weight: String, date: String) {
        val weight = Weight(weight = weight.toDouble(), date = date)
        mainViewModel.addUserWeight(weight)
        popUpWeightFragment.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}