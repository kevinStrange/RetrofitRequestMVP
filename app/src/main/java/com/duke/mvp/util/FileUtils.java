package com.duke.mvp.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author : Duke
 * date   : 2018/8/22
 * explain   :文件操作工具類
 * version: 1.0
 */
public class FileUtils {
    public static File SDPATH = Environment.getExternalStorageDirectory().getAbsoluteFile();
    private static File path;

    public static void init() {
        if (path == null) {
            path = new File(SDPATH + "/sctek/");
        }
        if (!path.exists()) {
            path.mkdir();
        }
    }

    /**
     * 根据byte数据保存成文件
     * @return
     */
    public static void saveFileByByteData(byte[] data, String fileName){
        BufferedOutputStream bufferedOutputStream = null;
        try {
            File file = new File(path, fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file, true);
            bufferedOutputStream = new BufferedOutputStream(out);
            bufferedOutputStream.write(data);
            bufferedOutputStream.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e ){
            e.printStackTrace();
        }

    }

    //保存图片
    public static int saveBitmap(Bitmap bm, String picName) {
        try {
            File f = new File(path, picName);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Lg.d("success");
            return 1;
        } catch (FileNotFoundException e) {
            Lg.d("FileNotFoundException " + e);
        } catch (IOException e) {
            Lg.d("IOException " + e);
        }
        return 0;
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }


    //删除文件
    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile() || file.exists()) {
            file.delete();
        }
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir() {
        File dir = new File(SDPATH + "/sctek/");
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

}
