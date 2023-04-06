package pl.edu.zut.trackfitnesstraining.ui.options.workout

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.model.Workout
import pl.edu.zut.trackfitnesstraining.data.model.actualTime
import pl.edu.zut.trackfitnesstraining.data.model.simpleDate
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddWorkoutBinding
import java.util.*
import kotlin.math.abs

@AndroidEntryPoint
class AddWorkoutFragment : Fragment(), AddExerciseToWorkoutPopUpFragment.SaveBtnClickListener {

    private lateinit var workoutViewModel: WorkoutViewModel
    private var _binding: FragmentAddWorkoutBinding? = null
    private lateinit var popUpFragment: AddExerciseToWorkoutPopUpFragment
    private val binding get() = _binding!!
    private val adapter by lazy {
        ExerciseWorkoutAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        _binding = FragmentAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startTime = binding.textStartWorkout
        startTime.text = actualTime(0)
        startTime.setOnClickListener {
            showTimePickerDialog(startTime)
        }
        val endTime = binding.textEndWorkout
        endTime.text = actualTime(1)
        endTime.setOnClickListener {
            showTimePickerDialog(endTime)
        }
        val calDate = binding.textCal
        calDate.text = simpleDate.format(Date())
        calDate.setOnClickListener {
            val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = simpleDate.parse("$dayOfMonth.${month+1}.$year")
                val selectedDate = simpleDate.format(date)
                calDate.text=selectedDate
            }
            val datePickerDialog = context?.let {
                DatePickerDialog(
                    it, dateListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            }
            datePickerDialog?.show()
        }


        binding.buttonAddExercise.setOnClickListener {
            popUpFragment = AddExerciseToWorkoutPopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddExerciseToPlanPopUpFragment"
            )
            adapter.notifyDataSetChanged()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(layoutInflater.context)


        workoutViewModel.exercises.observe(viewLifecycleOwner) {
            adapter.list = it
            adapter.notifyDataSetChanged()
            Log.d("tag", "adapter.toString()")
        }

        binding.buttonSubmit.setOnClickListener {
            if (adapter.list.size == 0) {
                Toast.makeText(context,"Uzupełnij listę ćwiczeń",Toast.LENGTH_SHORT).show()
            } else {
                val workoutTime = workoutTime(endTime, startTime)
                if (workoutTime > 0) {
                    workoutViewModel.addWorkout(
                        Workout(
                            kcal = (5.2 * workoutTime).toInt(),
                            minutes = workoutTime(endTime, startTime),
                            exercises = workoutViewModel.getExercises(),
                            date = calDate.text.toString()
                        )
                    )
                    workoutViewModel.clearExercises()
                    adapter.notifyDataSetChanged()
                    this.findNavController()
                        .navigate(AddWorkoutFragmentDirections.actionAddWorkoutFragmentToMainFragment())
                } else {
                    Toast.makeText(context, getString(R.string.uzupelnijCzas), Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun workoutTime(endTime: TextView, startTime: TextView): Int {
        val endParts = endTime.text.split(":")
        val endHours = endParts[0].toIntOrNull()
        val endMinutes = endParts[1].toIntOrNull()

        val startParts = startTime.text.split(":")
        val startHours = startParts[0].toIntOrNull()
        val startMinutes = startParts[1].toIntOrNull()

        val workoutTime: Int
        workoutTime =
            if (endHours!! < startHours!! || (endHours == startHours && endMinutes!! < startMinutes!!)) {
                1440 - ((abs(endHours - startHours) * 60) + abs(endMinutes!! - startMinutes!!))
            } else {
                (abs(endHours - startHours) * 60) + abs(endMinutes!! - startMinutes!!)
            }
        return workoutTime
    }

    private fun showTimePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                textView.text = String.format("%02d:%02d", hourOfDay, minute)
            },
            currentHour,
            currentMinute,
            true
        )
        timePickerDialog.show()
    }

    override fun onSaveTask(exercise: Exercise) {
        workoutViewModel.addExercise(exercise)
        popUpFragment.dismiss()
        adapter.notifyDataSetChanged()
    }
}