/**
 *     PDFReaderFragment fragment displays a given pdf in the screen. Its source is
 *     pdf-file selected by the user.
 *
 */

package com.example.aspmobile.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_pdfreader.*
import java.lang.Exception
import com.example.aspmobile.R.layout.fragment_pdfreader
import com.example.aspmobile.networking.fetchFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val LOG_TAG = "pdfreader"
private const val WRITE_REQUEST_CODE: Int = 55

class PDFReaderFragment : Fragment() {
    private var pdfSource: String = "http://www.cs.cornell.edu/courses/cs5740/2016sp/resources/backprop.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPermissionAndFetchFile()
//        fetchAndDisplayPdf()
//        displayPdf("/storage/emulated/0/Download/1603.03417v1.pdf")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(fragment_pdfreader, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        displayPdf("/storage/emulated/0/Download/1603.03417v1.pdf")
    }

    private fun getPermissionAndFetchFile() {
        Dexter.withActivity(activity).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(object :
            PermissionListener {
            override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest?, permissionToken: PermissionToken?) {
                val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
                alertBuilder.setMessage("We need this permission to display images!")
                alertBuilder.setTitle("We need this permission")
                alertBuilder.setCancelable(false)
                alertBuilder.setPositiveButton("OK") { dialog, _ ->
                    dialog!!.cancel()
                    permissionToken!!.continuePermissionRequest()
                }
                alertBuilder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog!!.cancel()
                    permissionToken!!.cancelPermissionRequest()
                }
                val alertDialog: AlertDialog = alertBuilder.create()
                alertDialog.show()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
                alertBuilder.setMessage("This permission is needed to display images, so please allow it!")
                alertBuilder.setTitle("We need this permission")
                alertBuilder.setCancelable(false)
                alertBuilder.setPositiveButton("OK") { dialog, _ ->
                    dialog.cancel()
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri: Uri = Uri.fromParts("package", activity!!.packageName, null)
                    intent.data = uri
                    activity!!.startActivity(intent)
                }
                alertBuilder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                val alertDialog: AlertDialog = alertBuilder.create()
                alertDialog.show()
            }

            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val pdfSavedLocation = fetchFile(pdfSource, context!!)
                displayPdf(pdfSavedLocation)
            }

        }).check()
    }

    private fun fetchAndDisplayPdf() {
        // Downloads and saves the file
        try {
            // TODO: Step 1: Download the file

            GlobalScope.launch {
                val filePath = fetchFile(pdfSource, context!!)
                Log.d(LOG_TAG, "Downloaded filePath: $filePath")
                displayPdf(filePath)
            }
            // TODO: Step 2: Check for space availability
            // TODO: Step 3: Save it in the internal storage

        } catch (e: Exception) {
            // TODO: Better exception handling
            Log.d(LOG_TAG, "EXCEPTION OCCURRED!! : ${e.cause}")
            print(e)
        }
    }

    private fun displayPdf(filePath: String) {
        pdfView.fromAsset("backprop_old.pdf").pageSnap(true).pageFling(true).swipeHorizontal(true).load()
//        val file = File(filePath)
//
//        if (file.exists()) {
//            Log.d(LOG_TAG, "$file does exist. file length: ${file.length()}")
//            pdfView.fromFile(file).load()
//        } else {
//            Log.d(LOG_TAG, "$file does not exist.")
//            // TODO: Manage the 'absence of the file'. Implement some way to inform the user and tackle the problem in the UI too :)
//        }
        print(filePath)
    }
}