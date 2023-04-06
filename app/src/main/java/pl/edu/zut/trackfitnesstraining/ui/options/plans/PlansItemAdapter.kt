package pl.edu.zut.trackfitnesstraining.ui.options.plans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.databinding.ItemInPlanLayoutBinding
import pl.edu.zut.trackfitnesstraining.util.BodyPart

class PlansItemAdapter(
) : RecyclerView.Adapter<PlansItemAdapter.MyViewHolder>() {
    private var list: MutableList<Exercise?> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlansItemAdapter.MyViewHolder {
        val itemView =
            ItemInPlanLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlansItemAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    fun updateList(list: MutableList<Exercise?>) {
        val listC: MutableList<Exercise?> = arrayListOf()
        for (Exercise in list)
            listC.add(Exercise)

        this.list = listC
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemInPlanLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            binding.textName.text = item.name
            when (item.category) {
                BodyPart.LEGS -> binding.image.setImageResource(R.drawable.pic_leg)
                BodyPart.BACK -> binding.image.setImageResource(R.drawable.pic_back)
                BodyPart.BICEPS -> binding.image.setImageResource(R.drawable.pic_biceps)
                BodyPart.TRICEPS -> binding.image.setImageResource(R.drawable.pic_triceps)
                BodyPart.ABDOMINAL -> binding.image.setImageResource(R.drawable.pic_abdominal)
                BodyPart.CALVES -> binding.image.setImageResource(R.drawable.pic_calves)
                BodyPart.CHEST -> binding.image.setImageResource(R.drawable.pic_chest)
                BodyPart.RUNNING -> binding.image.setImageResource(R.drawable.pic_running)
                BodyPart.GLUTES -> binding.image.setImageResource(R.drawable.pic_glutes)
                BodyPart.SHOULDER -> binding.image.setImageResource(R.drawable.pic_shoulder)
                BodyPart.FOREARMS -> binding.image.setImageResource(R.drawable.pic_forearms)
            }
        }
    }
}