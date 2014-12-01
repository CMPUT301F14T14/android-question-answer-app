package ca.ualberta.cs.cmput301f14t14.questionapp.view;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;

public class AddImage {
	
    
	private Uri Urifile;
	ImageButton img;
	
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
}
