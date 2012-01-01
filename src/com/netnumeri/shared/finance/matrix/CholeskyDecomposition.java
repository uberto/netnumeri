package com.netnumeri.shared.finance.matrix;


import com.netnumeri.shared.finance.utils.LogUtils;

/**
 * Cholesky Decomposition. <P> For a symmetric, positive definite matrix A, the Cholesky decomposition is an lower
 * triangular matrix L so that A = L*L'. <P> If the matrix is not symmetric or positive definite, the constructor
 * returns a partial decomposition and sets an internal flag that may be queried by the isSPD() method.
 */

public class CholeskyDecomposition implements java.io.Serializable {

    /**
     * Array for internal storage of decomposition.
     *
     * @serial internal array storage.
     */
    private double[][] L;

    /**
     * Row and column dimension (square matrix).
     *
     * @serial matrix dimension.
     */
    private int n;

    /**
     * Symmetric and positive definite flag.
     *
     * @serial is symmetric and positive definite flag.
     */
    private boolean isspd;

    /**
     */
    public CholeskyDecomposition(Matrix Arg) {
        // Initialize.
        double[][] A = Arg.getArray();
        n = Arg.getRowDimension();
        L = new double[n][n];
        isspd = (Arg.getColumnDimension() == n);
        // Main loop.
        for (int j = 0; j < n; j++) {
            double[] Lrowj = L[j];
            double d = 0.0;
            for (int k = 0; k < j; k++) {
                double[] Lrowk = L[k];
                double s = 0.0;
                for (int i = 0; i < k; i++) {
                    s += Lrowk[i] * Lrowj[i];
                }
                Lrowj[k] = s = (A[j][k] - s) / L[k][k];
                d = d + s * s;
                if ((A[k][j] == A[j][k]) == false) {
                    LogUtils.debug("matrix is not simmetric j=" + j + " k:" + k + " " + A[k][j] + " " + A[j][k]);
                }
                isspd = isspd & (A[k][j] == A[j][k]);
            }
            d = A[j][j] - d;
            if (d <= 0) {
                LogUtils.debug("matrix is not positive j=" + j + " d=" + d + "A[j][j]=" + A[j][j]);
            }
            isspd = isspd & (d > 0.0);
            L[j][j] = Math.sqrt(Math.max(d, 0.0));
            for (int k = j + 1; k < n; k++) {
                L[j][k] = 0.0;
            }
        }
    }

    /**
     * Is the matrix symmetric and positive definite?
     *
     * @return true if A is symmetric and positive definite.
     */

    public boolean isSPD() {
        return isspd;
    }

    /**
     * Return triangular factor.
     *
     * @return L
     */

    public Matrix getL() {
        return new Matrix(L, n, n);
    }

    /**
     * Solve A*X = B
     *
     * @param B A Matrix with as many rows as A and any number of columns.
     * @return X so that L*L'*X = B
     * @throws IllegalArgumentException Matrix row dimensions must agree.
     * @throws RuntimeException         Matrix is not symmetric positive definite.
     */
    public Matrix solve(Matrix B) {
        if (B.getRowDimension() != n) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!isspd) {
            throw new RuntimeException("Matrix is not symmetric positive definite.");
        }

        // Copy right hand side.
        double[][] X = B.getArrayCopy();
        int nx = B.getColumnDimension();

        // Solve L*Y = B;
        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * L[i][k];
                }
            }
            for (int j = 0; j < nx; j++) {
                X[k][j] /= L[k][k];
            }
        }

        // Solve L'*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                X[k][j] /= L[k][k];
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * L[k][i];
                }
            }
        }
        return new Matrix(X, n, nx);
    }
}