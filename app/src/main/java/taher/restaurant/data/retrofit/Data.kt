package com.devartlab.data.retrofit


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import taher.restaurant.data.room.categories.CategoriesEntity
import taher.restaurant.data.room.extras.ExtrasEntity
import taher.restaurant.data.room.items.ItemsEntity

data class Data(

    @SerializedName("categories") @Expose var categories: ArrayList<CategoriesEntity>,

    @SerializedName("items") @Expose var items: ArrayList<ItemsEntity>,

    @SerializedName("extras") @Expose var extras: ArrayList<ExtrasEntity>

)






