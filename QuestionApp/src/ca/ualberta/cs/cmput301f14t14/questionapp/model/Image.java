package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class Image implements Serializable {

	private static final long serialVersionUID = -5471444176345711312L;

	private byte[] imageData;
	
	public Image(Context context, Uri path) {
		Bitmap b = null;
		try {
			b = MediaStore.Images.Media.getBitmap(context.getContentResolver(), path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			imageData = null;
		} catch (IOException e) {
			e.printStackTrace();
			imageData = null;
		}
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		imageData = stream.toByteArray();
	}

	public Image(byte[] imageData) {
		this.imageData = imageData;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public long getSize(){
		return imageData.length;
	}
	public Bitmap getBitmap() {
		int width = 100;
		int height = 100;
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bmp = BitmapFactory.decodeByteArray(getImageData(), 0,
				getImageData().length);
		bmp = Bitmap.createScaledBitmap(bmp, width, height, true);
		return bmp;
	}
	
	public byte[] compress(Bitmap b){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		byte[] imageD = stream.toByteArray();
		return imageD;
	}

}
