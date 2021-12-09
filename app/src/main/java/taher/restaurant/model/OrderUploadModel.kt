package taher.restaurant.model

import com.google.gson.annotations.SerializedName

data class OrderUploadModel(

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("total")
	val total: Double? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("service")
	val service: Double? = null,

	@field:SerializedName("tax")
	val tax: Double? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
