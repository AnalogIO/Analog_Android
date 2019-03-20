package dk.analog.digitalclipcard.purchase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.backend.ApiErrorResponse
import dk.analog.digitalclipcard.backend.ApiSuccessResponse
import dk.analog.digitalclipcard.base.BaseActivity
import dk.analog.digitalclipcard.utils.showToast
import kotlinx.android.synthetic.main.activity_purchase_history.*
import kotlinx.android.synthetic.main.purchase_receipt.view.*

class PurchaseHistoryActivity : BaseActivity() {
    private var purchaseViewModel: PurchaseHistoryViewModel? = null
    private val adapter = PurchaseAdapter(this)
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_purchase_history
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        purchasesRecyclerView.layoutManager = LinearLayoutManager(this)
        purchasesRecyclerView.adapter = adapter
        purchasesRecyclerView.setHasFixedSize(true)

        purchaseViewModel = ViewModelProviders.of(this).get(PurchaseHistoryViewModel::class.java)
        observeViewModel(purchaseViewModel!!)
    }

    private fun observeViewModel(purchaseViewModel: PurchaseHistoryViewModel) {
        purchaseViewModel.getPurchaseHistory {
            when (it) {
                is ApiSuccessResponse -> {
                    adapter.purchases = it.body
                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                }
            }
        }

    }

    class PurchaseAdapter(val context: Context) :
            RecyclerView.Adapter<PurchaseHistoryActivity.PurchaseAdapter.MyViewHolder>() {
        var purchases: List<PurchaseResponse> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(receipt: PurchaseResponse) {
                with (view) {
                    purchaseName.text = receipt.productName
                    purchasePrice.text = context.getString(R.string.DKK, receipt.price)
                    purchaseDate.text = receipt.dateCreated.toString()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): PurchaseHistoryActivity.PurchaseAdapter.MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.purchase_receipt, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(purchases[position])
        }

        override fun getItemCount() = purchases.size
    }

}