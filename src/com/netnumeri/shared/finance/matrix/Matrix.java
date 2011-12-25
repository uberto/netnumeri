package com.netnumeri.shared.finance.matrix;


import java.io.Serializable;


/**
 * Jama = Java Matrix class. <P> The Java Matrix Class provides the fundamental operations of numerical linear algebra.
 * Various constructors create Matrices from two dimensional arrays of double precision floating point numbers.  Various
 * "gets" and "sets" provide access to submatrices and matrix elements.  Several methods implement basic matrix
 * arithmetic, including matrix addition and multiplication, matrix norms, and element-by-element array operations.
 * Methods for reading and printing matrices are also included.  All the operations in this version of the Matrix Class
 * involve real matrices. Complex matrices may be handled in a future version. <P> Five fundamental matrix
 * decompositions, which consist of pairs or triples of matrices, permutation vectors, and the like, produce results in
 * five decomposition classes.  These decompositions are accessed by the Matrix class to compute solutions of
 * simultaneous linear equations, determinants, inverses and other matrix functions.  The five decompositions are:
 * <P><UL> <LI>Cholesky Decomposition of symmetric, positive definite matrices. <LI>LU Decomposition of rectangular
 * matrices. <LI>QR Decomposition of rectangular matrices. <LI>Singular Value Decomposition of rectangular matrices.
 * <LI>Eigenvalue Decomposition of both symmetric and nonsymmetric square matrices. </UL> <DL> <DT><B>Example of
 * use:</B></DT> <P> <DD>Solve a linear system A x = b and compute the residual norm, ||b - A x||. <P><PRE> double[][]
 * vals = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}}; Matrix A = new Matrix(vals); Matrix b = Matrix.random(3,1); Matrix x =
 * A.solve(b); Matrix r = A.times(x).minus(b); double rnorm = r.normInf(); </PRE></DD> </DL>
 *
 * @author The MathWorks, Inc. and the National Institute of Standards and Technology.
 * @version 5 August 1998
 */

public class Matrix implements Serializable {

    /**
     * Array for internal storage of elements.
     *
     * @serial internal array storage.
     */
    private double[][] A;

    /**
     * Row and column dimensions.
     *
     * @serial row dimension.
     * @serial column dimension.
     */
    private int rows, cols;

    /**
     * Construct an rows-by-cols matrix of zeros.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     */

    public Matrix(int m, int n) {
        this.rows = m;
        this.cols = n;
        A = new double[m][n];
    }

