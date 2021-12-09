package taher.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.utils.ProgressLoading
import com.google.android.material.floatingactionbutton.FloatingActionButton
import taher.restaurant.MainViewModel
import taher.restaurant.R
import taher.restaurant.base.BaseActivity
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.items.ItemsEntity
import taher.restaurant.databinding.ActivityNewOrderBinding
import taher.restaurant.databinding.NavHeaderMainBinding

private const val TAG = "NewOrderActivity"

class NewOrderActivity : BaseActivity<ActivityNewOrderBinding>(), NewOrderItemAdapter.OnItemClick, NewOrderCategoryAdapter.OnCategoryClick {

    lateinit var binding: ActivityNewOrderBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapterItems: NewOrderItemAdapter
    lateinit var adapter: NewOrderCategoryAdapter
    lateinit var selectedAdapter: SelectedItemsAdapter
    private var toggle: ActionBarDrawerToggle? = null

    var fullList = ArrayList<ItemsEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar?.title = "3Gril"

        setRecycler()
        setUpNavHeader()
        setUpNavDrawer()
        setObserver()

        viewModel.getData()


        binding.order.setOnClickListener {

            val intent = Intent(this, ConfirmOrderActivity::class.java)
            intent.putExtra("ORDER", selectedAdapter.getMyData())
            startActivity(intent)
        }

        /*

                binding.addNewCategory.setOnClickListener {

                    val dialogBuilder = AlertDialog.Builder(this@NewOrderActivity) // ...Irrelevant code for customizing the buttons and title
                    val inflater = this.layoutInflater

                    val dialogView = inflater.inflate(R.layout.add_new_category, null)
                    dialogBuilder.setView(dialogView)
                    val add = dialogView.findViewById<View>(R.id.add) as Button
                    val close = dialogView.findViewById<View>(R.id.close) as ImageView


                    val name = dialogView.findViewById<View>(R.id.name) as EditText

                    val alertDialog = dialogBuilder.create()
                    add.setOnClickListener {
                        alertDialog.dismiss()

                        viewModel.saveCategory(name.text.toString())

                    }
                    close.setOnClickListener {
                        alertDialog.dismiss()

                    }

                    alertDialog.show()


                }
        */

    }

    private fun setObserver() {

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                10 -> {

                    ProgressLoading.dismiss()
                    viewModel.getCategory()
                }
                1 -> {

                    ProgressLoading.show(this)
                }

            }
        })

        viewModel.categories.observe(this, androidx.lifecycle.Observer {


            val lsit = it
            lsit.add(0, CategoriesEntity(0, "الكل", true))
            adapter.setMyData(lsit)
        })
        viewModel.items.observe(this, androidx.lifecycle.Observer {


            fullList = it
            adapterItems.setMyData(fullList)
        })

    }


    private fun setRecycler() {


        adapterItems = NewOrderItemAdapter(this, ArrayList(), this)
        val layoutManager = GridLayoutManager(this, 4)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.items.layoutManager = layoutManager
        binding.items.adapter = adapterItems




        adapter = NewOrderCategoryAdapter(this, ArrayList(), this)
        binding.category.layoutManager = LinearLayoutManager(this)
        binding.category.adapter = adapter




        selectedAdapter = SelectedItemsAdapter(this)
        binding?.recyclerViewSelected?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSelected?.adapter = selectedAdapter

    }

    private fun setUpNavDrawer() {

        toggle = object :
            ActionBarDrawerToggle(this, binding.drawerLayout, binding!!.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle?.syncState()
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            when (menuItem.itemId) {
                R.id.orders -> {

                }

            }
            true
        }

    }

    private fun setUpNavHeader() {
        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_main, binding!!.navView, false)

        binding!!.navView.addHeaderView(headerMainBinding.root)


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_new_order
    }

    override fun setOnCategoryClick(model: CategoriesEntity) {

        if (model.itemID == 0) {
            adapterItems.setMyData(fullList)

        }
        else {
            val list = ArrayList<ItemsEntity>()
            for (i in fullList) {
                if (i.categoryId == model.itemID) {
                    list.add(i)
                }
            }
            adapterItems.setMyData(list)
        }


    }

    override fun setOnItemClick(model: ItemsEntity) {


        val dialogBuilder = AlertDialog.Builder(this@NewOrderActivity) // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater

        val dialogView = inflater.inflate(R.layout.select_item, null)
        dialogBuilder.setView(dialogView)
        val add = dialogView.findViewById<View>(R.id.add) as Button
        val close = dialogView.findViewById<View>(R.id.close) as ImageView


        val decreaseBtn = dialogView.findViewById<View>(R.id.decreaseBtn) as FloatingActionButton
        val increaseBtn = dialogView.findViewById<View>(R.id.increaseBtn) as FloatingActionButton

        val notes = dialogView.findViewById<View>(R.id.notes) as EditText
        val countText = dialogView.findViewById<View>(R.id.count) as EditText

        var count = 1
        increaseBtn.setOnClickListener(View.OnClickListener {

            count += 1
            countText.setText(count.toString())
        })


        decreaseBtn.setOnClickListener(View.OnClickListener {
            if (count != 1) count -= 1
            countText.setText(count.toString())

        })


        val alertDialog = dialogBuilder.create()
        add.setOnClickListener {
            alertDialog.dismiss()

            model.note = notes.text.toString()
            model.amount = count
            selectedAdapter.addItem(model)

        }
        close.setOnClickListener {
            alertDialog.dismiss()

        }

        alertDialog.show()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_new_order_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sync -> {

                viewModel.syncData()
                true

            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}