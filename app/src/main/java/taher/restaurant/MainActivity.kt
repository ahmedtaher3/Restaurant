package taher.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import taher.restaurant.base.BaseActivity
import taher.restaurant.databinding.ActivityMainBinding
import taher.restaurant.databinding.NavHeaderMainBinding
import taher.restaurant.model.CardModel
import taher.restaurant.ui.NewOrderActivity
import taher.restaurant.ui.addupdate.AddAndUpdateActivity
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), MenuListAdapter.OnHomeItemClick {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MenuListAdapter
    private var toggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar?.title = "3Gril"

        setRecycler()
        setUpNavHeader()
        setUpNavDrawer()


    }

    private fun setRecycler() {
        val list = ArrayList<CardModel>()
        list.add(CardModel(1, "أوردر جديد"))
         list.add(CardModel(2, "أوردر دليفرى"))
         list.add(CardModel(3, "إضافة وتعديل"))


        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
    }

    private fun setUpNavDrawer() {

        toggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding!!.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
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
                R.id.orders ->  {

                }

            }
            true
        }

    }

    private fun setUpNavHeader() {
        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.nav_header_main, binding!!.navView, false
        )

        binding!!.navView.addHeaderView(headerMainBinding.root)


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when(model.id)
        {
            1->{

                startActivity(Intent(this, NewOrderActivity::class.java))

            }
            2->{}
            3->{
                startActivity(Intent(this, AddAndUpdateActivity::class.java))

            }
        }
    }
}