package com.example.ominext.storedeviceonline.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Ominext on 8/17/2017.
 */
// Dùng để kiểm tra có kết nối internet hay không
public class CheckConnection {
    public static boolean haveNetWorkConnection(Context context){
//        có kết nối bằng wifi
        boolean haveConnectedWifi=false;
//        có kết nối bằng điện thoại (2G, 3G, 4G)
        boolean haveConnectedMobile=false;
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos=manager.getAllNetworkInfo();
        for (NetworkInfo info: infos){
            if(info.getTypeName().equalsIgnoreCase("WIFI"));
            if(info.isConnected()){
                haveConnectedWifi=true;
            }
            if(info.getTypeName().equalsIgnoreCase("MOBILE"));
            if(info.isConnected()){
                haveConnectedMobile=true;
            }
        }
        return (haveConnectedMobile||haveConnectedWifi);
    }
    public static void showToast(Context context, String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
