package dk.analog.digitalclipcard.tickets

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dk.analog.digitalclipcard.R

class TicketsFragment : Fragment() {
    private var productViewModel: ProductViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        setupView()
    }

    private fun setupView() {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_tickets, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
