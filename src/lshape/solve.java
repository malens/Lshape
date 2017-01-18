package lshape;

import net.ericaro.surfaceplotter.surface.*;
import org.apache.commons.math3.linear.*;
/**
 * Created by malens on 2017-01-18.
 */
public class solve {


    public static void solver(int n, ArraySurfaceModel output)
    {
        RealVector vec = solveMatrix(n);
        float[][] v = new float[2 * n + 1][2 * n + 1];

        int mi = 0;
        for (int i= 0; i<=n; i++) {
            for (int j =0; j<= 2 * n; j++) {
                v[i][j] = (float)(vec.getEntry(mi++));
            }
        }
        for (int i=n + 1; i<=2 * n; i++)
            for (int j=0; j<=2 * n; j++)
                if (j<n)
                    v[i][j]=(float)0.0;
                else
                    v[i][j] = (float)vec.getEntry(mi++);

        output.setValues(-1f, 1f, -1f, 1f, 2 * n + 1, v, null);
    }


    public static RealVector solveMatrix(int n){
        return new LUDecomposition(matrixA(n)).getSolver().solve(vectorB(n));
    }


    private static Array2DRowRealMatrix matrixA (int n)
    {
        int size = (n+1) * (3 * n + 1);
        Array2DRowRealMatrix globalMatrix = new Array2DRowRealMatrix(size, size);

        for (int i = 0; i<(n*(2*n+1)); i++)
        {
            if (i%(2*n+1)!=2*n)
            {
                int _4 = i;
                int _3 = i+1;
                int _2 = i+2*n + 2;
                int _1 = _2 - 1;
                mapLocalToGlobal (globalMatrix, _1, _2, _3, _4);
            }

        }
        for (int i = n*(2*n+1)+n; i<size-n-1; i++)
        {
            if (i%(n+1)!=n)
            {
                int _4 = i;
                int _3 = i+1;
                int _2 = i+n+2;
                int _1 = i+n+1;
                mapLocalToGlobal(globalMatrix, _1, _2, _3, _4);
            }
        }

        for (int i = n*(2*n+1); i<=n*(2*n+1)+n; i++)
        {
            for (int j = 0; j<=size-1; j++)
                globalMatrix.setEntry(i, j, 0.0);
            globalMatrix.setEntry(i, i, 1.0);
        }
        for (int i = (n+1)*(2*n+1); i<=size-n; i+=n+1)
        {
            for (int j = 0; j<=size-1; j++)
                globalMatrix.setEntry(i,j, 0.0);
            globalMatrix.setEntry(i,i,1.0);
        }

        return globalMatrix;
    }

    private static void mapLocalToGlobal(Array2DRowRealMatrix m, int _1, int _2, int _3, int _4)
    {
        m.setEntry(_1, _1, m.getEntry(_1,_1)+localMatrix[0][0]);
        m.setEntry(_1, _2, m.getEntry(_1,_2)+localMatrix[0][1]);
        m.setEntry(_1, _3, m.getEntry(_1,_3)+localMatrix[0][2]);
        m.setEntry(_1, _4, m.getEntry(_1,_4)+localMatrix[0][3]);
        m.setEntry(_2, _1, m.getEntry(_2,_1)+localMatrix[1][0]);
        m.setEntry(_2, _2, m.getEntry(_2,_2)+localMatrix[1][1]);
        m.setEntry(_2, _3, m.getEntry(_2,_3)+localMatrix[1][2]);
        m.setEntry(_2, _4, m.getEntry(_2,_4)+localMatrix[1][3]);
        m.setEntry(_3, _1, m.getEntry(_3,_1)+localMatrix[2][0]);
        m.setEntry(_3, _2, m.getEntry(_3,_2)+localMatrix[2][1]);
        m.setEntry(_3, _3, m.getEntry(_3,_3)+localMatrix[2][2]);
        m.setEntry(_3, _4, m.getEntry(_3,_4)+localMatrix[2][3]);
        m.setEntry(_4, _1, m.getEntry(_4,_1)+localMatrix[3][0]);
        m.setEntry(_4, _2, m.getEntry(_4,_2)+localMatrix[3][1]);
        m.setEntry(_4, _3, m.getEntry(_4,_3)+localMatrix[3][2]);
        m.setEntry(_4, _4, m.getEntry(_4,_4)+localMatrix[3][3]);
    }

    private static double g (double x, double y)
    {
        double r = Math.sqrt(x * x + y * y);
        double cos = Math.cos(Math.atan(y / x) - Math.PI / 4);
        return Math.cbrt(r * r) * Math.cbrt(cos * cos);
    }


    private static RealVector vectorB (int n)
    {
        int size = (n+1) * (3 * n + 1);
        RealVector vector = new ArrayRealVector(size);
        double h = 1.0/n;

        vector.setEntry(0, 0.5 * h * (g(-1.0 + h, 1.0) + g(-1.0, 1.0 - h)));

        for (int i = 1; i<n; i++)
            vector.setEntry(i*(2*n+1), 0.5 * h * (g(-1.0, 1.0 - (i - 1.0) * h) + g(-1.0, 1.0 - (i + 1.0) * h)));


        for (int i = 1; i < 2 * n; i++)
            vector.setEntry(i, 0.5 * h * (g(-1.0 + (i - 1.0) * h, 1.0) + g(-1.0 + (i + 1.0) * h, 1.0)));

        vector.setEntry(2*n,0.5 * h * (g(1.0 - h, 1.0) + g(1.0, 1.0 - h)));

        for (int i = 1; i<=n; i++)
            vector.setEntry((i + 1) * (2 * n + 1) - 1, 0.5 * h * (g(1.0, 1.0 - (i - 1.0) * h) + g(1.0, 1.0 - (i + 1.0) * h)));


        for (int i = n + 1; i<2 * n; i++)
            vector.setEntry((n + 1) * (2 * n + 1) - 1 + (i - n) * (n + 1), 0.5 * h * (g(1.0, 1.0 - (i - 1.0) * h) + g(1.0, 1.0 - (i + 1.0) * h)));


        vector.setEntry(size-1, 0.5 * h * (g(1.0 - h, -1.0) + g(1.0, -1.0 + h)));

        for (int i = 1; i<n; i++)
            vector.setEntry((size - (n + 1)) + i,0.5 * h * (g(((i - 1.0) * h), -1.0) + g((i + 1.0) * h, -1.0)) );

        return vector;
    }


    private static double localMatrix [][]={
            {2.0 / 3.0, -1.0 / 6.0, -1.0 / 3.0, -1.0 / 6.0},
            {-1.0 / 6.0, 2.0 / 3.0, -1.0 / 6.0, -1.0 / 3.0},
            {-1.0 / 3.0, -1.0 / 6.0, 2.0 / 3.0, -1.0 / 6.0},
            {-1.0 / 6.0, -1.0 / 3.0, -1.0 / 6.0, 2.0 / 3.0}
    };

}
