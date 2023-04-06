package pl.edu.zut.trackfitnesstraining.ui.options.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.edu.zut.trackfitnesstraining.databinding.FragmentTimerBinding


class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timerViewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener {
            timerViewModel.changeColor()
            if (timerViewModel.flag.value == true) {
                timerViewModel.pauseTimer()
            } else {
                timerViewModel.startTimer()

            }
        }

        binding.buttonErase.setOnClickListener {
            timerViewModel.eraseTime()
        }

        timerViewModel.color.observe(viewLifecycleOwner) {
            binding.timerImage.drawable.setTint(it)
        }

        timerViewModel.timer.observe(viewLifecycleOwner) {
            val min = (it / 1000) / 60
            val sec = (it / 1000) % 60
            binding.minutes.text = String.format("%02d", min)
            binding.seconds.text = String.format("%02d", sec)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}