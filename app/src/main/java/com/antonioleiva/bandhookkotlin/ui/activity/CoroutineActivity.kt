package com.antonioleiva.bandhookkotlin.ui.activity

import android.support.v7.app.AppCompatActivity
import com.github.finecinnamon.JobHolder
import kotlinx.coroutines.experimental.Job

/**
 * Base class for all activities that use coroutine trees for handling their lifecycle and tasks
 */
abstract class CoroutineActivity : AppCompatActivity(), JobHolder {
    override var job: Job? = null

    override fun onPause() {
        super.onPause()

        job?.cancel()
    }

    override fun onResume() {
        super.onResume()

        job = Job()
    }
}
