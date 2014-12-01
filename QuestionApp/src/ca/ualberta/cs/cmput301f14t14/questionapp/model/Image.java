package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
		b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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
	
	public Drawable getDrawable(Context c) {
		byte[] b = this.getImageData();
		return new BitmapDrawable(c.getResources(),
				BitmapFactory.decodeByteArray(b, 0, b.length));
	}

}
