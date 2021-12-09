package taher.restaurant.ui.addupdate

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.utils.ProgressLoading
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import taher.restaurant.AddNewViewModel
import taher.restaurant.R
import taher.restaurant.base.BaseActivity
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.items.ItemsEntity
import taher.restaurant.databinding.ActivityAddNewBinding
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : BaseActivity<ActivityAddNewBinding>(), ItemAdapter.OnItemClick {

    lateinit var binding: ActivityAddNewBinding
    lateinit var viewModel: AddNewViewModel
    lateinit var adapter: ItemAdapter

    var categories = ArrayList<CategoriesEntity>()
    var categoriesNames = ArrayList<String>()
    var categoriesIdes = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(AddNewViewModel::class.java)
        setSupportActionBar(binding!!.toolbar)
        supportActionBar?.title = "3Gril"


        viewModel.getData()
        setObserver()
        setRecycler()




        binding.addNew.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this@AddItemActivity) // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.add_new_item, null)
            dialogBuilder.setView(dialogView)
            val add = dialogView.findViewById<View>(R.id.add) as Button
            val close = dialogView.findViewById<View>(R.id.close) as ImageView


            val name = dialogView.findViewById<View>(R.id.name) as EditText
            val price = dialogView.findViewById<View>(R.id.price) as EditText
            val priceOriginal = dialogView.findViewById<View>(R.id.priceOriginal) as EditText
            val priceBase = dialogView.findViewById<View>(R.id.priceBase) as EditText
            val notes = dialogView.findViewById<View>(R.id.notes) as EditText
            val categorySpinner = dialogView.findViewById<View>(R.id.categorySpinner) as MaterialBetterSpinner


            var catId = ""
            categorySpinner.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categoriesNames))
            categorySpinner.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
                catId = categoriesIdes[i].toString()
            })


            val alertDialog = dialogBuilder.create()
            add.setOnClickListener {
                alertDialog.dismiss()

                if (catId.isNullOrEmpty() ||
                    price.text.toString().isNullOrEmpty()||
                    priceOriginal.text.toString().isNullOrEmpty()||
                    priceBase.text.toString().isNullOrEmpty()
                )
                {

                    Toast.makeText(this, "اكمل البيانات", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    viewModel.saveItem(name.text.toString(),
                                       price.text.toString(),
                                       priceOriginal.text.toString(),
                                       priceBase.text.toString(),
                                       notes.text.toString(),
                                       catId)
                }



            }
            close.setOnClickListener {
                alertDialog.dismiss()

            }

            alertDialog.show()


        }


    }

    private fun setRecycler() {

        adapter = ItemAdapter(this, ArrayList(), this)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }

    private fun setObserver() {

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                10 -> {

                    ProgressLoading.dismiss()
                    viewModel.getData()
                }

                1 -> {

                    ProgressLoading.show(this)
                }

            }
        })

        viewModel.items.observe(this, androidx.lifecycle.Observer {

            val lsit = it
            adapter.setMyData(lsit)
        })

        viewModel.categories.observe(this, androidx.lifecycle.Observer {


            categories = it
            for (model in categories) {
                categoriesNames.add(model.name!!)
                categoriesIdes.add(model.itemID!!)
            }


        })

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new
    }

    override fun setOnItemClick(model: ItemsEntity) {


    }


}