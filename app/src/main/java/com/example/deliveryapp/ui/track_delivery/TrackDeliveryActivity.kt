package com.example.deliveryapp.ui.track_delivery

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.deliveryapp.R
import com.example.deliveryapp.data.local.entities.Delivery
import com.example.deliveryapp.databinding.ActivityTrackDeliveryBinding
import com.example.deliveryapp.di.Injectable
import com.example.deliveryapp.utils.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class TrackDeliveryActivity : AppCompatActivity(),Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var trackDeliveryViewModel: TrackDeliveryViewModel

    lateinit var mDelivery: Delivery
    lateinit var binding:ActivityTrackDeliveryBinding

    private lateinit var timelineAdapter:DeliveryTimelineAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_track_delivery)

        binding.toolbar.title = getString(R.string.track_delivery)
        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        trackDeliveryViewModel = ViewModelProviders.of(this,viewModelFactory).get(TrackDeliveryViewModel::class.java)

        mDelivery = intent.getSerializableExtra(DELIVERY_EXTRA) as Delivery

        binding.delivery = mDelivery

        setUpDeliveryTimeline()

    }

    private fun setUpDeliveryTimeline(){

        timelineAdapter = DeliveryTimelineAdapter(mDelivery)
        layoutManager = LinearLayoutManager(this)

        binding.deliveryTimelineRecycler.adapter = timelineAdapter
        binding.deliveryTimelineRecycler.layoutManager = layoutManager

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.track_delivery_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.menu.track_delivery_menu -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.cancel_delivery))
                    .setMessage(getString(R.string.confirm_cancel_delivery))
                    .setNegativeButton(getString(R.string.yes)){ dialog, _->
                        trackDeliveryViewModel.cancelDelivery(mDelivery)
                        dialog.dismiss()
                    }.setPositiveButton(getString(R.string.no)){ dialog, _->
                        dialog.dismiss()
                    }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val DELIVERY_EXTRA = "delivery"

        fun newInstance(context: Context,delivery: Delivery): Intent {
            val intent = Intent(context,TrackDeliveryActivity::class.java)
            intent.putExtra(DELIVERY_EXTRA,delivery)
            return intent
        }
    }

}