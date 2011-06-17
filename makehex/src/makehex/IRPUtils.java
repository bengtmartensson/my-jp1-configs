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
public class IRPUtils {
    
    public static String join(String[] s, String separator) {
        if (s == null || s.length == 0)
            return "";
        
        String res = s[0];
        for (int i = 1; i < s.length; i++)
            res += separator + s[i];
        
        return res;        
    }
    
    public static String join(String[] s, char separator) {
        return join(s, Character.toString(separator));
    }
    
    /**
     * Searches to the next matching right parenthesis. 
     * 
     * @param from
     * @param s
     * @return position of right parenthesis.
     */
    public static int searchMatchingRightPara(int from, String s) {
        if (s.charAt(from) == '(')
            from++;
        int left = s.indexOf('(', from);
        int right = s.indexOf(')', from);
        
        return (left == -1 || right < left) ? right
                : searchMatchingRightPara(searchMatchingRightPara(left, s)+1, s);
                    
    }
    
    private static void test(int from, String s) {
        System.out.println(s.substring(from));
        System.out.println(s.substring(searchMatchingRightPara(from, s)));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test(0, "(16,-8,D:8,S:8,F:8,~F:8,1,-78,(16,-4,1,-173)*)");
        test(1, "(16,-8,D:8,(S:8),F:8,~F:8,1,-78,(16,-4,1,-173)*)");
        test(35, "(16,-8,D:8,S:8,F:8,~F:8,1,-78,(16,-4,1,-173)*)");
    }
}
