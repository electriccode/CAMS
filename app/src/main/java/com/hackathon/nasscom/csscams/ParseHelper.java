package com.hackathon.nasscom.csscams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hackathon.nasscom.csscams.models.Incident;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by tarun on 07/04/18.
 */

public class ParseHelper {


    private static final String TAG = "ParseHelper";


    public static boolean uploadIncident(final Context context, final Incident incident,
                                         final SOSActivity sosActivity){

        SaveCallback mSaveCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d(TAG, "Uploaded Successfully");
                    CommonHelpers.makeShortToast(context, "Reported Successfully");

                    Log.d(TAG, "Emitting to socket.io");
                    Gson gson = new Gson();
                    Log.d(TAG,gson.toJson(incident));
                    sosActivity.mSocket.emit("new incident", gson.toJson(incident));

                }else {
                    Log.d(TAG,"Upload Failed" + e.getMessage());
                }

                sosActivity.uploadScreen.setVisibility(View.GONE);
                sosActivity.refreshScreen();
            }
        };

        final ParseObject parseObject = new ParseObject("Incident");

        boolean uploadPic = true;

        //Bitmap original = BitmapFactory.decodeFile(incident.getPhoto().getPath());

        Bitmap compressed = null;

        try {
             compressed = CommonHelpers.compress(new File(incident.getPhoto().getPath()), context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(uploadPic && compressed != null) {


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            compressed.compress(Bitmap.CompressFormat.PNG, 100, out);

            byte[] fileBytes = out.toByteArray();

            Log.e("Original   dimensions", compressed.getWidth()+" "+compressed.getHeight());
            Log.d(TAG,"Original File size is :" + compressed.getByteCount());

            String fileName = "Image-"+UUID.randomUUID().toString()+".png";
            final ParseFile parseFile = new ParseFile(fileName,fileBytes);

            parseObject.put("photo",parseFile);

        }

        parseObject.put("id",incident.getId());
        parseObject.put("user", ParseUser.getCurrentUser());
        parseObject.put("userName",incident.getUserName());
        parseObject.put("category",incident.getCategoryId());
        parseObject.put("description", incident.getDescription());

        if(incident.getLocation() != null){

            ParseGeoPoint geoPoint = new ParseGeoPoint();

            double rand  = 0.004 + (0.014 - 0.004)*Math.random();

            geoPoint.setLatitude(incident.getLocation().getLatitude() + (Math.random() > 0.5?1:-1)*rand);
            geoPoint.setLongitude(incident.getLocation().getLongitude()+ (Math.random() > 0.5?1:-1)*rand);

            parseObject.put("location",geoPoint);
        }


        parseObject.put("status","Open");
        parseObject.saveInBackground(mSaveCallback);

        return true;
    }


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


