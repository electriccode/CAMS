package com.hackathon.nasscom.csscams;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

/**
 * Created by tarun on 07/04/18.
 */

public class CommonHelpers {



    public static void makeShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Bitmap compress(File file, Context context) throws IOException {
        return new Compressor(context)
                .setQuality(10)
                .setMaxHeight(150)
                .setMaxWidth(75)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .compressToBitmap(file);
    }

    public static void hidekeyboard(Activity activity){

            View v = activity.getWindow().getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

    }

}
