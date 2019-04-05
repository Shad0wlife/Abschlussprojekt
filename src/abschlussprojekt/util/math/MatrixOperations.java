package abschlussprojekt.util.math;

public class MatrixOperations { //TODO Kommentare �bersetzen

	/**
	 * Multiplies a matrix with a vector
	 * @param matrix The Matrix to multiply
	 * @param vector The vector to multiply
	 * @return The resulting vector.
	 */
	public static int[] matrixVectorProduct(int[][] matrix, int[] vector)
    {
		if(!isMatrixRectangular(matrix)) {
			throw new IllegalArgumentException("The matrix is a jagged array and thus not rectangular. Matrices must be rectangular.");
		}
		
        int width = getMatrixWidth(matrix);
        int height = getMatrixHeight(matrix);
        int length = vector.length;
        
        // Pr�fen, ob Matrix und Vektor kompatibel sind
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
        	throw new IllegalArgumentException("The matrix dimensions do not match the vector dimensions required for multiplication.");
        }
    }
    
   /**
    * Calculates the kronecker-product of two matrices.
    * @param matA First matrix.
    * @param matB Second matrix.
    * @return The resulting kronecker-product matrix.
    */
    public static int[][] kroneckerProduct(int[][] matA, int[][] matB)
    {
    	if(!isMatrixRectangular(matA) || !isMatrixRectangular(matB)) {
			throw new IllegalArgumentException("A matrix is a jagged array and thus not rectangular. Matrices must be rectangular.");
		}
        // Breite und H�he der Eingangsmatritzen anfordern
        int widthA = getMatrixWidth(matA);
        int heightA = getMatrixHeight(matA);
        int widthB = getMatrixWidth(matB);
        int heightB = getMatrixHeight(matB);
        // Breite und H�he der Ausgangsmatrix bestimmen
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
    
    /**
     * Checks whether a matrix is rectangular by checking that all line lengths are equal
     * @param matrix The matrix to check
     * @return Whether the matrix is rectangular
     */
    public static boolean isMatrixRectangular(int[][] matrix) {
    	int width = matrix[0].length;
    	for(int cnt = 1; cnt < matrix.length; cnt++) {
    		if(matrix[cnt].length != width) {
    			return false;
    		}
    	}
    	return true;
    }
	
}
