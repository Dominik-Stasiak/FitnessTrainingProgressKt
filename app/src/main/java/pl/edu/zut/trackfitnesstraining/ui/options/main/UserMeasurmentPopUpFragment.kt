package pl.edu.zut.trackfitnesstraining.ui.options.main

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.data.model.simpleDate
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddMeasurmentPopUpBinding
import java.util.*

@AndroidEntryPoint
class UserMeasurmentPopUpFragment : DialogFragment() {

    private var _binding: FragmentAddMeasurmentPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveMeasurmentBtnClickListener

    fun setListener(listener: SaveMeasurmentBtnClickListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentAddMeasurmentPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun event() {
        binding.buttonSave.setOnClickListener {

            val neck = binding.textNeck.text.toString()
            val chest = binding.textChest.text.toString()
            val hip = binding.textHip.text.toString()
            val weist = binding.textWeist.text.toString()
            val biceps = binding.textBiceps.text.toString()
            val tigh = binding.textTigh.text.toString()
            val calv = binding.textCalv.text.toString()
            val stomach = binding.textStomach.text.toString()

            if (neck.isNotEmpty() && chest.isNotEmpty() && hip.isNotEmpty()
                && weist.isNotEmpty() && biceps.isNotEmpty() && tigh.isNotEmpty()
                && calv.isNotEmpty() && stomach.isNotEmpty()
            ) {
                listener.onSaveMeasurmentTask(
                    neck, chest, hip, weist, biceps,
                    tigh, calv, stomach, binding.textCal.text.toString()
                )
            } else {
                Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface SaveMeasurmentBtnClickListener {
        fun onSaveMeasurmentTask(
            neck: String,
            chest: String,
            hip: String,
            weist: String,
            biceps: String,
            tigh: String,
            calv: String,
            stomach: String,
            date:String
        )
    }
}