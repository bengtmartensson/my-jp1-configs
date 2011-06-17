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
public class InputVariable {
    
    private char name;
    private int min;
    private int max;
    private String deflt;
    private int current;
    boolean initialized = false;
    
    @Override
    public String toString() {
        return name + " " + min + ".." + max + (deflt != null ? (":" + deflt) : "");
    }

    public InputVariable(char name, int min, int max, String deflt) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.deflt = deflt;
    }
    
    public boolean isOK(int x) {
        return min <= x && x <= max;
    }
    
    //public getValue
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InputVariable dev = new InputVariable('d', 0, 255, "255-s");
        System.out.println(dev);
        System.out.println(dev.isOK(-1));
        System.out.println(dev.isOK(0));
        System.out.println(dev.isOK(255));
        System.out.println(dev.isOK(256));
    }
}
