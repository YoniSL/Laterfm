package laterfm;

public class Laterfm {
	public static String username = "";
	public static String outputPath = "backup.csv";
	public static boolean debug = false;
	
	public static void main(String[] args) {
		int i = 0;
		int lastUsedParamIndex = 0;
		
		for(String s : args) {
			switch(s) {
				case "-user":
				case "-username":
					if(i + 1 < args.length) {
						Laterfm.username = args[i + 1];
						lastUsedParamIndex = i + 1;
					}
				break;
				
				default:
					if(i + 1 == args.length && i != lastUsedParamIndex) {
						Laterfm.outputPath = args[i];
					}
				break;
			}
			
			i++;
		}
		
		Laterfm.outputPath = Laterfm.outputPath.replace("\\", "\\\\");
		
		if(!Laterfm.username.equals("") && !Laterfm.outputPath.equals("")) { // TODO check whether file can be created and written to
			Laterfm fm = new Laterfm();
			fm.run();
		} else {
			System.out.println("Missing a required parameter");
		}
	}
	
	public void run() {
		System.out.print(
			" _           _             __           \n" +
			"| |         | |           / _|          \n" +
			"| |     __ _| |_ ___ _ __| |_ _ __ ___  \n" +
			"| |    / _` | __/ _ \\ '__|  _| '_ ` _ \\ \n" +
			"| |___| (_| | ||  __/ |  | | | | | | | |\n" +
			"\\_____/\\__,_|\\__\\___|_|  |_| |_| |_| |_|\n" +
			"                                        \n" +
			"                                        \n"
		);
		
		System.out.println("By YoniSL (last.fm/user/yonisl)\nPlease stop scrobbling while backing up\n");
		System.out.println("----------------------------------------\n");
		System.out.println("Backup started for user \"" + Laterfm.username + "\"");
		System.out.println("Output path \"" + Laterfm.outputPath + "\"\n\n");
	
		Track[] tracks = ApiHandler.getAllTracks(Laterfm.username);
		BackupManager.backupTracks(tracks);
	}
}
