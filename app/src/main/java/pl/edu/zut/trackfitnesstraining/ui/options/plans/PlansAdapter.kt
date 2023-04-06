package pl.edu.zut.trackfitnesstraining.ui.options.plans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.zut.trackfitnesstraining.data.model.Plan
import pl.edu.zut.trackfitnesstraining.databinding.PlanItemLayoutBinding

class PlansAdapter(private val onItemClicked: (Plan) -> Unit) :
    RecyclerView.Adapter<PlansAdapter.MyViewHolder>() {
    private var list: MutableList<Plan?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansAdapter.MyViewHolder {

        val itemView =
            PlanItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlansAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener {
            if (item != null) {
                onItemClicked(item)
            }
        }
        if (item != null) {
            holder.bind(item)
        }
    }

    fun updateList(list: MutableList<Plan?>) {
        val listC: MutableList<Plan?> = arrayListOf()
        for (plan in list) {
            listC.add(plan)
        }
        this.list = listC
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: PlanItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = itemView.context
        fun bind(item: Plan) {
            binding.textPlanName.text = item.name
        }
    }
}