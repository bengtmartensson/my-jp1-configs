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
public class Bitfield extends IRStreamItem {

    //static IRPEnvironment environment;
    
    boolean complement;
    boolean reverse;
    int input;
    int width = 32;
    int skip = 0;
    
    private int evaluatePrimaryItem(String s, int deflt) {
        return 
                (s == null || s.isEmpty()) ? deflt
                : Character.isDigit(s.charAt(0)) ? Integer.parseInt(s)
                : (s.charAt(0) == '(') ? evaluateExpression(s)
                : evaluateName(s);
    }
    
    private int evaluateExpression(String s) {
        return 0;
    }
    
    private int evaluateName(String s) {
        return 0;
    }
    
    public Bitfield(String str) {
        String[] s = str.toLowerCase().split(":");
        String first = s[0].trim();
        String second = s[1].trim();
        String third = s.length >= 3 ? s[2].trim() : null;
        
        if (first.charAt(0) == '~') {
            complement = true;
            first = first.substring(1);
        }
        input = evaluatePrimaryItem(first, 0);
        if (second.charAt(0) == '-') {
            reverse = true;
            second = second.substring(1);
        }
        width = evaluatePrimaryItem(second, 32);
        skip = evaluatePrimaryItem(third, 0);
    }
    
    @Override
    public String toString() {
        return (complement ? "~" : "") + input + ":" + (reverse ? "-" : "") + width + ":" + skip; 
    }
    
    public String evaluateString() {
        int x = input >> skip;
        if (complement)
            x = ~x;
        String padding = x >= 0 ? "00000000000000000000000000000000" : "11111111111111111111111111111111";

        String s = Integer.toBinaryString(x);
        if (s.length() > width)
            s = s.substring(s.length() - width);
        else if (s.length() < width)
            s = padding.substring(0, width - s.length()) + s;

        if (reverse)
            s = (new StringBuffer(s)).reverse().toString();
        return s;
    }
    
    public int evaluate() {
        String s = evaluateString();
        return Integer.parseInt(s, 2);
    }
    
    private static void test(String s) {
        Bitfield bf = new Bitfield(s);
        System.out.println(bf + "######\t" + bf.evaluateString() + "***" + bf.evaluate());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test("244:8");
        test("~244:8");
        test("244:-8");
        test("~244:6:2");
        test("244:-6:2");
        test("~244:-6:2");
        test("1027:4");
        test("~1027:4");
        test("1025:-4");
        test("1027:4:8");
        
    }
}
