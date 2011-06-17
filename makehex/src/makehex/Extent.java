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
 *
 * @author Bengt Martensson
 */
public class Extent extends Duration {
    
    public Extent(Protocol env, String str) {
        super(env, str); // str.charAt(0) must be '^'
    }
    
    //public double evaluate(double elapsed) {
    //    return evaluate() - elapsed;
    //}
    
    @Override
    public double evaluate() {
        return super.evaluate() - this.environment.timeElapsed();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //GeneralSpec gs = new GeneralSpec("[100k, 10000u]");
        Extent d = new Extent(new Protocol(), "^123u");
        System.out.println(d.getDurationType());
        System.out.println(d);
        System.out.println(d.evaluate());
        System.out.println(d.evaluate());
    }
}
