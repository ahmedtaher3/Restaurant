package taher.restaurant.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import taher.restaurant.MainViewModel
import taher.restaurant.R
import taher.restaurant.base.BaseActivity
import taher.restaurant.data.room.items.ItemsEntity
import taher.restaurant.databinding.ActivityConfirmOrderBinding
import taher.restaurant.model.ItemsItem
import taher.restaurant.model.OrderUploadModel
import taher.restaurant.ui.printer.PrinterConnectActivity
import taher.restaurant.ui.printer.PrinterControl.BixolonPrinter

private const val TAG = "ConfirmOrderActivity"
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding>(), OrderPrintAdapter.OnRemoveItem {

    lateinit var binding: ActivityConfirmOrderBinding
    lateinit var myData: ArrayList<ItemsEntity>
    lateinit var viewModel: MainViewModel
    lateinit var adapter: OrderPrintAdapter
    val list = ArrayList<ItemsEntity>()
    var subtotal = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = viewDataBinding
        myData = intent.getParcelableArrayListExtra<ItemsEntity>("ORDER") as ArrayList<ItemsEntity>

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        adapter = OrderPrintAdapter(this, ArrayList(), this)

        for (m in myData) {
            System.out.println(m.toString())

            if (m.amount!! > 0) {
                list.add(m)
                subtotal = subtotal + (m.price!! * m.amount!!)
            }
        }

        binding.subtotal.text = subtotal.toString()
        binding.tax.text = "0"
        binding.total.text = (subtotal + binding.tax.text.toString().toInt()).toString()


        binding.recyclerView.adapter = adapter
        adapter.setMyData(myData)


        binding.order.setOnClickListener {

            val orderItems = ArrayList<ItemsItem>()
            for (m in myData) {
                orderItems.add(ItemsItem(m.amount.toString(), m.notes + ".", m.itemID))
            }

            val order = OrderUploadModel(".", total = subtotal, price = subtotal, service = 0.0, tax = 0.0, orderItems)

            val buildsSchema = Gson().toJsonTree(order).asJsonObject

            Log.d(TAG, "onCreate: $buildsSchema")

            viewModel.saveOrder(buildsSchema)
        }


    }

    fun print() {


        if (bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)!!) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.rounded_selected)
            bxlPrinter?.printImage(bitmap, 500, BixolonPrinter.ALIGNMENT_CENTER, 50, 0, 1)
            bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)


            bxlPrinter?.printText("Devart Lab\n", BixolonPrinter.ATTRIBUTE_BOLD, 0, 1)
            bxlPrinter?.printText("فاتورة بيع نقدي\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("اسم المندوب : احمد طاهر احمد\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("رقم المندوب : 01018388777\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("اسم العميل  : كارفور\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("رقم العميل : 01018388777 \n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("العنوان :  شبرا\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)

            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

            bxlPrinter?.printText("المنتجات\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 1)

            bxlPrinter?.printText("الاسم         السعر     الكمية   اجمالى" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

            bxlPrinter?.printText("الاسم         السعر     الكمية    اجمالى" + "\n" + "\n" + "ddddddddddddddddd" + "\n",
                                  BixolonPrinter.ALIGNMENT_RIGHT,
                                  0,
                                  1)


            for (m in myData) {

                if (m.amount!! > 0) {

                    bxlPrinter?.printText(m.name + "      " + m.price + "      " + m.amount + "      " + (m.amount!! * m.price!!).toString() + "\n",
                                          BixolonPrinter.ALIGNMENT_RIGHT,
                                          0,
                                          1)
                    subtotal = subtotal + (m.price!! * m.amount!!)


                }
            }


            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)
            bxlPrinter?.printText("الاجمالى = ", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)
            bxlPrinter?.printText(binding.total.text.toString() + " L.E" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

            bxlPrinter?.printText("Total = ", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)
            bxlPrinter?.printText(binding.total.text.toString() + " L.E" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)


            bxlPrinter?.printText("توقيع العميل: __________ " + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)

            bxlPrinter?.printText("توقيع البائع: __________ " + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)



            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)

            val data = ("Thank you for your order!\n" + "www.devartlab.com\n" + "TOGETHER FOR WORTHY LIFE.\n\n\n\n")
            bxlPrinter?.printText(data, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.ATTRIBUTE_BOLD, 1)
        }
        else {
            startConnectionActivity()
        }


    }

    fun startConnectionActivity() {
        val intent = Intent(this, PrinterConnectActivity::class.java)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
    }

    override fun setOnRemoveItem(model: ItemsEntity) {
        list.clear()
        subtotal = 0.0
        myData.remove(model)
        for (m in myData) {
            System.out.println(m.toString())

            if (m.amount!! > 0) {
                list.add(m)
                subtotal = subtotal + (m.price!! * m.amount!!)
            }
        }

        binding.subtotal.text = subtotal.toString()
        binding.tax.text = "0"
        binding.total.text = (subtotal + binding.tax.text.toString().toInt()).toString()

        adapter.setMyData(list)
    }

    init {
        bxlPrinter = BixolonPrinter(this)
    }


    fun getPrinterInstance(): BixolonPrinter? {
        return bxlPrinter
    }

    companion object {
        public var bxlPrinter: BixolonPrinter? = null


        fun showMsg(text: String) {
        }

    }
}