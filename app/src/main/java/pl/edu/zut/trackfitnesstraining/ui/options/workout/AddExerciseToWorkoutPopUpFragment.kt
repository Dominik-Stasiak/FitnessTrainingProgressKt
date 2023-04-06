package pl.edu.zut.trackfitnesstraining.ui.options.workout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddExerciseToPlanPopUpBinding
import pl.edu.zut.trackfitnesstraining.ui.options.exercises.ExerciseViewModel
import pl.edu.zut.trackfitnesstraining.util.UiResState
import pl.edu.zut.trackfitnesstraining.util.toast

@AndroidEntryPoint
class AddExerciseToWorkoutPopUpFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddExerciseToPlanPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveBtnClickListener
    private var selectedExercise: Exercise? = null
    private var exercises = listOf<Exercise?>()
    private lateinit var spinner: Spinner
    private lateinit var spinText: String
    private lateinit var exerciseViewModel: ExerciseViewModel


    fun setListener(listener: SaveBtnClickListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentAddExerciseToPlanPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseViewModel.getExercise()
        exerciseViewModel.exercise.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiResState.Loading -> {
                }
                is UiResState.Failure -> {
                    state.error?.let { toast(it) }
                }
                is UiResState.Succes -> {
                    exercises = state.data
                    spinner = binding.spinnerExercise
                    spinner.onItemSelectedListener = this
                    val aa = context?.let {
                        ArrayAdapter(
                            it,
                            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                            exercises
                        )
                    }
                    spinner.adapter = aa
                    event()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun event() {
        binding.buttonSave.setOnClickListener {
            if (selectedExercise != null) {
                listener.onSaveTask(selectedExercise!!)
            } else {
                Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface SaveBtnClickListener {
        fun onSaveTask(exercise: Exercise)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedExercise = exercises[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        spinText = ""
    }


}

