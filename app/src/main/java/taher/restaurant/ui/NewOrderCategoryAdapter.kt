package taher.restaurant.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import taher.restaurant.R
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.databinding.CategoryItemBinding

class NewOrderCategoryAdapter(private val context: AppCompatActivity,
                              private var myData: ArrayList<CategoriesEntity>,
                              private val myInterface: OnCategoryClick) : RecyclerView.Adapter<NewOrderCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        return MyViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface OnCategoryClick {
        fun setOnCategoryClick(model: CategoriesEntity)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]

        if (model.selected) {
            holder._binding?.layout?.setBackgroundResource(R.drawable.rounded_selected)
            holder._binding?.name?.setTextColor(context.resources.getColor(R.color.white))
        }
        else {
            holder._binding?.layout?.setBackgroundResource(R.drawable.rounded_not_selected)
            holder._binding?.name?.setTextColor(context.resources.getColor(R.color.colorPrimary))
        }

        holder._binding?.name?.text = model.name
        holder.itemView.setOnClickListener {

            for (m in myData) {
                m.selected = false
                notifyDataSetChanged()
            }
            myData[position].selected = true
            notifyDataSetChanged()
            myInterface.setOnCategoryClick(model)


        }
    }

    fun setMyData(list: ArrayList<CategoriesEntity>) {
        this.myData = list
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class MyViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var _binding: CategoryItemBinding? = null

        init {
            this._binding = binding;


        }

    }


}