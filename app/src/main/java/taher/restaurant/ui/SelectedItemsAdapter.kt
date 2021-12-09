package taher.restaurant.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import taher.restaurant.data.room.items.ItemsEntity
import taher.restaurant.databinding.SelectedItemsBinding

class SelectedItemsAdapter(context: Context) : RecyclerView.Adapter<SelectedItemsAdapter.MyViewHolder>() {
    private var myData: ArrayList<ItemsEntity>
    private val context: Context
    fun getMyData(): ArrayList<ItemsEntity> {
        return myData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        return MyViewHolder(SelectedItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder._binding?.selectedName?.text = model.name
        holder._binding?.selectedDelete?.setOnClickListener {
            myData.removeAt(position)
            notifyDataSetChanged()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: ArrayList<ItemsEntity>) {
        this.myData = myData.distinctBy { it.id } as ArrayList<ItemsEntity>
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var binding: SelectedItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        var _binding: SelectedItemsBinding? = null

        init {
            this._binding = binding;


        }

    }

    fun addItem(model: ItemsEntity) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    init {
        myData = ArrayList()
        this.context = context
    }
}