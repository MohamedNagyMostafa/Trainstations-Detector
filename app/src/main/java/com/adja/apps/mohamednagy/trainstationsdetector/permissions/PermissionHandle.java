package com.adja.apps.mohamednagy.trainstationsdetector.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Mohamed Nagy on 2/1/2018.
 */

public class PermissionHandle {

    public static final String ACCESS_FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int REQUEST_CODE = 1;

    public static boolean checkPermission(String permission, Context context){
        return (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED);
    }

    public static void askPermission(Context context, String... permissions){
        ActivityCompat.requestPermissions((Activity) context, permissions, REQUEST_CODE);
    }

}
