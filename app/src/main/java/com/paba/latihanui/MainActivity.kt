package com.paba.latihanui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val _OperationSatu = findViewById<EditText>(R.id.operationSatu)
        val _OperationDua = findViewById<EditText>(R.id.operationDua)
        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMulti: Button = findViewById(R.id.btnMulti)
        val btnDivide: Button = findViewById(R.id.btnDivide)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}