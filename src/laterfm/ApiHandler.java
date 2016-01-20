package laterfm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import com.google.gson.*;
public class ApiHandler {
	final static String API_KEY = "8f744e9be960efba8d2c996fdd4851c7";
	final static String API_URL = "http://ws.audioscrobbler.com/2.0/";
	
	public static Track[] getAllTracks(String username) {
		DecimalFormat percentageFormat = new DecimalFormat("#.00");
		Track[] tracks;
		ApiURL au = new ApiURL("user.getRecentTracks");
		au.addParam("username", username);
		au.addParam("limit", 1000); // Get the rage page total with the right limt (1000 is the maximum)
		
		ApiResponse countCheck = getResponse(au);
		
		try {
			// scrobbling error margin, in case someone is still scrobbling
			tracks = new Track[countCheck.recentTracks.userInfo.total + 100]; 
			
			System.out.println(String.valueOf(tracks.length) + " scrobbles to backup.");
			System.out.println(String.valueOf(countCheck.recentTracks.userInfo.totalPages) + " pages to read.");		
			
			int trackCount = 0;
			for(int i = 0; i < countCheck.recentTracks.userInfo.totalPages; i++) {
				ApiURL pageReq = new ApiURL("user.getRecentTracks");
				pageReq.addParam("username", username);
				pageReq.addParam("limit", 1000);
				pageReq.addParam("page", i + 1);
				
				ApiResponse page = getResponse(pageReq);
				
				System.out.print(
					"reading page " + String.valueOf(i + 1) + " (" +
					percentageFormat.format(((double)i + 1) / countCheck.recentTracks.userInfo.totalPages * 100) +
					"%)             \r"
				);
				
				for(Track t : page.recentTracks.tracks) {
					tracks[trackCount++] = t;
				}
				
				if(Laterfm.debug) {
					if(i==5) {
						break;
					}
				}
			}
			
			System.out.println(
				"Reading complete." +
				"                                  "
			);
		} catch(Exception e) {
			if(Laterfm.debug) {
				e.printStackTrace();
			}

			return new Track[0];
		}

		return tracks;
	}

	public static ApiResponse getResponse(ApiURL au) {
		try {
			String buffer	= "";
			String line		= null;
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							au.getURL().openStream(),
							"UTF-8" // Reason should be obvious
					)
			);

			while((line = reader.readLine()) != null) {
				buffer += line;
			}

			reader.close();

			Gson gson = new Gson();

			ApiResponse ar = gson.fromJson(buffer, ApiResponse.class);

			return ar;
		} catch(Exception e) {
			if(Laterfm.debug) {
				e.printStackTrace();
			}
		}
		
		return new ApiResponse();
	}
}
