package pl.edu.zut.trackfitnesstraining.ui.options.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.model.Plan
import pl.edu.zut.trackfitnesstraining.databinding.FragmentPlanDetailBinding
import pl.edu.zut.trackfitnesstraining.util.FireStoreTables

@AndroidEntryPoint
class PlanDetailFragment : Fragment(), AddExerciseToPlanPopUpFragment.SaveBtnClickListener {

    private var _binding: FragmentPlanDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var plansViewModel: PlansViewModel
    private lateinit var id: String
    private lateinit var popUpFragment: AddExerciseToPlanPopUpFragment
    private val navigationArgs: PlanDetailFragmentArgs by navArgs()
    val database = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        plansViewModel = ViewModelProvider(this)[PlansViewModel::class.java]
        _binding = FragmentPlanDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = navigationArgs.planId

        refresh()

        binding.floatingActionButton.setOnClickListener {
            popUpFragment = AddExerciseToPlanPopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddExerciseToPlanPopUpFragment"
            )
        }
    }

    private fun refresh() {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.PLANS)
            .collection(FireStoreTables.PLANS)
            .document(id)
            .get()
            .addOnSuccessListener {
                bind(it.toObject(Plan::class.java)!!)
            }
    }

    private fun bind(plan: Plan) {
        binding.apply {
            textPlanName.text = plan.name
            val adapter = PlansItemAdapter()
            recyclerPlan.adapter = adapter
            recyclerPlan.layoutManager = LinearLayoutManager(layoutInflater.context)
            adapter.updateList(plan.exercises.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveTask(exercise: Exercise) {
        var mutableExercise: MutableList<Exercise>

        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.PLANS)
            .collection(FireStoreTables.PLANS)
            .document(id)
            .get()
            .addOnSuccessListener {
                val plan = it.toObject(Plan::class.java)!!
                mutableExercise = plan.exercises as MutableList<Exercise>
                mutableExercise.add(exercise)
                database.collection(auth.currentUser?.uid.toString())
                    .document(FireStoreTables.PLANS)
                    .collection(FireStoreTables.PLANS)
                    .document(id)
                    .update("exercises", mutableExercise)
                refresh()
            }
        popUpFragment.dismiss()
    }
}