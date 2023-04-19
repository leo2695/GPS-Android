package cr.ac.una.workerejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var workManager: WorkManager
    private lateinit var numeroTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workManager = WorkManager.getInstance(this)
        numeroTextView = findViewById(R.id.numeroTextView)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            // Programar el trabajo
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val workRequest = OneTimeWorkRequestBuilder<SumWorker>()
                .setInputData(Data.Builder().putInt("numero", 0).build())
                .setConstraints(constraints)
                .setInitialDelay(2, TimeUnit.SECONDS)
                .build()
            workManager.enqueue(workRequest)
            // Observar el progreso del trabajo
            workManager.getWorkInfoByIdLiveData(workRequest.id)
                .observe(this, Observer { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                        val progreso = workInfo.progress.getInt("numero", 0)
                        numeroTextView.text = progreso.toString()
                    } else if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val numero = workInfo.outputData.getInt("numero", 0)
                        numeroTextView.text = numero.toString()
                    }
                })
        }
    }
}
