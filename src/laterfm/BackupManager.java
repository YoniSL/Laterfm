package laterfm;

import java.io.*;
import java.text.DecimalFormat;

public class BackupManager {
	public static void backupTracks(Track[] tracks) {
		File file = new File(Laterfm.outputPath);
		DecimalFormat percentageFormat = new DecimalFormat("#.00");
		
		if(!file.isDirectory()) {
			try {
				PrintWriter writer = new PrintWriter(Laterfm.outputPath, "UTF-8");
				
				System.out.println("Writing to file started for " + String.valueOf(tracks.length - 100) + " tracks");
				
				int i = 1;
				for(Track track : tracks) {
					if( // This is ugly
						track != null && track.artist != null && track.artist.name != null &&
						track.name != null && track.album != null && track.album.name != null
					) {
						if(track.date == null || track.date.timestamp == null) { // Scrobbling while backing up huh problems
							track.date = new ScrobbleDate();
							track.date.timestamp = String.valueOf(System.currentTimeMillis() / 1000);
						}
						
						System.out.print(
							"Wrting scrobbles to file (" +
							percentageFormat.format((double)i / (tracks.length - 100) * 100) + "%)" +
							"                     \r" // cleaning line
						);
						
						writer.println("\"" + track.artist.name + "\",\"" + track.name + "\",\"" + track.album.name + "\",\"" + track.date.timestamp + "\"");
						
						i++;
					} else { // Should always happen due to the 100 now scrobbling error margin
						System.out.println(
							"Wrote " + i + " scrobbles to file." +
							"                                  " // cleaning line
						);
						break; // Reached the end
					}
				}
				
				writer.close();
			} catch(Exception e) {
				if(Laterfm.debug) {
					e.printStackTrace();
				}
				
				System.out.println("Writing error. Check whether the filepath is writable");
			}
		}
	}
}
