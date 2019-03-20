package dk.analog.digitalclipcard.ticketHistory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.backend.ApiErrorResponse
import dk.analog.digitalclipcard.backend.ApiSuccessResponse
import dk.analog.digitalclipcard.utils.showToast
import kotlinx.android.synthetic.main.fragment_ticket_history.*
import kotlinx.android.synthetic.main.ticket_receipt.view.*
import org.joda.time.DateTime

class TicketHistoryFragment : Fragment() {
    private var ticketViewModel: TicketHistoryViewModel? = null
    private lateinit var adapter: TicketAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ticket_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TicketAdapter(context!!)
        ticketHistoryRecyclerView.layoutManager = LinearLayoutManager(context)
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
                    context?.showToast(it.errorMessage)
                }
            }
        }

    }

    class TicketAdapter(val context: Context) :
            RecyclerView.Adapter<TicketHistoryFragment.TicketAdapter.MyViewHolder>() {
        var tickets: List<TicketResponse> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(receipt: TicketResponse) {
                with(view) {
                    val dt = DateTime(receipt.dateUsed.time)
                    usedTicketTime.text = dt.toString("HH:mm")
                    usedTicketName.text = receipt.productName
                    usedTicketDate.text = dt.toString("yyyy-MM-dd")
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): TicketHistoryFragment.TicketAdapter.MyViewHolder {
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