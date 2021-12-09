package taher.restaurant.ui.addupdate

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import taher.restaurant.MainViewModel
import taher.restaurant.MenuListAdapter
import taher.restaurant.R
import taher.restaurant.base.BaseActivity
import taher.restaurant.databinding.ActivityAddAndUpdateBinding
import taher.restaurant.model.CardModel
import java.util.*

class AddAndUpdateActivity : BaseActivity<ActivityAddAndUpdateBinding>(), MenuListAdapter.OnHomeItemClick {
    lateinit var binding: ActivityAddAndUpdateBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar?.title = "3Gril"

        setRecycler()
    }

    private fun setRecycler() {
        val list = ArrayList<CardModel>()
        list.add(CardModel(1, "أقسام"))
        list.add(CardModel(2, "أصناف"))
        list.add(CardModel(3, "إضافات"))


        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_and_update
    }

    override fun setOnHomeItemClick(model: CardModel) {


        when (model.id) {

            1 -> {
                startActivity(Intent(this, AddCategoryActivity::class.java))

            }
            2 -> {
                startActivity(Intent(this, AddItemActivity::class.java))

            }
            3 -> {
                startActivity(Intent(this, AddExtraActivity::class.java))

            }
            else->{

            }
        }


    }
}