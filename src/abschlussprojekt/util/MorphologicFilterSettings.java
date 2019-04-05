package abschlussprojekt.util;

/**
 * Contains all relevant filter settings for a morphologic filter selection to avoid handing them all one by one.
 */
public class MorphologicFilterSettings {
	
	private MorphologicFilterChoice filterChoice;
	private int matrixSize;
	
	public MorphologicFilterSettings(MorphologicFilterChoice filterChoice, int matrixSize) {
		this.filterChoice = filterChoice;
		this.matrixSize = matrixSize;
	}

	public MorphologicFilterChoice getFilterChoice() {
		return filterChoice;
	}

	public int getMatrixSize() {
		return matrixSize;
	}

}
