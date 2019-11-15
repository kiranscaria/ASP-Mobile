package com.example.aspmobile.networking

import android.content.Context
import android.os.Environment
import android.util.Log
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import com.tonyodev.fetch2core.Func


private const val LOG_TAG = "pdfreader"

fun fetchFile(url: String, context: Context): String{
    val fetch: Fetch
    val fetchConfiguration = FetchConfiguration.Builder(context).setDownloadConcurrentLimit(1).enableLogging(true).build()
    fetch = Fetch.Impl.getInstance(fetchConfiguration)

    val fileName = "pdfTemp_${System.currentTimeMillis()}.pdf"
    val filePath = "${context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath}/${fileName}"

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