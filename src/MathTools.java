package src;

public class MathTools {
    public static double[][] multScalar(double[][] matrix, double scale) {
        double[][] scaledMatrix = new double[matrix.length][matrix[0].length];
        if (matrix.length > 0 && matrix[0].length > 0) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    scaledMatrix[i][j] = scale * matrix[i][j];
                }
            }
            return scaledMatrix;
        } else {
            return matrix;
        }
    }

    public static double[][] multMatrix(double[][] a, double[][] b) {
        double[][] multipliedMatrix = new double[a.length][b[0].length];
        if (a.length * b.length * b[0].length > 0 && a[0].length == b.length) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    double result = 0;
                    for (int k = 0; k < a[0].length; k++) {
                        result += a[i][k] * b[k][j];
                    }
                    multipliedMatrix[i][j] = result;
                }
            }
            return multipliedMatrix;
        } else {
            // if the inputs are invalid, return null
            return null;
        }
    }

    public static double euclideanDist(double[][] a, double[][] b) {
        if (a.length == 1 && a.length == b.length && a[0].length == b[0].length) {
            double result = 0;
            for (int i = 0; i < a[0].length; i++) {
                result += Math.pow(a[0][i] - b[0][i], 2);
            }
            result = Math.pow(result, 0.5);
            return result;
        } else {
            // if the inputs are invalid, return -1
            return -1;
        }
    }

}
