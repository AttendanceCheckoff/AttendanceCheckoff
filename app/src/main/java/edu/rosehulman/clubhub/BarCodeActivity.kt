package edu.rosehulman.clubhub

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.camerakit.CameraKitView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.FORMAT_ALL_FORMATS
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import edu.rosehulman.clubhub.util.Constants
import kotlinx.android.synthetic.main.bar_code_activity.*

class BarCodeActivity : AppCompatActivity() {

    companion object {
        const val KEY_DETECTED_VALUE = "Bar code value"
    }

    val detector by lazy {
        FirebaseVision.getInstance().getVisionBarcodeDetector(
            FirebaseVisionBarcodeDetectorOptions.Builder().setBarcodeFormats(FORMAT_ALL_FORMATS).build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar_code_activity)

        camera.gestureListener = object : CameraKitView.GestureListener {
            override fun onTap(cameraKitView: CameraKitView, v: Float, v1: Float) {
                cameraKitView.captureImage { _, bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    detector.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
                        .addOnSuccessListener { list ->
                            list.forEach { barcode ->
                                Log.d(Constants.TAG, barcode.displayValue)
                                Intent().also {
                                    it.putExtra(KEY_DETECTED_VALUE, barcode.displayValue)
                                    setResult(Activity.RESULT_OK, it)
                                    finish()
                                }
                            }
                        }
                }
            }
            override fun onLongTap(cameraKitView: CameraKitView, v: Float, v1: Float) {
            }
            override fun onDoubleTap(cameraKitView: CameraKitView, v: Float, v1: Float) {
            }
            override fun onPinch(cameraKitView: CameraKitView, v: Float, v1: Float, v2: Float) {
            }
        }
    }

    override fun onStart() {
        super.onStart()
        camera.onStart()
    }

    override fun onResume() {
        super.onResume()
        camera.onResume()
    }

    override fun onPause() {
        camera.onPause()
        super.onPause()
    }

    override fun onStop() {
        camera.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
