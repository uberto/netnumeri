package com.netnumeri.shared.finance.finpojo;


import com.netnumeri.shared.finance.beans.FinConstants;

import java.io.Serializable;

public class Parameters implements FinConstants, Serializable {
    public int nParameters;       // number of parameters
    public double[] parameter;        //[nParameters] array of parameters
    public String[] parameterName;    //[nParameters] array of names of parameters
    public int[] parameterType;    //[nParameters] array of parameter types
    public double[] lowerBound;   //[nParameters] array of lower bounds
    public double[] upperBound;   //[nParameters] array of upper bounds
    public boolean[] isFixed; //[nParameters] array of fixed flags

    public Parameters() {
        parameter = new double[nParameters];
        parameterType = new int[nParameters];
        parameterName = new String[nParameters];
        lowerBound = new double[nParameters];
        upperBound = new double[nParameters];
        isFixed = new boolean[nParameters];
        nParameters = 0;
    }

    public Parameters(double Param1) {
        nParameters = 0;
        setNParam(1);
        parameter[0] = Param1;
    }

    public Parameters(double Param1, double Param2) {
        nParameters = 0;
        setNParam(2);
        parameter[0] = Param1;
        parameter[1] = Param2;
    }

    public Parameters(double Param1, double Param2, double Param3) {
        nParameters = 0;
        setNParam(3);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4) {
        nParameters = 0;
        setNParam(4);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5) {
        nParameters = 0;
        setNParam(5);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5, double Param6) {
        nParameters = 0;
        setNParam(6);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
        parameter[5] = Param6;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5, double Param6, double Param7) {
        nParameters = 0;
        setNParam(7);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
        parameter[5] = Param6;
        parameter[6] = Param7;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5, double Param6, double Param7, double Param8) {
        nParameters = 0;
        setNParam(8);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
        parameter[5] = Param6;
        parameter[6] = Param7;
        parameter[7] = Param8;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5, double Param6, double Param7, double Param8, double Param9) {
        nParameters = 0;
        setNParam(9);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
        parameter[5] = Param6;
        parameter[6] = Param7;
        parameter[7] = Param8;
        parameter[8] = Param9;
    }

    public Parameters(double Param1, double Param2, double Param3, double Param4, double Param5, double Param6, double Param7, double Param8, double Param9, double Param10) {
        nParameters = 0;
        setNParam(10);
        parameter[0] = Param1;
        parameter[1] = Param2;
        parameter[2] = Param3;
        parameter[3] = Param4;
        parameter[4] = Param5;
        parameter[5] = Param6;
        parameter[6] = Param7;
        parameter[7] = Param8;
        parameter[8] = Param9;
        parameter[9] = Param10;
    }

    public void setNParam(int NParam) {
        if (NParam != nParameters) {
            double[] NewParam = new double[NParam];
            int[] NewParamType = new int[NParam];
            String[] NewParamName = new String[NParam];
            double[] NewLowerBound = new double[NParam];
            double[] NewUpperBound = new double[NParam];
            boolean[] NewIsParamFixed = new boolean[NParam];
            int NewNParam = Math.min(nParameters, NParam);
            if (nParameters == 0) {
                for (int i = 0; i < NewNParam; i++) {
                    NewParam[i] = parameter[i];
                    NewParamType[i] = parameterType[i];
                    NewParamName[i] = parameterName[i];
                    NewLowerBound[i] = lowerBound[i];
                    NewUpperBound[i] = upperBound[i];
                    NewIsParamFixed[i] = isFixed[i];
                }
            }
            parameter = NewParam;
            parameterType = NewParamType;
            parameterName = NewParamName;
            lowerBound = NewLowerBound;
            upperBound = NewUpperBound;
            isFixed = NewIsParamFixed;
            for (int i = nParameters; i < NParam; i++) {
                parameterType[i] = kFloat;
                parameterName[i] = "";
                lowerBound[i] = 1e-99;
                upperBound[i] = 1e+99;
                isFixed[i] = false;
            }
            nParameters = NParam;
        }
    }

    public int getNParam() {
        return nParameters;
    }

    public double getParam(int NParam) {
        return parameter[NParam];
    }

    public int getParamType(int NParam) {
        return parameterType[NParam];
    }

    public String getParamName(int NParam) {
        return parameterName[NParam];
    }

    public double getLowerBound(int NParam) {
        return lowerBound[NParam];
    }

    public double getUpperBound(int NParam) {
        return upperBound[NParam];
    }

    public boolean isParamFixed(int NParam) {
        return isFixed[NParam];
    }

    public void setParam(int NParam, double Param) {
        // set parameter and type
        if (NParam + 1 > nParameters) {
            setNParam(NParam + 1);
        }
        parameter[NParam] = Param;
    }

    public void setParamName(int NParam, String ParamName) {
        // set name
        if (NParam > nParameters - 1) {
            throw new IllegalStateException("Param index out of bounds\n");
        } else {
            parameterName[NParam] = ParamName;
        }
    }

    public void setLowerBound(int NParam, double Bound) {
        // set lower bound
        if (NParam > nParameters - 1) {
            throw new IllegalStateException("Param index out of bounds\n");
        } else {
            lowerBound[NParam] = Bound;
        }
    }

    public void setUpperBound(int NParam, double Bound) {
        // set upper bound
        if (NParam > nParameters - 1) {
            throw new IllegalStateException("Param index out of bounds\n");
        } else {
            upperBound[NParam] = Bound;
        }
    }

    public void setParamBounds(int NParam, double LowerBound, double UpperBound) {
        // set lower and upper bounds
        if (NParam > nParameters - 1) {
            throw new IllegalStateException("Param index out of bounds");
        } else {
            lowerBound[NParam] = LowerBound;
            upperBound[NParam] = UpperBound;
        }
    }

    public void fixParam(int NParam) {
        fixParam(NParam, true);
    }

    public void fixParam(int NParam, boolean Fixed) {
        // Fix param
        if (NParam > nParameters - 1) {
            throw new IllegalStateException("Param index out of bounds\n");
        } else {
            isFixed[NParam] = Fixed;
        }
    }

    public int getNFixedParam() {
        // Return number of fixed parameters
        int NFixedParam = 0;
        for (int i = 0; i < nParameters; i++) {
            if (isParamFixed(i)) {
                NFixedParam++;
            }
        }
        return NFixedParam;
    }

    public double getMinLowerBound() {
        // Return minimal lower bound
        double Bound = lowerBound[0];
        for (int i = 1; i < nParameters; i++) {
            Bound = Math.min(Bound, lowerBound[i]);
        }
        return Bound;
    }

    public double getMaxLowerBound() {
        // Return maximal lower bound
        double Bound = lowerBound[0];
        for (int i = 1; i < nParameters; i++) {
            Bound = Math.max(Bound, lowerBound[i]);
        }
        return Bound;
    }

    public double getMinUpperBound() {
        // Return minimal upper bound
        double Bound = upperBound[0];
        for (int i = 1; i < nParameters; i++) {
            Bound = Math.min(Bound, upperBound[i]);
        }
        return Bound;
    }

    public double getMaxUpperBound() {
        // Return maximal upper bound
        double Bound = upperBound[0];
        for (int i = 1; i < nParameters; i++) {
            Bound = Math.max(Bound, upperBound[i]);
        }
        return Bound;
    }

}
