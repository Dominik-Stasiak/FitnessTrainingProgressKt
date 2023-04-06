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
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.simpleDate
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddWeightPopUpBinding
import java.util.*

@AndroidEntryPoint
class UserWeightPopUpFragment : DialogFragment() {

    private var _binding: FragmentAddWeightPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveWeightBtnClickListener

    fun setListener(listener: SaveWeightBtnClickListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentAddWeightPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event()
        val calDate = binding.textCal
        calDate.text = simpleDate.format(Date())
        calDate.setOnClickListener {
            val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val date = simpleDate.parse("$dayOfMonth.${month + 1}.$year")
                val selectedDate = simpleDate.format(date)
                calDate.text = selectedDate
            }
            val datePickerDialog = context?.let {
                DatePickerDialog(
                    it, dateListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
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

            val weight = binding.textWeight.text.toString()

            if (weight.isNotEmpty()) {
                if (weight.toDouble() in 20.0..500.0) {
                    listener.onSaveWeightTask(weight, binding.textCal.text.toString())
                } else if (weight.toDouble() < 20.0 || weight.toDouble() > 500.0) {
                    Toast.makeText(context, getString(R.string.wagaZakres), Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, getString(R.string.uzupelnijWage), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    interface SaveWeightBtnClickListener {
        fun onSaveWeightTask(weight: String, date: String)
    }
}