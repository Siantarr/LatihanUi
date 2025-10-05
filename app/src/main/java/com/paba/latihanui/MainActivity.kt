package com.paba.latihanui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), fragmentEquation.OnClearClickListener, fragmentResult.OnCalculationErrorListener {
    private lateinit var _OperationSatu: EditText
    private lateinit var _OperationDua: EditText
    private lateinit var _tvNotification: TextView

    private val equationFragmentTag = "EQUATION_FRAGMENT"
    private val hasilFragmentTag = "HASIL_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        _OperationSatu = findViewById<EditText>(R.id.operationSatu)
        _OperationDua = findViewById<EditText>(R.id.operationDua)
        _tvNotification = findViewById<TextView>(R.id.tvNotification)

        val _btnPlus: Button = findViewById(R.id.btnPlus)
        val _btnMinus: Button = findViewById(R.id.btnMinus)
        val _btnMulti: Button = findViewById(R.id.btnMulti)
        val _btnDivide: Button = findViewById(R.id.btnDivide)

        _btnPlus.setOnClickListener { handleOperationClick("+") }
        _btnMinus.setOnClickListener { handleOperationClick("-") }
        _btnMulti.setOnClickListener { handleOperationClick("X") }
        _btnDivide.setOnClickListener { handleOperationClick("/") }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleOperationClick(operator: String) {
        // Selalu sembunyikan notifikasi error setiap kali tombol operasi diklik
        _tvNotification.visibility = View.GONE

        val num1Str = _OperationSatu.text.toString()
        val num2Str = _OperationDua.text.toString()

        // Validasi input
        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            showError("Harap isi kedua angka")
            return
        }

        val num1 = num1Str.toDouble()
        val num2 = num2Str.toDouble()

        // Kirim data ke fragment
        showEquationFragment(num1, num2, operator)
        showResultFragment(num1, num2, operator)
    }

    private fun showEquationFragment(num1: Double, num2: Double, operator: String) {
        val equationFragment = fragmentEquation.newInstance(num1, num2, operator)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer1, equationFragment, equationFragmentTag)
            .commit()
    }

    private fun showResultFragment(num1: Double, num2: Double, operator: String) {
        val hasilFragment = fragmentResult.newInstance(num1, num2, operator)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer2, hasilFragment, hasilFragmentTag)
            .commit()
    }

    override fun onClearClicked() {
        _OperationSatu.text.clear()
        _OperationDua.text.clear()
        _tvNotification.visibility = View.GONE // Sembunyikan juga saat clear

        // Cari dan hapus fragment yang ada
        val equationFragment = supportFragmentManager.findFragmentByTag(equationFragmentTag)
        val hasilFragment = supportFragmentManager.findFragmentByTag(hasilFragmentTag)

        val transaction = supportFragmentManager.beginTransaction()
        if (equationFragment != null) transaction.remove(equationFragment)
        if (hasilFragment != null) transaction.remove(hasilFragment)
        transaction.commit()
    }

    override fun onCalculationError(message: String) {
        showError(message)
    }
    private fun showError(message: String) {
        _tvNotification.text = message
        _tvNotification.visibility = View.VISIBLE
    }


}