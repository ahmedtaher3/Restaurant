package taher.restaurant.ui.addupdate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.extras.ExtrasEntity
import taher.restaurant.databinding.ListItemBinding

class ExtraAdapter(private val context: AppCompatActivity,
                   private var myData: ArrayList<ExtrasEntity>,
                   private val myInterface: OnExtraClick) : RecyclerView.Adapter<ExtraAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface OnExtraClick {
        fun setOnItemClick(model: ExtrasEntity)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]

        holder._binding?.name?.text = model.name
        holder.itemView.setOnClickListener { myInterface.setOnItemClick(model) }
    }

    fun setMyData(list: ArrayList<ExtrasEntity>) {
        this.myData = list
        notifyDataSetChanged()
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