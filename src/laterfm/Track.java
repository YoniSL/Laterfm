package laterfm;

import com.google.gson.annotations.SerializedName;

public class Track {
	public Artist artist;
	public String name;
	public String streamable;
	public String mbid;
	public Album album;
	public String url;
	public Image[] image;
	@SerializedName("date")
	public ScrobbleDate date;
	
	// TODO nowplaying?
}
