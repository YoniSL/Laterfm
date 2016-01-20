package laterfm;

import com.google.gson.annotations.SerializedName;

public class RecentTracks {
	@SerializedName("track")
	public Track[] tracks;
	@SerializedName("@attr")
	public UserInfo userInfo;
}
