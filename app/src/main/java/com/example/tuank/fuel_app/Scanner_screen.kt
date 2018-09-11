package com.example.tuank.fuel_app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.ContextMenu
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Scanner_screen : AppCompatActivity() {
    lateinit var database: DatabaseReference
    private lateinit var svbarcode: SurfaceView
    private lateinit var tvbarcode: TextView

    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSourse: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_screen)

        database = FirebaseDatabase.getInstance().getReference("QR_CODE")
        svbarcode = findViewById(R.id.barcode_view)
        tvbarcode = findViewById(R.id.tv_barcode)

        detector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        detector.setProcessor(object : Detector.Processor<Barcode>{
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if (barcodes!!.size()>0){
                    tvbarcode.post{
                        tvbarcode.text = barcodes.valueAt(0).displayValue
                        database.child("QR_CODE").setValue(barcodes.valueAt(0))
                    }
                }
            }

        })
        cameraSourse = CameraSource.Builder(this,detector).setRequestedPreviewSize(1024,768)
                .setRequestedFps(25f).setAutoFocusEnabled(true).build()
        svbarcode.holder.addCallback(object : SurfaceHolder.Callback2{
            override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
            }
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, w: Int, h: Int) {
            }
            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSourse.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if (ContextCompat.checkSelfPermission(this@Scanner_screen, Manifest.permission
                                .CAMERA) == PackageManager.PERMISSION_GRANTED)
                cameraSourse.start(holder)
                else ActivityCompat.requestPermissions(this@Scanner_screen,
                        arrayOf(Manifest.permission.CAMERA), 123)
            }
        })
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                cameraSourse.start(svbarcode.holder)
            else Toast.makeText(this, "Scanner won't work without permission", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.release()
        cameraSourse.stop()
        cameraSourse.release()
        startActivity(Intent(this, main1 ::class.java))
    }

}
