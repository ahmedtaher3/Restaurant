package taher.restaurant

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.Data
import com.devartlab.data.shared.DataManager
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
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
import taher.restaurant.data.room.extras.ExtrasDao
import taher.restaurant.data.room.items.ItemsDao
import taher.restaurant.data.room.items.ItemsEntity

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // axasasasas


    val categories: MutableLiveData<ArrayList<CategoriesEntity>>
    val items: MutableLiveData<ArrayList<ItemsEntity>>
    val progress: MutableLiveData<Int>
    var itemsDao: ItemsDao
    var categoriesDao: CategoriesDao
    var extrasDao: ExtrasDao


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
        itemsDao = DatabaseClient.getInstance(application)?.appDatabase?.itemsDao()!!
        categoriesDao = DatabaseClient.getInstance(application)?.appDatabase?.categoriesDao()!!
        extrasDao = DatabaseClient.getInstance(application)?.appDatabase?.extrasDao()!!


    }

    fun getData() {

        Completable.fromAction {

            items.postValue(itemsDao.all as ArrayList<ItemsEntity>)
            categories.postValue(categoriesDao.all as ArrayList<CategoriesEntity>)

        }.subscribeOn(Schedulers.io()).subscribe()
    }

    fun syncData() {

        progress.postValue(1)

        val call = myAPI.syncItems()
        call.enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                progress.postValue(0)

                Completable.fromAction {
                    itemsDao.deleteTable()
                    itemsDao.insertAll(response.body()?.items!!)
                    categoriesDao.deleteTable()
                    categoriesDao.insertAll(response.body()?.categories!!)
                    extrasDao.deleteTable()
                    extrasDao.insertAll(response.body()?.extras!!)
                    getData()

                }.subscribeOn(Schedulers.io()).subscribe()


            }
        })
    }


    fun getCategory() {

        progress.postValue(1)

        val call = myAPI.getCategory()
        call.enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                progress.postValue(0)


            }
        })
    }

    fun saveOrder(order: JsonObject) {

        progress.postValue(1)
        val call = myAPI.saveOrder(order)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progress.postValue(0)


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
}