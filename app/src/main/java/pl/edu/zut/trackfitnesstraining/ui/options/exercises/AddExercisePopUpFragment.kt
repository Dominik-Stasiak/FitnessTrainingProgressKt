package pl.edu.zut.trackfitnesstraining.ui.options.exercises

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddExercisePopUpBinding
import pl.edu.zut.trackfitnesstraining.util.BodyPart

@AndroidEntryPoint
class AddExercisePopUpFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    private var _binding : FragmentAddExercisePopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveBtnClickListener
    private lateinit var spinText: String

    fun setListener(listener: SaveBtnClickListener) {
        this.listener =  listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentAddExercisePopUpBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exercises = arrayOf(
            getString(R.string.chest),
            getString(R.string.plecy),
            getString(R.string.stomach),
            getString(R.string.nogi),
            getString(R.string.obrecz_barkowa),
            getString(R.string.lydki),
            getString(R.string.posladki),
            getString(R.string.biceps),
            getString(R.string.triceps),
            getString(R.string.przedramiona),
            getString(R.string.bieganie)
        )
        val spinner = binding.spinner
        val aa = context?.let { ArrayAdapter(it, androidx.transition.R.layout.support_simple_spinner_dropdown_item, exercises) }
        spinner.onItemSelectedListener = this
        spinner.adapter = aa
        event()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun event() {
        binding.buttonSave.setOnClickListener {
            val name = binding.textName.text.toString()
            if (name.isNotEmpty() && spinText.isNotBlank()) {
                listener.onSaveTask(name, spinText)
            } else {
                Toast.makeText(context, getString(R.string.uzupelniPola), Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface SaveBtnClickListener {
        fun onSaveTask(name : String, spin : String)
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val exercises = BodyPart.allPart
        spinText = exercises[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        spinText = ""
    }
}

