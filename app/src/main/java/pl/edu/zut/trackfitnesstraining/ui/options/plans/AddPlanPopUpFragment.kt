package pl.edu.zut.trackfitnesstraining.ui.options.plans

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.databinding.FragmentAddPlanPopUpBinding

@AndroidEntryPoint
class AddPlanPopUpFragment : DialogFragment() {

    private var _binding: FragmentAddPlanPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveBtnClickListener
    private var planName = ""


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
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentAddPlanPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun event() {
        binding.buttonSave.setOnClickListener {
            planName = binding.planName.text.toString()
            if (planName.isNotBlank()) {
                listener.onSaveTask(planName)
            } else {
                Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface SaveBtnClickListener {
        fun onSaveTask(planName: String)
    }

}

