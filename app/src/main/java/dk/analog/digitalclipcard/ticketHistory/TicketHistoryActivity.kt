package dk.analog.digitalclipcard.ticketHistory

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
import kotlinx.android.synthetic.main.activity_ticket_history.*
import kotlinx.android.synthetic.main.ticket_receipt.view.*
import org.joda.time.DateTime

class TicketHistoryActivity: BaseActivity() {
    private var ticketViewModel: TicketHistoryViewModel? = null
    private val adapter = TicketAdapter(this)
    override fun getLayoutResourceId(): Int = R.layout.activity_ticket_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ticketHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        ticketHistoryRecyclerView.adapter = adapter
        ticketHistoryRecyclerView.setHasFixedSize(true)

        ticketViewModel = ViewModelProviders.of(this).get(TicketHistoryViewModel::class.java)
        observeViewModel(ticketViewModel!!)

    }


    private fun observeViewModel(purchaseViewModel: TicketHistoryViewModel) {
        purchaseViewModel.getTicketHistory {
            when (it) {
                is ApiSuccessResponse -> {
                    adapter.tickets = it.body
                }
                is ApiErrorResponse -> {
                    showToast(it.errorMessage)
                }
            }
        }

    }

    class TicketAdapter(val context: Context) :
            RecyclerView.Adapter<TicketHistoryActivity.TicketAdapter.MyViewHolder>() {
        var tickets: List<TicketResponse> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(receipt: TicketResponse) {
                with (view) {
                    val dt = DateTime(receipt.dateUsed.time)
                    usedTicketTime.text = dt.toString("HH:mm")
                    usedTicketName.text = receipt.productName
                    usedTicketDate.text = dt.toString("yyyy-MM-dd")
                    //purchaseName.text = receipt.productName
                    //purchasePrice.text = context.getString(R.string.DKK, receipt.price)
                    //purchaseDate.text = receipt.dateCreated.toString()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): TicketHistoryActivity.TicketAdapter.MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.purchase_receipt, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(tickets[position])
        }

        override fun getItemCount() = tickets.size
    }
}