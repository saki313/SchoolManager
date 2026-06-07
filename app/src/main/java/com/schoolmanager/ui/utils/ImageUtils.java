package com.schoolmanager.ui.utils;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static String saveToInternalStorage(Context context, Bitmap bitmap) {
        String filename = "photo_" + System.currentTimeMillis() + ".jpg";
        File directory = context.getFilesDir();
        File file = new File(directory, filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromPath(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
