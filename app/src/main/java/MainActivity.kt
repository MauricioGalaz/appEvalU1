import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.mgalaz.appevalu1.R

class MainActivity : AppCompatActivity() {

    private lateinit var editTextPastel: EditText
    private lateinit var editTextCazuela: EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchPropina: Switch
    private lateinit var textViewSubtotal: TextView
    private lateinit var textViewPropina: TextView
    private lateinit var textViewTotal: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPastel = findViewById(R.id.editTextPastel)
        editTextCazuela = findViewById(R.id.editTextCazuela)
        switchPropina = findViewById(R.id.switchPropina)
        textViewSubtotal = findViewById(R.id.textViewSubtotal)
        textViewPropina = findViewById(R.id.textViewPropina)
        textViewTotal = findViewById(R.id.textViewTotal)

        editTextPastel.addTextChangedListener(textWatcher)
        editTextCazuela.addTextChangedListener(textWatcher)
        switchPropina.setOnCheckedChangeListener { _, _ -> actualizarTotales() }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            actualizarTotales()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun actualizarTotales() {
        val cantidadPastel = editTextPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = editTextCazuela.text.toString().toIntOrNull() ?: 0
        val subtotalPastel = cantidadPastel * PRECIO_PASTEL
        val subtotalCazuela = cantidadCazuela * PRECIO_CAZUELA
        val subtotal = subtotalPastel + subtotalCazuela
        val propina = if (switchPropina.isChecked) subtotal * PROPINA_PORCENTAJE else 0.0
        val total = propina + subtotal

        textViewSubtotal.text = getString(R.string.label_subtotal, subtotal)
        textViewPropina.text = getString(R.string.label_propina, propina)
        textViewTotal.text = getString(R.string.label_total, total)
    }

    companion object {
        private const val PRECIO_PASTEL = 12000
        private const val PRECIO_CAZUELA = 10000
        private const val PROPINA_PORCENTAJE = 0.1
    }

    override fun onDestroy() {
        super.onDestroy()
        editTextPastel.removeTextChangedListener(textWatcher)
        editTextCazuela.removeTextChangedListener(textWatcher)
    }
}
