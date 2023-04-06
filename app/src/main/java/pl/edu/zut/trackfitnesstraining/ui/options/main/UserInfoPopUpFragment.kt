package pl.edu.zut.trackfitnesstraining.ui.options.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.databinding.FragmentBasicInfoPopUpBinding

@AndroidEntryPoint
class UserInfoPopUpFragment : DialogFragment() {

    private var _binding: FragmentBasicInfoPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: SaveInfoBtnClickListener

    fun setListener(listener: SaveInfoBtnClickListener) {
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
        _binding = FragmentBasicInfoPopUpBinding.inflate(inflater, container, false)
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
            val nick = binding.textName.text.toString()
            val age = binding.textAge.text.toString()
            val height = binding.textHeight.text.toString()
            val weight = binding.textWeight.text.toString()
            if (nick.isNotEmpty() && age.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()) {
                listener.onSaveInfoTask(nick, age, height, weight)
            } else {
                Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface SaveInfoBtnClickListener {
        fun onSaveInfoTask(nick: String, age: String, height: String, weight: String)
    }
}