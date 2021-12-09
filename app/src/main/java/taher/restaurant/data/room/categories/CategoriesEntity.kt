package taher.restaurant.data.room.categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CategoriesEntity(

    @PrimaryKey(autoGenerate = true) @SerializedName("databaseId") @ColumnInfo(name = "id") var id: Int? = null,
    @SerializedName("id") @ColumnInfo(name = "itemID") var itemID: Int? = null,
    @SerializedName("name") @ColumnInfo(name = "name") var name: String? = null,
    @SerializedName("selected") var selected: Boolean = false,
){
    constructor(itemID: Int?, name: String?, selected: Boolean):this() {
        this.itemID = itemID
        this.name = name
        this.selected = selected
    }
}
