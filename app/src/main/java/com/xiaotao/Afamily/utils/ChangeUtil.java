package com.xiaotao.Afamily.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 *
 * @author xiaoTao
 * @date 2016-03-03  19:12
 */
public class ChangeUtil {
    /*
     * toBinary 将Bitmap转为String
     * @param bitmap 要转换的
     * */
    public static String toBinary(Bitmap bitmap) {
        if (bitmap == null) {
            return "False";
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] Buffer = os.toByteArray();
        //s = new String(Buffer);
        String s = Base64.encodeToString(Buffer, Base64.DEFAULT);
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
    * toBinary 将File转为String
    * @param file 要转换的
    * */
    public static String toBinary(File file) {
        String s = null;
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            while ((len = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            byte[] data = os.toByteArray();
            s = new String(Base64.encode(data, Base64.DEFAULT));
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
    * readStream 将File转为String
    * @param file 要转换的
    * test不太好使
    * */
    public static String readStream(File file) {
        String s = null;
        int len = 0;
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] Buffer = new byte[1024];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            while ((len = inputStream.read(Buffer)) >= 0) {
                os.write(Buffer, 0, len);
            }
            //s = new String(Buffer);
            s = Base64.encodeToString(Buffer, Base64.DEFAULT);
            os.flush();
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
    * toBitmap 将String转为Bitmap
    * @param 转换的String
    * */
    public static Bitmap toBitmap(String s) {
        //byte[] Buffer = s.getBytes();
        byte[] buffer = Base64.decode(s, Base64.DEFAULT);
        //ByteArrayInputStream inputStream = new ByteArrayInputStream(Buffer);
        //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.length, null);
    }

    /*
    * toFile 将String转为File
    * @param 转换的String
    * */
    public static File toFile(String s, String dir, String fileName) {
        //byte[] Buffer = s.getBytes();
        byte[] Buffer = Base64.decode(s, Base64.DEFAULT);
        File file = new File(dir, fileName);
        //ByteArrayInputStream is = new ByteArrayInputStream(Buffer);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(Buffer);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
