/**
 *     PDFReaderFragment fragment displays a given pdf in the screen. Its source is
 *     pdf-file selected by the user.
 *
 */

package com.example.aspmobile.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.aspmobile.R
import kotlinx.android.synthetic.main.fragment_pdfreader.*
import java.io.File
import java.lang.Exception


private const val LOG_TAG = "pdfreader"


class PDFReaderFragment : Fragment() {
    private var pdfSource: String = "backprop_old.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadPdf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdfreader, container, false)
    }

    private fun loadPdf() {
        val uri = Uri.parse(pdfSource)

        // Downloads and saves the file
        try {
            // TODO: Step 1: Download the file
            // TODO: Step 2: Check for space availability
            // TODO: Step 3: Save it in the internal storage
        } catch (e: Exception) {
            // TODO: Better exception handling
            Log.d(LOG_TAG, "EXCEPTION OCCURED!! : ${e.cause}")
            print(e)
        }

        val file = File("")

        if (file.exists()) {
            Log.d(LOG_TAG, "$file does exist.")
            pdfView.fromFile(file)
        } else {
            Log.d(LOG_TAG, "$file does not exist.")
            // TODO: Manage the 'absence of the file'. Implement some way to inform the user and tackle the problem in the UI too :)
        }

    }
}
