package edu.rosehulman.attendancecheckoff

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.id_fragment.view.*

class IDFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.id_fragment, container, false).apply {
            image_qr_code.setImageBitmap(generateQRCode())
        }
    }

    private fun generateQRCode(): Bitmap? {
        val matrix = MultiFormatWriter().encode(CurrentState.user.username, BarcodeFormat.QR_CODE, 600, 600)
        return BarcodeEncoder().createBitmap(matrix)
    }
}
