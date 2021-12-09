package taher.restaurant.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import taher.restaurant.data.room.categories.CategoriesDao
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.extras.ExtrasDao
import taher.restaurant.data.room.extras.ExtrasEntity
import taher.restaurant.data.room.items.ItemsDao
import taher.restaurant.data.room.items.ItemsEntity


@Database(entities = [ItemsEntity::class, CategoriesEntity::class , ExtrasEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
    abstract fun extrasDao(): ExtrasDao
    abstract fun categoriesDao(): CategoriesDao

}

