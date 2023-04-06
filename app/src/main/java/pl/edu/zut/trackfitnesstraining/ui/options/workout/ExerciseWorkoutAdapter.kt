package pl.edu.zut.trackfitnesstraining.ui.options.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.databinding.ExerciseItemLayoutBinding
import pl.edu.zut.trackfitnesstraining.util.BodyPart

class ExerciseWorkoutAdapter(
) : RecyclerView.Adapter<ExerciseWorkoutAdapter.MyViewHolder>() {
    var list = mutableListOf<Exercise>()
    private var pic: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ExerciseItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ExerciseItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            binding.textName.text = item.name
            when {
                item.category == BodyPart.CHEST -> pic = R.drawable.pic_chest
                item.category == BodyPart.BICEPS -> pic = R.drawable.pic_biceps
                item.category == BodyPart.GLUTES -> pic = R.drawable.pic_glutes
                item.category == BodyPart.RUNNING -> pic = R.drawable.pic_running
                item.category == BodyPart.TRICEPS -> pic = R.drawable.pic_triceps
                item.category == BodyPart.FOREARMS -> pic = R.drawable.pic_forearms
                item.category == BodyPart.SHOULDER -> pic = R.drawable.pic_shoulder
                item.category == BodyPart.CALVES -> pic = R.drawable.pic_calves
                item.category == BodyPart.ABDOMINAL -> pic = R.drawable.pic_abdominal
                item.category == BodyPart.BACK -> pic = R.drawable.pic_back
                item.category == BodyPart.LEGS -> pic = R.drawable.pic_leg
            }
            binding.image.setImageResource(pic)
        }
    }
}