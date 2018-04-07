//package com.hackathon.nasscom.csscams;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import static android.os.Build.VERSION_CODES.M;
//
//public class SplashScreen extends AppCompatActivity {
//
//    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
//    private static final int PERMISSIONS_MULTIPLE_REQUEST_WHILE_LOGIN = 124;
//    private static boolean isFromSetting = false;
//    private boolean allPermissionGranted = false;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (isFromSetting) {
//            checkPermission();
//            isFromSetting = false;
//        }
//    }
//
//
//    private String[] runtimePermissionArray = new String[]{
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_SMS,
//            Manifest.permission.RECEIVE_SMS,
//            Manifest.permission.READ_CONTACTS,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.GET_ACCOUNTS
//    };
//
//    private void checkPermission() {
//        if (ContextCompat.
//                checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.
//                    shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)) {
//                showPermissionSnackBar2(PERMISSIONS_MULTIPLE_REQUEST);
//            } else {
//                if (Build.VERSION.SDK_INT >= M) {
//                    requestPermissions(runtimePermissionArray, PERMISSIONS_MULTIPLE_REQUEST);
//                }
//            }
//        } else {
//            //  if permission alreastartLogin();
//            allPermissionGranted = true;
//        }
//    }
//
//    @TargetApi(M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERMISSIONS_MULTIPLE_REQUEST || requestCode == PERMISSIONS_MULTIPLE_REQUEST_WHILE_LOGIN) {
//            if (grantResults.length > 0) {
//                int resultPermissionSum = 0;
//                for (int i = 0; i < grantResults.length - 1; i++) {
//                    resultPermissionSum = resultPermissionSum + grantResults[i];
//                }
//
//                if (resultPermissionSum == PackageManager.PERMISSION_GRANTED) {
//                    allPermissionGranted = true;
//
//                    if (requestCode == PERMISSIONS_MULTIPLE_REQUEST_WHILE_LOGIN || requestCode == PERMISSIONS_MULTIPLE_REQUEST) {
//                        loginSubmit.performClick();
//                    }
//
//                    return;
//                }
//                if (ActivityCompat.
//                        shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) ||
//                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)) {
//
//                    showSnackBarWithAction("Please Grant All Permissions to Run the application", "ENABLE",
//                            v -> requestPermissions(runtimePermissionArray, PERMISSIONS_MULTIPLE_REQUEST));
//
////
////                    Snackbar.make(this.findViewById(android.R.id.content),
////                            "Please Grant All Permissions to Run the application",
////                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
////                            v -> requestPermissions(
////                                    runtimePermissionArray,
////                                    PERMISSIONS_MULTIPLE_REQUEST)).show();
//                } else {
//
//                    showSnackBarWithAction("Need To Turn on permission in Settings", "Setting", v -> {
//                        isFromSetting = true;
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//                    });
//
////                    Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Need To Turn on permission in Settings", Snackbar.LENGTH_INDEFINITE);
////                    snackbar.setAction("Setting", v -> {
////                        isFromSetting = true;
////                        Intent intent = new Intent();
////                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                        Uri uri = Uri.fromParts("package", getPackageName(), null);
////                        intent.setData(uri);
////                        startActivity(intent);
////                    });
////                    snackbar.show();
//                }
//            }
//
//        }
//    }
//
//}
