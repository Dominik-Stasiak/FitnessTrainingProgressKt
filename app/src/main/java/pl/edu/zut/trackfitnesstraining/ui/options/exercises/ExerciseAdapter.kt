package pl.edu.zut.trackfitnesstraining.ui.options.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.databinding.ExerciseItemLayoutBinding
import pl.edu.zut.trackfitnesstraining.util.BodyPart

class ExerciseAdapter(
    val category: String
) : RecyclerView.Adapter<ExerciseAdapter.MyViewHolder>() {
    private var list: MutableList<Exercise?> = arrayListOf()
    private var pic: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ExerciseItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    fun updateList(list: MutableList<Exercise?>) {
        val listC: MutableList<Exercise?> = arrayListOf()
        if (category == BodyPart.CHEST) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.CHEST) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_chest
                }
            }
        } else if (category == BodyPart.BACK) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.BACK) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_back
                }
            }
        } else if (category == BodyPart.ABDOMINAL) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.ABDOMINAL) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_abdominal
                }
            }
        } else if (category == BodyPart.LEGS) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.LEGS) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_leg
                }
            }
        } else if (category == BodyPart.SHOULDER) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.SHOULDER) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_shoulder
                }
            }
        } else if (category == BodyPart.CALVES) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.CALVES) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_calves
                }
            }
        } else if (category == BodyPart.GLUTES) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.GLUTES) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_glutes
                }
            }
        } else if (category == BodyPart.BICEPS) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.BICEPS) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_biceps
                }
            }
        } else if (category == BodyPart.TRICEPS) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.TRICEPS) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_triceps
                }
            }
        } else if (category == BodyPart.FOREARMS) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.FOREARMS) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_forearms
                }
            }
        } else if (category == BodyPart.RUNNING) {
            for (Exercise in list) {
                if (Exercise?.category == BodyPart.RUNNING) {
                    listC.add(Exercise)
                    pic = R.drawable.pic_running
                }
            }
        }
        this.list = listC
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ExerciseItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            binding.textName.text = item.name
            binding.image.setImageResource(pic)
        }
    }
}