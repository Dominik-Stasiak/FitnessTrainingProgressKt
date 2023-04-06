package pl.edu.zut.trackfitnesstraining.ui.options.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.data.model.Plan
import pl.edu.zut.trackfitnesstraining.databinding.FragmentPlansBinding
import pl.edu.zut.trackfitnesstraining.util.UiResState
import pl.edu.zut.trackfitnesstraining.util.hide
import pl.edu.zut.trackfitnesstraining.util.show
import pl.edu.zut.trackfitnesstraining.util.toast

@AndroidEntryPoint
class PlansFragment : Fragment(), AddPlanPopUpFragment.SaveBtnClickListener {

    private var _binding: FragmentPlansBinding? = null
    private val binding get() = _binding!!
    private lateinit var plansViewModel: PlansViewModel
    private lateinit var popUpFragment: AddPlanPopUpFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        plansViewModel = ViewModelProvider(this)[PlansViewModel::class.java]
        _binding = FragmentPlansBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlansAdapter {
            val action = PlansFragmentDirections.actionPlansFragmentToPlanDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerPlan.adapter = adapter
        binding.recyclerPlan.layoutManager = LinearLayoutManager(layoutInflater.context)

        plansViewModel.getPlan()

        binding.floatingActionButton.setOnClickListener {
            popUpFragment = AddPlanPopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddExercisePopUpFragment"
            )
        }

        plansViewModel.plans.observe(viewLifecycleOwner) { state ->
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
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveTask(planName: String) {
        val plan = Plan(name = planName)
        plansViewModel.addPlan(plan)
        popUpFragment.dismiss()
    }
}