package com.paba.latihanui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [fragmentResult.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentResult : Fragment() {

    interface OnCalculationErrorListener {
        fun onCalculationError(message: String)
    }

    private var listener: OnCalculationErrorListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Pastikan Activity mengimplementasikan listener
        if (context is OnCalculationErrorListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCalculationErrorListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvHasil: TextView = view.findViewById(R.id.tvResult)

        val num1 = arguments?.getDouble("NUM1", 0.0) ?: 0.0
        val num2 = arguments?.getDouble("NUM2", 0.0) ?: 0.0
        val operator = arguments?.getString("OPERATOR") ?: "+"
        val result: Double

        // Logika kalkulasi
        when (operator) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "X" -> result = num1 * num2
            "/" -> {
                if (num2 == 0.0) {
                    // Kirim pesan error ke MainActivity melalui interface
                    listener?.onCalculationError("Error: Tidak bisa dibagi dengan nol")
                    // Hapus fragment ini karena tidak ada hasil yang valid
                    parentFragmentManager.beginTransaction().remove(this).commit()
                    return
                }
                result = num1 / num2
            }
            else -> {
                listener?.onCalculationError("Error: Operasi tidak dikenal")
                parentFragmentManager.beginTransaction().remove(this).commit()
                return
            }
        }

        val df = DecimalFormat("#.##")
        tvHasil.text = "Hasil = ${df.format(result)}"
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        fun newInstance(num1: Double, num2: Double, operator: String): fragmentResult {
            val fragment = fragmentResult()
            val args = Bundle()
            args.putDouble("NUM1", num1)
            args.putDouble("NUM2", num2)
            args.putString("OPERATOR", operator)
            fragment.arguments = args
            return fragment
        }
    }
}
