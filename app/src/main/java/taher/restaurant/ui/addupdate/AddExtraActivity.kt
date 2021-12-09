package taher.restaurant.ui.addupdate

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.utils.ProgressLoading
import taher.restaurant.AddNewViewModel
import taher.restaurant.MainViewModel
import taher.restaurant.R
import taher.restaurant.base.BaseActivity
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.extras.ExtrasEntity
import taher.restaurant.databinding.ActivityAddNewBinding
import java.util.*

class AddExtraActivity : BaseActivity<ActivityAddNewBinding>(), ExtraAdapter.OnExtraClick {

    lateinit var binding: ActivityAddNewBinding
    lateinit var viewModel: AddNewViewModel
    lateinit var adapter: ExtraAdapter

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

            val dialogBuilder = AlertDialog.Builder(this@AddExtraActivity) // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.add_new_extra, null)
            dialogBuilder.setView(dialogView)
            val add = dialogView.findViewById<View>(R.id.add) as Button
            val close = dialogView.findViewById<View>(R.id.close) as ImageView


            val name = dialogView.findViewById<View>(R.id.name) as EditText
            val price = dialogView.findViewById<View>(R.id.price) as EditText

            val alertDialog = dialogBuilder.create()
            add.setOnClickListener {
                alertDialog.dismiss()

                viewModel.saveExtra(name.text.toString() , price.text.toString())

            }
            close.setOnClickListener {
                alertDialog.dismiss()

            }

            alertDialog.show()


        }


    }

    private fun setRecycler() {

        adapter = ExtraAdapter(this, ArrayList(), this)
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

        viewModel.extras.observe(this, androidx.lifecycle.Observer {

            val lsit = it
            adapter.setMyData(lsit)
        })


    }


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new
    }



    override fun setOnItemClick(model: ExtrasEntity) {

    }


}