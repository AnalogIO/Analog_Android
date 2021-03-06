package dk.analog.digitalclipcard.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.ticketHistory.TicketHistoryFragment
import dk.analog.digitalclipcard.tickets.TicketsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_drawer.*

class MainActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initial fragment
        setFragment(TicketsFragment::class.java)

        setupToolbar()
    }

    private fun setFragment(fragment: Class<out Fragment>) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFrame, fragment.newInstance())
                .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_tickets -> setFragment(TicketsFragment::class.java)
                R.id.nav_used_tickets -> setFragment(TicketHistoryFragment::class.java)
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }
        val drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.menuItemTicketHistory -> {
                setFragment(TicketHistoryFragment::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
