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
public class RepeatMarker {
    
    int min = 1;
    int max = 1;
    
    public RepeatMarker(String str) {
        str = str.trim();
        if (str == null || str.isEmpty()) {
            min = 1;
            max = 1;
        } else if (str.charAt(0) == '*') {
            min = 0;
            max = Integer.MAX_VALUE;
        } else if (str.charAt(0) == '+') {
            min = 1;
            max = Integer.MAX_VALUE;
        } else if (str.endsWith("+")) {
            min = Integer.parseInt(str.substring(0, str.length() - 1));
            max = Integer.MAX_VALUE;
        } else {
            try {
            min = Integer.parseInt(str);
            max = min;
            } catch (Exception e) {
                
            }
        }
    }
    
    public RepeatMarker(char ch) {
        this(Character.toString(ch));
    }
    
    @Override
    public String toString() {
        return
                (min == 1 && max == 1) ? ""
                : (min == 0 && max == Integer.MAX_VALUE) ? "*"
                : (min == 1 && max == Integer.MAX_VALUE) ? "+"
                : (min == max) ? Integer.toString(min)
                : (max == Integer.MAX_VALUE) ? Integer.toString(min) + "+"
                : "??";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println((new RepeatMarker("")));
        System.out.println((new RepeatMarker('*')));
        System.out.println((new RepeatMarker("*")));
        System.out.println((new RepeatMarker('+')));
        System.out.println((new RepeatMarker("1+")));
        System.out.println((new RepeatMarker("0+")));
        System.out.println((new RepeatMarker("7")));
        System.out.println((new RepeatMarker("7+")));
        System.out.println((new RepeatMarker("\t7+   ")));
    }
}
