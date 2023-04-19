package cr.ac.una.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import cr.ac.una.roomdb.adapter.UbicacionAdapter
import cr.ac.una.roomdb.db.AppDatabase
import cr.ac.una.roomdb.entity.Ubicacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var ubicacionDao: UbicacionDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ubicacionDao = AppDatabase.getInstance(this).ubicacionDao()

        val buttonInsert = findViewById<Button>(R.id.buttonInsert)
        buttonInsert.setOnClickListener {
            val entity = Ubicacion(
                id = null,
                latitud = 1.0,
                longitud = 2.0,
                fecha = Date()
            )
            insertEntity(entity)
        }
        val listView = findViewById<ListView>(R.id.listUbicaciones)
        var ubicaciones: List<Ubicacion>


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ubicaciones = ubicacionDao.getAll() as List<Ubicacion>
                val adapter = UbicacionAdapter(this@MainActivity, ubicaciones)
                listView.adapter = adapter
            }

        }





    }
    private fun insertEntity(entity: Ubicacion) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ubicacionDao.insert(entity)
            }
        }

    }
}