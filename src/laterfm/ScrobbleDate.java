package laterfm;

import com.google.gson.annotations.SerializedName;

public class ScrobbleDate {
	@SerializedName("uts")
	public String timestamp;
	@SerializedName("#text")
	public String humanizedDate;
}
