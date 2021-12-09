package taher.restaurant.data.room.items

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ItemsEntity(@PrimaryKey(autoGenerate = true) @SerializedName("databaseId") @ColumnInfo(name = "id") var id: Int? = null,

                       @SerializedName("id") @ColumnInfo(name = "itemID") var itemID: Int? = null,

                       @SerializedName("category_id") @ColumnInfo(name = "categoryId") var categoryId: Int? = null,

                       @SerializedName("name") @ColumnInfo(name = "name") var name: String? = null,

                       @SerializedName("price") @ColumnInfo(name = "price") var price: Double? = null,

                       @SerializedName("price_original") @ColumnInfo(name = "priceOriginal") var priceOriginal: Double? = null,

                       @SerializedName("price_base") @ColumnInfo(name = "priceBase") var priceBase: Double? = null,

                       @SerializedName("note") @ColumnInfo(name = "note") var note: String? = null,

                       @SerializedName("type") @ColumnInfo(name = "type") var type: String? = null,

                       @ColumnInfo(name = "amount") var amount: Int? = null,

                       @ColumnInfo(name = "notes") var notes: String? = null

) : Parcelable {

    constructor(itemID: Int?,
                categoryId: Int?,
                name: String?,
                price: Double?,
                priceOriginal: Double?,
                priceBase: Double?,
                note: String?,
                type: String?,
                amount: Int?,
                notes: String?) : this() {
        this.itemID = itemID
        this.categoryId = categoryId
        this.name = name
        this.price = price
        this.priceOriginal = priceOriginal
        this.priceBase = priceBase
        this.note = note
        this.type = type
        this.amount = amount
        this.notes = notes
    }
}
