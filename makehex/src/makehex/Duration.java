/*
Copyright (C) 2011 Bengt Martensson.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program. If not, see http://www.gnu.org/licenses/.
 */

package makehex;

/**
 * This class ...
 * Depends on its GeneralSpec, otherwise it is immutable.
 *
 * @author Bengt Martensson
 */

// TODO names in constructor string
public class Duration extends IRStreamItem {

    private DurationType durationType;
    
    private double us;
    private double time_periods = -1;
    private double time_units = -1;
    
    //private GeneralSpec generalSpec;
    
    public Duration(Protocol env, double time, DurationType dt) {
        super(env);
        us = time;
        durationType = dt;
    }
    
    public Duration(Protocol env, String str) {
        super(env);
        str = str.trim().toLowerCase();
        if (str.charAt(0) == '-' || str.charAt(0) == '^') {
            durationType = DurationType.gap;
            str = str.substring(1);
        } else
            durationType = DurationType.flash;
        if (str.endsWith("m")) {
            us = 1000*parseButLast(str);
        } else if (str.endsWith("u")) {
            us = parseButLast(str);
        } else if (str.endsWith("p")) {
            time_periods = parseButLast(str);
        } else {
            time_units = Double.parseDouble(str);
        }
    }
    
    private double parseButLast(String s) {
        return Double.parseDouble(s.substring(0, s.length()-1));
    }
    
    public double evaluate() throws ArithmeticException {
        if (time_periods != -1) {
            if (environment.getGeneralSpec().getFrequency() > 0) {
                return time_periods/environment.getGeneralSpec().getFrequency();
            } else {
                throw new ArithmeticException("Units in p and frequency == 0 do not go together.");
            }
        } else if (time_units != -1) {
            if (environment.getGeneralSpec().getUnit() > 0) {
                return time_units * environment.getGeneralSpec().getUnit();
            } else {
                throw new ArithmeticException("Relative units and unit == 0 do not go together.");
            }
        } else {
            return us;
        }
    }
    
    public DurationType getDurationType() {
        return durationType;
    }
    
    @Override
    public String toString() {
        return "evaluate() = " + evaluate() + ", us = " + us + ", time_periods = " + time_periods + ", time_units = " + time_units + ", DurationType = " + durationType + ", GeneralSpec: " + environment.getGeneralSpec();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //GeneralSpec gs = new GeneralSpec("[100k, 10000u]");
        Duration d = new Duration(new Protocol(), "-123u");
        /*System.out.println(d.getDurationType());
        d = new Duration(new Protocol(), "123u");
        System.out.println(d.getDurationType());
        System.out.println((new Duration(new Protocol(), "-1m")));
        System.out.println((new Duration(new Protocol(), "1m")));
        System.out.println((new Duration(new Protocol(), "1u")));
        System.out.println((new Duration(new Protocol(), "1p")));
        System.out.println((new Duration(new Protocol(), "1")));*/
    }
}
