package cr.ac.una.serviciotest



import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class MyService : Service() {
    private var number = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                increaseNumber()
            }
        }, 0, 1000)
        return START_STICKY
    }

    private fun increaseNumber() {
        number++
        val intent = Intent("com.example.myapp.NUMBER_CHANGED")
        intent.putExtra("number", number)
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}