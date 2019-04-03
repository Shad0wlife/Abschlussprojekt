package abschlussprojekt.util.math;

public class MatrixOperations {

	/**
	 * Multiplies a matrix with a vector
	 * @param matrix The Matrix to multiply
	 * @param vector The vector to multiply
	 * @return The resulting vector.
	 */
	public static int[] matrixVectorProduct(int[][] matrix, int[] vector)
    {
        int width = getMatrixWidth(matrix);
        int height = getMatrixHeight(matrix);
        int length = vector.length;
        
        // Prüfen, ob Matrix und Vektor kompatibel sind
        if(width == length)
        {
            int[] result = new int[height];
            // Zeile der Matrix und Position in Ergebnisvektor
            for(int posResult = 0; posResult < height; posResult++)
            {
                // Position s in Zeile der Matrix und Position im Vektor
                for(int s = 0; s < width; s++)
                {
                    result[posResult] += matrix[posResult][s] * vector[s];
                }
            }
            return result;
        }
        else
        {
        	//TODO So lassen, null zurückgeben oder Exception werfen?
            int[] result = new int[0];
            return result; // Fehler zurückgeben (korrekt?)
        }
    }
    
   /**
    * Calculates the kronecker-product of two matrices.
    * @param matA First matrix.
    * @param matB Second matrix.
    * @return The reuslting kronecker-product matrix.
    */
    public static int[][] kroneckerProduct(int[][] matA, int[][] matB)
    {
        // Breite und Höhe der Eingangsmatritzen anfordern
        int widthA = getMatrixWidth(matA);
        int heightA = getMatrixHeight(matA);
        int widthB = getMatrixWidth(matB);
        int heightB = getMatrixHeight(matB);
        // Breite und Höhe der Ausgangsmatrix bestimmen
        int widthResult = widthA * widthB;
        int heightResult = heightA * heightB;
        
        int[][]  result = new int[widthResult][heightResult];
        
        // Ausgangsmatrix berechnen
        for(int matAx = 0; matAx < widthA; matAx++)
        {
            for(int matAy = 0; matAy < heightA; matAy++)
            {
                for(int matBx = 0; matBx < widthB; matBx++)
                {
                    for(int matBy = 0; matBy < heightB; matBy++)
                    {
                        result[(matAx*widthB)+matBx][(matAy*heightB)+matBy]=matA[matAx][matAy]*matB[matBx][matBy];
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Gets the width of a matrix.
     * Requires a rectangular matrix (no jagged array!) with a size greater than 0.
     * @param matrix
     * @return
     */
    public static int getMatrixWidth(int[][] matrix)
    {
        int width = matrix[0].length;
        return width;
    }
    
    /**
     * Gets the height of a matrix.
     * Requires a rectangular matrix (no jagged array!) with a size greater than 0.
     * @param matrix
     * @return
     */
    public static int getMatrixHeight(int[][] matrix)
    {
        int height = matrix.length;
        return height;
    }
	
}