    /**
     * Construct an rows-by-cols constant matrix.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @param s Fill the matrix with this scalar value.
     */
    public Matrix(int m, int n, double s) {
        this.rows = m;
        this.cols = n;
        A = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = s;
            }
        }
    }

    /**
     * Construct a matrix from a 2-D array.
     *
     * @param A Two-dimensional array of doubles.
     * @throws IllegalArgumentException All rows must have the same length
     * @see #constructWithCopy
     */
    public Matrix(double[][] A) {
        rows = A.length;
        cols = A[0].length;
        for (int i = 0; i < rows; i++) {
            if (A[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
        }
        this.A = A;
    }

    /**
     * Construct a matrix quickly without checking arguments.
     *
     * @param A Two-dimensional array of doubles.
     * @param m Number of rows.
     * @param n Number of colums.
     */

    public Matrix(double[][] A, int m, int n) {
        this.A = A;
        this.rows = m;
        this.cols = n;
    }

    /**
     * Construct a matrix from a one-dimensional packed array
     *
     * @param vals One-dimensional array of doubles, packed by columns (ala Fortran).
     * @param m    Number of rows.
     * @throws IllegalArgumentException Array length must be a multiple of rows.
     */

    public Matrix(double vals[], int m) {
        this.rows = m;
        cols = (m != 0 ? vals.length / m : 0);
        if (m * cols != vals.length) {
            throw new IllegalArgumentException("Array length must be a multiple of rows.");
        }
        A = new double[m][cols];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = vals[i + j * m];
            }
        }
    }

    /**
     * Construct a matrix from a copy of a 2-D array.
     *
     * @param A Two-dimensional array of doubles.
     * @throws IllegalArgumentException All rows must have the same length
     */

    public static Matrix constructWithCopy(double[][] A) {
        int m = A.length;
        int n = A[0].length;
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            if (A[i].length != n) {
                throw new IllegalArgumentException
                        ("All rows must have the same length.");
            }
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j];
            }
        }
        return X;
    }

    /**
     * Make a deep copy of a matrix
     */

    public Matrix copy() {
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j];
            }
        }
        return X;
    }

    /**
     * Clone the Matrix object.
     */

    public Object clone() {
        return this.copy();
    }

    /**
     * Access the internal two-dimensional array.
     *
     * @return Pointer to the two-dimensional array of matrix elements.
     */

    public double[][] getArray() {
        return A;
    }

    /**
     * Copy the internal two-dimensional array.
     *
     * @return Two-dimensional array copy of matrix elements.
     */

    public double[][] getArrayCopy() {
        double[][] C = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j];
            }
        }
        return C;
    }

    /**
     * Make a one-dimensional column packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by columns.
     */
    public double[] getColumnPackedCopy() {
        double[] vals = new double[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                vals[i + j * rows] = A[i][j];
            }
        }
        return vals;
    }

    /**
     * Make a one-dimensional row packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by rows.
     */

    public double[] getRowPackedCopy() {
        double[] vals = new double[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                vals[i * cols + j] = A[i][j];
            }
        }
        return vals;
    }

    /**
     * Get row dimension.
     *
     * @return rows, the number of rows.
     */
    public int getRowDimension() {
        return rows;
    }

    /**
     * Get column dimension.
     *
     * @return cols, the number of columns.
     */

    public int getColumnDimension() {
        return cols;
    }

    /**
     * Get a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @return A(i,j)
     * @throws ArrayIndexOutOfBoundsException
     */

    public double get(int i, int j) {
        return A[i][j];
    }

    /**
     * Get a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param j0 Initial column index
     * @param j1 Final column index
     * @return A(i0:i1,j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int i0, int i1, int j0, int j1) {
        Matrix X = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
        double[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i - i0][j - j0] = A[i][j];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get a submatrix.
     *
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @return A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int[] r, int[] c) {
        Matrix X = new Matrix(r.length, c.length);
        double[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < c.length; j++) {
                    B[i][j] = A[r[i]][c[j]];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param c  Array of column indices.
     * @return A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int i0, int i1, int[] c) {
        Matrix X = new Matrix(i1 - i0 + 1, c.length);
        double[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = 0; j < c.length; j++) {
                    B[i - i0][j] = A[i][c[j]];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get a submatrix.
     *
     * @param r Array of row indices.
     * @return A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int[] r, int j0, int j1) {
        Matrix X = new Matrix(r.length, j1 - j0 + 1);
        double[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i][j - j0] = A[r[i]][j];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Set a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @param s A(i,j).
     * @throws ArrayIndexOutOfBoundsException
     */

    public void set(int i, int j, double s) {
        A[i][j] = s;
    }

    /**
     * Set a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param j0 Initial column index
     * @param j1 Final column index
     * @param X  A(i0:i1,j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int i0, int i1, int j0, int j1, Matrix X) {
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = j0; j <= j1; j++) {
                    A[i][j] = X.get(i - i0, j - j0);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @param X A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int[] r, int[] c, Matrix X) {
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < c.length; j++) {
                    A[r[i]][c[j]] = X.get(i, j);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param r  Array of row indices.
     * @param j0 Initial column index
     * @param j1 Final column index
     * @param X  A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int[] r, int j0, int j1, Matrix X) {
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = j0; j <= j1; j++) {
                    A[r[i]][j] = X.get(i, j - j0);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param c  Array of column indices.
     * @param X  A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int i0, int i1, int[] c, Matrix X) {
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = 0; j < c.length; j++) {
                    A[i][c[j]] = X.get(i - i0, j);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Matrix transpose.
     *
     * @return A'
     */

    /*  public Matrix transpose()
        {
            Matrix X = new Matrix(cols, rows);
            double[][] C = X.getArray();
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    C[j][i] = A[i][j];
            return X;
        }
    */

    /**
     * One norm
     *
     * @return maximum column sum.
     */

    public double norm1() {
        double f = 0;
        for (int j = 0; j < cols; j++) {
            double s = 0;
            for (int i = 0; i < rows; i++) {
                s += Math.abs(A[i][j]);
            }
            f = Math.max(f, s);
        }
        return f;
    }

    /**
     * Two norm
     *
     * @return maximum singular value.
     */

    public double norm2() {
        return (new SingularValueDecomposition(this).norm2());
    }

    /**
     * Infinity norm
     *
     * @return maximum row sum.
     */

    public double normInf() {
        double f = 0;
        for (int i = 0; i < rows; i++) {
            double s = 0;
            for (int j = 0; j < cols; j++) {
                s += Math.abs(A[i][j]);
            }
            f = Math.max(f, s);
        }
        return f;
    }

    /**
     * Frobenius norm
     *
     * @return sqrt of sum of squares of all elements.
     */

    public double normF() {
        double f = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                f = Maths.hypot(f, A[i][j]);
            }
        }
        return f;
    }

    /**
     * Unary minus
     *
     * @return -A
     */

    public Matrix uminus() {
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = -A[i][j];
            }
        }
        return X;
    }

    /**
     * C = A + B
     *
     * @param B another matrix
     * @return A + B
     */

    public Matrix plus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j] + B.A[i][j];
            }
        }
        return X;
    }

    /**
     * A = A + B
     *
     * @param B another matrix
     * @return A + B
     */
    public Matrix plusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = A[i][j] + B.A[i][j];
            }
        }
        return this;
    }

    /**
     * C = A - B
     *
     * @param B another matrix
     * @return A - B
     */
    public Matrix minus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j] - B.A[i][j];
            }
        }
        return X;
    }

    /**
     * A = A - B
     *
     * @param B another matrix
     * @return A - B
     */
    public Matrix minusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = A[i][j] - B.A[i][j];
            }
        }
        return this;
    }

    /**
     * Element-by-element multiplication, C = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */
    public Matrix arrayTimes(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j] * B.A[i][j];
            }
        }
        return X;
    }

    /**
     * Element-by-element multiplication in place, A = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */
    public Matrix arrayTimesEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = A[i][j] * B.A[i][j];
            }
        }
        return this;
    }

    /**
     * Element-by-element right division, C = A./B
     *
     * @param B another matrix
     * @return A./B
     */
    public Matrix arrayRightDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j] / B.A[i][j];
            }
        }
        return X;
    }

    /**
     * Element-by-element right division in place, A = A./B
     *
     * @param B another matrix
     * @return A./B
     */
    public Matrix arrayRightDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = A[i][j] / B.A[i][j];
            }
        }
        return this;
    }

    /**
     * Element-by-element left division, C = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */
    public Matrix arrayLeftDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = B.A[i][j] / A[i][j];
            }
        }
        return X;
    }

    /**
     * Element-by-element left division in place, A = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */
    public Matrix arrayLeftDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = B.A[i][j] / A[i][j];
            }
        }
        return this;
    }

    /**
     * Multiply a matrix by a scalar, C = s*A
     *
     * @param s scalar
     * @return s*A
     */
    public Matrix times(double s) {
        Matrix X = new Matrix(rows, cols);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = s * A[i][j];
            }
        }
        return X;
    }

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     *
     * @param s scalar
     * @return replace A by s*A
     */
    public Matrix timesEquals(double s) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = s * A[i][j];
            }
        }
        return this;
    }

    /**
     * Linear algebraic matrix multiplication, A * B
     *
     * @param B another matrix
     * @return Matrix product, A * B
     * @throws IllegalArgumentException Matrix inner dimensions must agree.
     */
    public Matrix times(Matrix B) {
        if (B.rows != cols) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix X = new Matrix(rows, B.cols);
        double[][] C = X.getArray();
        double[] Bcolj = new double[cols];
        for (int j = 0; j < B.cols; j++) {
            for (int k = 0; k < cols; k++) {
                Bcolj[k] = B.A[k][j];
            }
            for (int i = 0; i < rows; i++) {
                double[] Arowi = A[i];
                double s = 0;
                for (int k = 0; k < cols; k++) {
                    s += Arowi[k] * Bcolj[k];
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    /**
     * LU Decomposition
     *
     * @return LUDecomposition
     */

    public LUDecomposition lu() {
        return new LUDecomposition(this);
    }

    /**
     * QR Decomposition
     *
     * @return QRDecomposition
     */

    public QRDecomposition qr() {
        return new QRDecomposition(this);
    }

    /**
     * Cholesky Decomposition
     *
     * @return CholeskyDecomposition
     */

    public CholeskyDecomposition chol() {
        return new CholeskyDecomposition(this);
    }

    /**
     * Singular Value Decomposition
     *
     * @return SingularValueDecomposition
     */

    public SingularValueDecomposition svd() {
        return new SingularValueDecomposition(this);
    }

    /**
     * Eigenvalue Decomposition
     *
     * @return EigenvalueDecomposition
     */
    public EigenvalueDecomposition eig() {
        return new EigenvalueDecomposition(this);
    }

    /**
     * Solve A*X = B
     *
     * @param B right hand side
     * @return solution if A is square, least squares solution otherwise
     */
    public Matrix solve(Matrix B) {
        return (rows == cols ? (new LUDecomposition(this)).solve(B) :
                (new QRDecomposition(this)).solve(B));
    }

    /**
     * Solve X*A = B, which is also A'*X' = B'
     *
     * @param B right hand side
     * @return solution if A is square, least squares solution otherwise.
     */

    public Matrix solveTranspose(Matrix B) {
        return transpose().solve(B.transpose());
    }

    /**
     * Matrix inverse or pseudoinverse
     *
     * @return inverse(A) if A is square, pseudoinverse otherwise.
     */
    public Matrix inverse() {
        return solve(identity(rows, rows));
    }

    /**
     * Matrix determinant
     *
     * @return determinant
     */
    public double det() {
        return new LUDecomposition(this).det();
    }

    /**
     * Matrix rank
     *
     * @return effective numerical rank, obtained from SVD.
     */
    public int rank() {
        return new SingularValueDecomposition(this).rank();
    }

    /**
     * Matrix condition (2 norm)
     *
     * @return ratio of largest to smallest singular value.
     */
    public double cond() {
        return new SingularValueDecomposition(this).cond();
    }

    /**
     * Matrix trace.
     *
     * @return sum of the diagonal elements.
     */

    public double trace() {
        double t = 0;
        for (int i = 0; i < Math.min(rows, cols); i++)
        //      {
        {
            t += A[i][i];
        }
        //     }
        return t;
    }

    /**
     * Generate matrix with random elements
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An rows-by-cols matrix with uniformly distributed random elements.
     */

    public static Matrix random(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++)
//        {
        {
            for (int j = 0; j < n; j++) {
                X[i][j] = Math.random();
            }
        }
//        }
        return A;
    }

    /**
     * Generate identity matrix
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An rows-by-cols matrix with ones on the diagonal and zeros elsewhere.
     */

    public static Matrix identity(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return A;
    }


    private void checkMatrixDimensions(Matrix B) {
        if (B.rows != rows || B.cols != cols) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
    }

    public Matrix scalarDivide(double price) {
        Matrix retm = (Matrix) this.clone();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                retm.set(i, j, retm.get(i, j) / price);
            }
        }
        return retm;
    }

    public double sumSquare() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += this.get(i, j) * this.get(i, j);
            }
        }
        return sum;
    }

    public Matrix toDiagonal() {
        Matrix matrix = new Matrix(rows * cols, rows * cols);
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.set(index, index, this.get(i, j));
                index++;
            }
        }
        return matrix;
    }

    public Matrix(Matrix matrix) {
        double ad[][] = matrix.toArray();
        Dimension dimension = matrix.dimensions();
        rows = dimension.height;
        cols = dimension.width;
        A = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] = ad[i][j];
            }
        }
    }

    public Matrix getSubMatrix(int i, int j, int k, int l) {
        double ad[][] = new double[k][l];
        for (int i1 = 0; i1 < k; i1++) {
            for (int j1 = 0; j1 < l; j1++) {
                ad[i1][j1] = A[(i + i1) - 1][(j + j1) - 1];
            }
        }
        return new Matrix(ad);
    }

    public static Matrix join(Matrix matrix, Matrix matrix1) {
        Dimension dimension = matrix.dimensions();
        Dimension dimension1 = matrix1.dimensions();
        if (dimension.height != dimension1.height) {
            throw new IndexOutOfBoundsException();
        }
        double ad[][] = matrix.toArray();
        double ad1[][] = matrix1.toArray();
        double ad2[][] = new double[dimension.height][dimension.width + dimension1.width];
        for (int i = 0; i < dimension.height; i++) {
            for (int j = 0; j < dimension.width; j++) {
                ad2[i][j] = ad[i][j];
            }

            for (int k = 0; k < dimension1.width; k++) {
                ad2[i][k + dimension.width] = ad1[i][k];
            }

        }
        return new Matrix(ad2);
    }

    public static Matrix stack(Matrix matrix, Matrix matrix1) {
        Dimension dimension = matrix.dimensions();
        Dimension dimension1 = matrix1.dimensions();
        if (dimension.width != dimension1.width) {
            throw new IndexOutOfBoundsException();
        }
        double ad[][] = matrix.toArray();
        double ad1[][] = matrix1.toArray();
        double ad2[][] = new double[dimension.height + dimension1.height][dimension.width];
        for (int i = 0; i < dimension.width; i++) {
            for (int j = 0; j < dimension.height; j++) {
                ad2[j][i] = ad[j][i];
            }
            for (int k = 0; k < dimension1.height; k++) {
                ad2[k + dimension.height][i] = ad1[k][i];
            }
        }
        return new Matrix(ad2);
    }

    public Dimension dimensions() {
        return new Dimension(cols, rows);
    }

    public double[][] toArray() {
        double ad[][] = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ad[i][j] = A[i][j];
            }
        }
        return ad;
    }

    public Matrix add(Matrix matrix) {
        Dimension dimension = matrix.dimensions();
        if (dimension.width != cols || dimension.height != rows) {
            throw new IndexOutOfBoundsException();
        }
        double ad[][] = matrix.toArray();
        double ad1[][] = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ad1[i][j] = A[i][j] + ad[i][j];
            }
        }
        return new Matrix(ad1);
    }

    public Matrix subtract(Matrix matrix) {
        Dimension dimension = matrix.dimensions();
        if (dimension.width != cols || dimension.height != rows) {
            throw new IndexOutOfBoundsException();
        }
        double ad[][] = matrix.toArray();
        double ad1[][] = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ad1[i][j] = A[i][j] - ad[i][j];
            }
        }
        return new Matrix(ad1);
    }

    public Matrix premultiply(Matrix matrix) {
        Dimension dimension = matrix.dimensions();
        Dimension dimension1 = dimensions();
        double ad[][] = new double[dimension.height][dimension1.width];
        ad = arrayMultiply(matrix.toArray(), dimension, A, dimension1);
        return new Matrix(ad);
    }

    public Matrix postmultiply(Matrix matrix) {
        Dimension dimension = dimensions();
        Dimension dimension1 = matrix.dimensions();
        double ad[][] = new double[dimension.height][dimension1.width];
        ad = arrayMultiply(A, dimension, matrix.toArray(), dimension1);
        return new Matrix(ad);
    }

    private double[][] arrayMultiply(double ad[][],
                                     Dimension dimension,
                                     double ad1[][],
                                     Dimension dimension1) {
        int i = dimension.height;
        int j = dimension.width;
        int k = dimension1.height;
        int l = dimension1.width;
        if (j != k) {
            throw new IndexOutOfBoundsException();
        }
        double ad2[][] = new double[i][l];
        for (int i1 = 0; i1 < i; i1++) {
            for (int j1 = 0; j1 < l; j1++) {
                ad2[i1][j1] = 0.0D;
                for (int k1 = 0; k1 < j; k1++) {
                    ad2[i1][j1] += ad[i1][k1] * ad1[k1][j1];
                }
            }
        }
        return ad2;
    }

    public void scalarMult(double d) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A[i][j] *= d;
            }
        }
    }

    public Matrix transpose() {
        double ad[][] = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ad[j][i] = A[i][j];
            }
        }
        return new Matrix(ad);
    }

    public double elementAt(int i, int j) {
        if (i > rows || j > cols) {
            throw new IndexOutOfBoundsException();
        } else {
            return A[i - 1][j - 1];
        }
    }

    public void rowExchange(int i, int j) {
        for (int k = 0; k < cols; k++) {
            double d = A[i - 1][k];
            A[i - 1][k] = A[j - 1][k];
            A[j - 1][k] = d;
        }
    }

    public void rowMultiply(double d, int i) {
        for (int j = 0; j < cols; j++) {
            A[i - 1][j] *= d;
        }
    }

    public void rowAddMultiple(double d, int i, int j) {
        for (int k = 0; k < cols; k++) {
            A[j - 1][k] += d * A[i - 1][k];
        }
    }

}