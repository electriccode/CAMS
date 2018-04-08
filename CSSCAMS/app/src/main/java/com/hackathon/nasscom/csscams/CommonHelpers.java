package com.hackathon.nasscom.csscams;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tarun on 07/04/18.
 */

public class CommonHelpers {



    public static void makeShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void makeLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG);
    }

}
