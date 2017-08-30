package nz.co.udenbrothers.clockwork.customWigets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import nz.co.udenbrothers.clockwork.R;


public class QRView extends AppCompatImageView{

    public QRView(Context context)
    {
        super(context);
    }

    public QRView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public QRView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void set(String content){
        QRCodeWriter writer = new QRCodeWriter();
        if(content.equals("")) return;

        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnPreDrawListener(() -> {
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();

            try {
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                setImageBitmap(bmp);
            } catch (WriterException e) {
                Log.e("QR_scan",e+"");
                setImageResource(R.drawable.error);
            }

            return true;
        });
    }
}
