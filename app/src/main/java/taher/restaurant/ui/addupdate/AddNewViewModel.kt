package taher.restaurant

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.Data
import com.devartlab.data.shared.DataManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import taher.restaurant.base.BaseApplication
import taher.restaurant.data.retrofit.RetrofitClient
import taher.restaurant.data.room.DatabaseClient
import taher.restaurant.data.room.categories.CategoriesDao
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.extras.ExtrasEntity
import taher.restaurant.data.room.items.ItemsDao
import taher.restaurant.data.room.items.ItemsEntity

class AddNewViewModel(application: Application) : AndroidViewModel(application) {

    // axasasasas


    val categories: MutableLiveData<ArrayList<CategoriesEntity>>
    val items: MutableLiveData<ArrayList<ItemsEntity>>
    val extras: MutableLiveData<ArrayList<ExtrasEntity>>
    val progress: MutableLiveData<Int>
    var itemsDao: ItemsDao
    var categoriesDao: CategoriesDao


    var myAPI: ApiServices
    var dataManager: DataManager
    var retrofit: Retrofit


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)



        progress = MutableLiveData()
        categories = MutableLiveData()
        items = MutableLiveData()
        extras = MutableLiveData()
        itemsDao = DatabaseClient.getInstance(application)?.appDatabase?.itemsDao()!!
        categoriesDao = DatabaseClient.getInstance(application)?.appDatabase?.categoriesDao()!!


    }


    fun getData() {

        progress.postValue(1)

        val call = myAPI.syncItems()
        call.enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                progress.postValue(0)
                categories.postValue(response.body()?.categories)
                items.postValue(response.body()?.items)
                extras.postValue(response.body()?.extras)

            }
        })
    }

    fun saveCategory(name: String) {

        progress.postValue(1)
        val parms: MutableMap<String, String> = HashMap()
        parms["name"] = name
        val call = myAPI.saveCategory(parms)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progress.postValue(10)


            }
        })
    }

    fun saveItem(name: String, price: String, price_original: String, price_base: String, note: String, category_id: String) {

        progress.postValue(1)
        val parms: MutableMap<String, String> = HashMap()
        parms["name"] = name
        parms["price"] = price
        parms["price_original"] = price_original
        parms["price_base"] = price_base
        parms["note"] = note
        parms["category_id"] = category_id
        parms["type"] = "test"
        val call = myAPI.saveItem(parms)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progress.postValue(10)


            }
        })
    }

    fun saveExtra(name: String, price: String) {

        progress.postValue(1)
        val parms: MutableMap<String, String> = HashMap()
        parms["name"] = name
        parms["price"] = price
        val call = myAPI.saveExtra(parms)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progress.postValue(10)


            }
        })
    }


}