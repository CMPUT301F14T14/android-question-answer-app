package ca.ualberta.cs.cmput301f14t14.questionapp.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

public class Image implements Serializable {

	private static final long serialVersionUID = -5471444176345711312L;

	private Uri mLocalUrl;
	private byte[] imageData;
	
	public Image(Uri local, byte[] image) {
		setmLocalUrl(local);
		setImageData(image);
	}


	private void setmLocalUrl(Uri local) {
		// TODO Auto-generated method stub
		mLocalUrl = local;
	}

	public Uri getLocalUrl() {
		return mLocalUrl;
	}
	
	public String getByteImageFromFile() {
		Bitmap b = null;
		if (this.getLocalUrl() != null) {
			b = BitmapFactory.decodeFile(this.getLocalUrl().toString());
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] array = stream.toByteArray();
			return Base64.encodeToString(array, Base64.DEFAULT);
		}
		return null;
	}


	public byte[] getImageData() {
		return imageData;
	}


	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public Drawable getDrawable(Context c) {
		byte[] b = this.getImageData();
		return new BitmapDrawable(c.getResources(),BitmapFactory.decodeByteArray(b, 0, b.length));
	}

}
