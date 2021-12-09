package taher.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import taher.restaurant.databinding.ListItemBinding
import taher.restaurant.model.CardModel

class MenuListAdapter(context: Context, private val myData: List<CardModel>, private val onHomeItemClick: OnHomeItemClick) :
    RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface OnHomeItemClick {
        fun setOnHomeItemClick(model: CardModel)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]

        holder._binding?.name?.text = model.name
        holder.itemView.setOnClickListener { onHomeItemClick.setOnHomeItemClick(model) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class MyViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var _binding: ListItemBinding? = null

        init {
            this._binding = binding;


        }

    }


}