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
		System.out.println("Writing to File " + this.target.getName()); //DEBUG
		try (FileWriter writer = new FileWriter(target)){
			int numClasses = results.get(0).length;
			writeHeaderLine(writer, numClasses);
			
			for(int cnt = 0; cnt < results.size(); cnt++) {
				writeDataLine(writer, cnt, results.get(cnt));
			}
		} catch (IOException e) {
			System.err.println("Fehler beim Schreiben der Daten!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a header line to the CSV, containing an empty corner field and a title for each class column
	 * @param writer The FileWriter to write to
	 * @param numClasses The amount of classes a title should be created for
	 * @throws IOException
	 */
	private void writeHeaderLine(FileWriter writer, int numClasses) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(SEPERATOR);
		for(int cnt = 0; cnt < numClasses; cnt++) {
			builder.append("Klasse ");
			builder.append(cnt + 1);
			builder.append(SEPERATOR);
		}
		builder.append(NEWLINE);
		writer.write(builder.toString());
	}
	
	/**
	 * Writes a data line to the CSV, containing a row index for the dataset and the classification information for each class
	 * @param writer The FileWriter to write to
	 * @param datasetIndex The line index for the dataset to print
	 * @param classification The classification information for the dataset
	 * @throws IOException
	 */
	private void writeDataLine(FileWriter writer, int datasetIndex, int[] classification) throws IOException {
		StringBuilder builder = new StringBuilder("Datensatz ");
		builder.append(datasetIndex);
		builder.append(SEPERATOR);
		for(int x : classification) {
			builder.append(x);
			builder.append(SEPERATOR);
		}
		builder.append(NEWLINE);
		writer.write(builder.toString());
	}

}
