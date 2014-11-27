package ca.ualberta.cs.cmput301f14t14.questionapp.data.threading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.ESSearch;
import ca.ualberta.cs.cmput301f14t14.questionapp.data.GenericSearchItem;

public class ESSearchTask extends AbstractDataManagerTask<String, Void, List<GenericSearchItem>> {
	private Context context;
	public ESSearchTask(Context c) {
		super(c);
		context = c;
	}

	@Override
	protected List<GenericSearchItem> doInBackground(String... arg0) {
		
		String query = arg0[0];
		List<GenericSearchItem> gSIList = null;
		ESSearch es = new ESSearch(context);
		try{
			gSIList = es.search(query);
		}catch(IOException e){
			Log.e("ESSearch", "Failed to get search results");
			gSIList = new ArrayList<GenericSearchItem>();
		}
		return gSIList;
	}
	
	@Override
	protected void onPostExecute(List<GenericSearchItem> la) {
		if (callback == null){
			return;
		}
		callback.run(la);
	}

}
