package com.example.ominext.storedeviceonline.helper;

import com.example.ominext.storedeviceonline.data.remote.RetrofitClient;
import com.example.ominext.storedeviceonline.data.remote.SOService;
import com.example.ominext.storedeviceonline.until.Server;

/**
 * Created by Ominext on 8/21/2017.
 */

public class ApiUtil {
    public static  final String BASE_URL="http://"+ Server.localhost;
    public static SOService getSOService(){
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
