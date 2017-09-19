package com.example.ominext.storedeviceonline.model;

import android.content.Context;
import android.os.Environment;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Ominext on 8/30/2017.
 */
//lưu lại các mặt hàng đã thêm vào giỏ hàng khi chưa thanh toán
public class Cache {
    public static int size=0;
//chuyển thành chuỗi json
    public static String writeJsonStream(Cart data) throws Exception {
        StringWriter output = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(output);
        jsonWriter.beginObject();
        jsonWriter.name("image").value(data.getImage());
        jsonWriter.name("name").value(data.getName());
        jsonWriter.name("number").value(data.getNumber());
        jsonWriter.name("price").value(data.getPrice());
        jsonWriter.endObject();
        String jsonText = output.toString();
        return jsonText;
    }
//chuyển thành đối tượng
    public static Cart readJsonStream(JSONObject object) throws Exception {
        Cart cart = new Cart();
        String image = object.getString("image");
        String name = object.getString("name");
        String number = object.getString("number");
        String price = object.getString("price");
        cart.setImage(image);
        cart.setName(name);
        cart.setNumber(Integer.parseInt(number));
        cart.setPrice(Integer.parseInt(price));
        return cart;
    }

    public static boolean saveToFile(String path, String fileName, String data) {
        try {
            File file2 = new File(path);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
            return true;
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return false;
    }
    public static boolean delete(String path, String fileNameInPut, String fileNameOutPut, String lineToRemove) {
        //đọc file ra 1 chuỗi json rồi xóa
        File input = new File(path + fileNameInPut);
        File output = new File(path + fileNameOutPut);
        FileInputStream inputStream;
        FileOutputStream outputStream;
        String currentLine;
        boolean successful = false;
        try {
            inputStream = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            //file Output
            outputStream = new FileOutputStream(output);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            successful = output.renameTo(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return successful;
    }
    public static List<Cart> readFile(String path) {
        List<Cart> cartList = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while ((s = reader.readLine()) != null) {
                JSONObject object = new JSONObject(s);
                Cart cart = readJsonStream(object);
                cartList.add(cart);
            }
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e("==============>Error: ", e.toString());
        }
        size=cartList.size();
        return cartList;
    }
}
