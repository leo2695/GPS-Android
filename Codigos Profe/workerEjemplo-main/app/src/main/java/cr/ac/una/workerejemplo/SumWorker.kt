package cr.ac.una.workerejemplo

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters

class SumWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        var numero = inputData.getInt("numero", 0)
        for (i in 0 until 5) {
            // Sumar el número
            numero++
            Log.d("SumWorker", "Número actual: $numero")
            // Actualizar el progreso

            setProgress(Data.Builder().putInt("numero", numero).build())
            // Esperar un minuto
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return Result.success(Data.Builder().putInt("numero", numero).build())
    }
}
