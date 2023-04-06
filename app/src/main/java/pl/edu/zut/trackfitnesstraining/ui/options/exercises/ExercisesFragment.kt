package pl.edu.zut.trackfitnesstraining.ui.options.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.databinding.FragmentExercisesBinding
import pl.edu.zut.trackfitnesstraining.util.*

@AndroidEntryPoint
class ExercisesFragment : Fragment(), AddExercisePopUpFragment.SaveBtnClickListener {
    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!
    private lateinit var popUpFragment: AddExercisePopUpFragment
    private lateinit var exerciseViewModel: ExerciseViewModel

    private val adapterChest by lazy { ExerciseAdapter(BodyPart.CHEST) }
    private val adapterBack by lazy { ExerciseAdapter(BodyPart.BACK) }
    private val adapterAbdominal by lazy { ExerciseAdapter(BodyPart.ABDOMINAL) }
    private val adapterLegs by lazy { ExerciseAdapter(BodyPart.LEGS) }
    private val adapterShoulder by lazy { ExerciseAdapter(BodyPart.SHOULDER) }
    private val adapterCalves by lazy { ExerciseAdapter(BodyPart.CALVES) }
    private val adapterGlutes by lazy { ExerciseAdapter(BodyPart.GLUTES) }
    private val adapterBiceps by lazy { ExerciseAdapter(BodyPart.BICEPS) }
    private val adapterTriceps by lazy { ExerciseAdapter(BodyPart.TRICEPS) }
    private val adapterForearms by lazy { ExerciseAdapter(BodyPart.FOREARMS) }
    private val adapterRunning by lazy { ExerciseAdapter(BodyPart.RUNNING) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        _binding = FragmentExercisesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerChest.adapter = adapterChest
        binding.recyclerChest.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerBack.adapter = adapterBack
        binding.recyclerBack.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerAbdominal.adapter = adapterAbdominal
        binding.recyclerAbdominal.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerLegs.adapter = adapterLegs
        binding.recyclerLegs.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerShoulder.adapter = adapterShoulder
        binding.recyclerShoulder.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerCalves.adapter = adapterCalves
        binding.recyclerCalves.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerGlutes.adapter = adapterGlutes
        binding.recyclerGlutes.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerBiceps.adapter = adapterBiceps
        binding.recyclerBiceps.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerTriceps.adapter = adapterTriceps
        binding.recyclerTriceps.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerForearms.adapter = adapterForearms
        binding.recyclerForearms.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.recyclerRunning.adapter = adapterRunning
        binding.recyclerRunning.layoutManager = LinearLayoutManager(layoutInflater.context)

        binding.floatingActionButton.setOnClickListener {
            popUpFragment = AddExercisePopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddExercisePopUpFragment"
            )
        }
        exerciseViewModel.getExercise()
        exerciseViewModel.exercise.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiResState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiResState.Failure -> {
                    binding.progressCircular.hide()
                    state.error?.let { toast(it) }
                }
                is UiResState.Succes -> {
                    binding.progressCircular.hide()
                    adapterChest.updateList(state.data.toMutableList())
                    adapterBack.updateList(state.data.toMutableList())
                    adapterAbdominal.updateList(state.data.toMutableList())
                    adapterLegs.updateList(state.data.toMutableList())
                    adapterShoulder.updateList(state.data.toMutableList())
                    adapterCalves.updateList(state.data.toMutableList())
                    adapterGlutes.updateList(state.data.toMutableList())
                    adapterBiceps.updateList(state.data.toMutableList())
                    adapterRunning.updateList(state.data.toMutableList())
                    adapterTriceps.updateList(state.data.toMutableList())
                    adapterForearms.updateList(state.data.toMutableList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveTask(name: String, spin: String) {
        val exercise = Exercise(name = name, category = spin)
        exerciseViewModel.addExercise(exercise)
        popUpFragment.dismiss()
    }
}