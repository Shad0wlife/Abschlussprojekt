package abschlussprojekt.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
	
	private final String NEWLINE = "\r\n";
	private final char SEPERATOR;
	private final File target;
	
	public CSVWriter(File target) {
		this.target = target;
		this.SEPERATOR = ',';
	}
	
	public CSVWriter(File target, char seperator) {
		this.target = target;
		this.SEPERATOR = seperator;
	}
	
	public void write(List<int[]> results) {
		System.out.println("Writing to File " + this.target.getName());
		try (FileWriter writer = new FileWriter(target)){
			StringBuilder builder = new StringBuilder();
			builder.append(SEPERATOR);
			for(int cnt = 0; cnt < results.get(0).length; cnt++) {
				builder.append("Klasse ");
				builder.append(cnt + 1);
				builder.append(SEPERATOR);
			}
			builder.append(NEWLINE);
			writer.write(builder.toString());
			
			for(int cnt = 0; cnt < results.size(); cnt++) {
				builder = new StringBuilder("Datensatz ");
				builder.append(cnt);
				builder.append(SEPERATOR);
				for(int x : results.get(cnt)) {
					builder.append(x);
					builder.append(SEPERATOR);
				}
				builder.append(NEWLINE);
				writer.write(builder.toString());
			}
		} catch (IOException e) {
			System.err.println("Fehler beim Schreiben der Daten!");
			e.printStackTrace();
		}
	}

}
