package com.hackathon.nasscom.csscams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import bolts.Task;

/**
 * Created by tarun on 07/04/18.
 */

public class ParseHelper {


    private static final String TAG = "ParseHelper";



    public static String uploadFile(final Context context, File file){


        SaveCallback mSaveCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d(TAG, "Uploaded Successfully");
                }else{
                    Log.d(TAG,"Upload Failed" + e.getMessage());
                }
            }
        };

        Bitmap original = BitmapFactory.decodeFile(file.getPath());
        final ParseObject parseObject = new ParseObject("csscamsImageObject");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.PNG, 100, out);

        byte[] fileBytes = out.toByteArray();


        Log.e("Original   dimensions", original.getWidth()+" "+original.getHeight());
        Log.d(TAG,"Original File size is :" + original.getByteCount());

        String fileName = "Image-"+UUID.randomUUID().toString()+".png";
        final ParseFile parseFile = new ParseFile(fileName,fileBytes);

        parseObject.put("imageContent",parseFile);
        parseObject.saveInBackground(mSaveCallback);

//        parseObject.saveEventually(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//
//                if(e == null){
//                    Log.d(TAG, "Uploaded Successfully");
//                    parseObject.put("imageContent",parseFile);
//                    parseObject.saveInBackground();
//                }else{
//                    Log.d(TAG,"Upload Failed");
//                }
//
//            }
//        });

        Log.d(TAG,"File Name : " + fileName);

        return fileName;
    }


    private static byte[] readInFile(String path) throws IOException {
        // TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();

    }
}


