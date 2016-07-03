package com.banvien.fcv.mobile.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtils {

	public static File savebitmap(String filePath) {
		File file = new File(filePath);
		try {
			// make a new bitmap from your file
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);

			OutputStream outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e("file", "" + file);
		return file;

	}
	
}
