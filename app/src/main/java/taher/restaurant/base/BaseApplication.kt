package taher.restaurant.base;
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import com.devartlab.data.shared.DataManager

import com.mazenrashed.printooth.Printooth
import taher.restaurant.data.shared.SharedPrefsHelper


class BaseApplication : MultiDexApplication() {
    var dataManager: DataManager? = null
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
     }

    override fun onCreate() {
        super.onCreate()
        val sharedPrefsHelper = SharedPrefsHelper(applicationContext)
        dataManager = DataManager(sharedPrefsHelper)
        Printooth.init(this)
    }
}