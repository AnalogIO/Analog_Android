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
    private val adapter = MyAdapter(this)
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_purchase_history
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setupListeners()
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
                    adapter.myDataset = it.body


                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                }
            }
        }

    }

    class MyAdapter(val context: Context) :
            RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        var myDataset: List<PurchaseResponse> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(receipt: PurchaseResponse) {
                view.purchaseName.text = receipt.productName
                view.purchasePrice.text = context.getString(R.string.DKK, receipt.price)
                view.purchaseDate.text = receipt.dateCreated.toString()

            }
        }


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyAdapter.MyViewHolder {
            // create a new view
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.purchase_receipt, parent, false)
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.bind(myDataset[position])
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }

}