package com.paba.latihanui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [fragmentEquation.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentEquation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    interface OnClearClickListener {
        fun onClearClicked()
    }
    private var listener: OnClearClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Pastikan Activity mengimplementasikan listener
        if (context is OnClearClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnClearClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_equation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvEquation: TextView = view.findViewById(R.id.tvEquation)
        val btnClear: Button = view.findViewById(R.id.btnClear)

        // Ambil data mentah dari arguments
        val num1 = arguments?.getDouble("NUM1", 0.0) ?: 0.0
        val num2 = arguments?.getDouble("NUM2", 0.0) ?: 0.0
        val operator = arguments?.getString("OPERATOR") ?: "+"

        // Logika untuk membuat string persamaan ada di dalam fragment ini
        val operatorSymbol = when (operator) {
            "X" -> "x"
            else -> operator
        }
        val equation = "$num1 $operatorSymbol $num2"
        tvEquation.text = equation

        // Kirim event klik ke MainActivity untuk dieksekusi
        btnClear.setOnClickListener {
            listener?.onClearClicked()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmentEquation.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(num1: Double, num2: Double, operator: String): fragmentEquation {
            val fragment = fragmentEquation()
            val args = Bundle()
            args.putDouble("NUM1", num1)
            args.putDouble("NUM2", num2)
            args.putString("OPERATOR", operator)
            fragment.arguments = args
            return fragment
        }
    }
}
