package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import ca.ualberta.cs.cmput301f14t14.questionapp.R;
import ca.ualberta.cs.cmput301f14t14.questionapp.model.Image;

public class AddImage {
	
    
	private Uri Urifile;
	ImageButton img;
	private int maxSize = 64000;
	
	public Intent takeAPhoto(){
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QAImages";
		File folder = new File(path);
		
		if (!folder.exists()){
			folder.mkdir();
		}
			
		String imagePathAndFileName = path + File.separator + 
				String.valueOf(System.currentTimeMillis()) + ".jpg" ;
		
		File imageFile = new File(imagePathAndFileName);
		
		Urifile = Uri.fromFile(imageFile); 
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Urifile);
		
		return intent;
	}
	
	public Uri getImgUri(){
		return Urifile;
	}
	
	public Intent addPhoto(){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		return intent;
	}
	
	public Bitmap getBitmap(Image img) {
		File imgFile = new File(img.getLocalUrl().getPath());
		long len = imgFile.length();
	    int width=100;
	    int height=100;
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), op);
		bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
		return bmp;
	}
}
