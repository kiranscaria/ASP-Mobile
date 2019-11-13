/**
 *     PDFReaderFragment fragment displays a given pdf in the screen. Its source is
 *     pdf-file selected by the user.
 *
 */

package com.example.aspmobile.fragments

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tonyodev.fetch2.*
import kotlinx.android.synthetic.main.fragment_pdfreader.*
import java.io.File
import java.lang.Exception
import com.tonyodev.fetch2core.Func
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock
import com.example.aspmobile.R.layout.fragment_pdfreader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val LOG_TAG = "pdfreader"

class PDFReaderFragment : Fragment() {
    private var pdfSource: String = "http://www.cs.cornell.edu/courses/cs5740/2016sp/resources/backprop.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        displayPdf("/storage/emulated/0/Download/1603.03417v1.pdf")
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
        pdfView.fromAsset("backprop_old.pdf").load()
//        val file = File(filePath)
//
//        if (file.exists()) {
//            Log.d(LOG_TAG, "$file does exist. file length: ${file.length()}")
//            pdfView.fromFile(file).load()
//        } else {
//            Log.d(LOG_TAG, "$file does not exist.")
//            // TODO: Manage the 'absence of the file'. Implement some way to inform the user and tackle the problem in the UI too :)
//        }
    }
}

fun fetchFile(url: String, context: Context): String{
    val fetch: Fetch
    val fetchConfiguration = FetchConfiguration.Builder(context).setDownloadConcurrentLimit(1).enableLogging(true).build()
    fetch = Fetch.Impl.getInstance(fetchConfiguration)

    val fileName = "pdfTemp_${System.currentTimeMillis()}.pdf"
    val filePath = "${context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/${fileName}"

    val request = Request(url, filePath)
    request.priority = Priority.HIGH
    request.networkType = NetworkType.ALL
    request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")

    fetch.enqueue(request, Func {
        // Request was successfully enqueued for download
        // TODO
        Log.d(LOG_TAG, "Downloaded! fileUri: ${it.fileUri} | fileLocation: ${it.file} | fileId: ${it.id} | fileUrl: ${it.url}")
    }, Func {
        // An error occurred enqueuing the request.
        // TODO
        Log.d(LOG_TAG, "Could not download the file")
    })

    val fetchListener = object: FetchListener {
        override fun onAdded(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCancelled(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCompleted(download: Download) {
            print("Download Completed!!!")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDeleted(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDownloadBlockUpdated(
            download: Download,
            downloadBlock: DownloadBlock,
            totalBlocks: Int
        ) {
            TODO("not implemented")
        }

        override fun onError(download: Download, error: Error, throwable: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPaused(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProgress(
            download: Download,
            etaInMilliSeconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            Log.d(LOG_TAG, "Download Progressing: download:$download | ETA:$etaInMilliSeconds | bytesRemaining:$downloadedBytesPerSecond")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRemoved(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onResumed(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStarted(
            download: Download,
            downloadBlocks: List<DownloadBlock>,
            totalBlocks: Int
        ) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onWaitingNetwork(download: Download) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
    fetch.addListener(fetchListener)
    //Remove listener when done.
    fetch.removeListener(fetchListener)

    fetch.close()

    return request.file
}
