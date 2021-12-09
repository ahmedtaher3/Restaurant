package taher.restaurant.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import taher.restaurant.data.room.items.ItemsEntity
import taher.restaurant.databinding.OrderPrintItemBinding


class OrderPrintAdapter(private val context: Context, private var myData: ArrayList<ItemsEntity>, private var onRemoveItem: OnRemoveItem

) : RecyclerView.Adapter<OrderPrintAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrderPrintItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    interface OnRemoveItem {
        fun setOnRemoveItem(model: ItemsEntity)
    }

    fun setMyData(myData: ArrayList<ItemsEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<ItemsEntity> {
        return this.myData
    }

    fun addItem(model: ItemsEntity) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder._binding?.name?.text = model.name
        holder._binding?.price?.text = model.price.toString()
        holder._binding?.unit?.text = model.amount.toString()
        holder._binding?.amount?.text = (model.amount!! * model.price!!).toString()
        holder._binding?.delete?.setOnClickListener {
            onRemoveItem.setOnRemoveItem(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: OrderPrintItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var _binding: OrderPrintItemBinding? = null

        init {
            this._binding = binding;


        }

    }

}