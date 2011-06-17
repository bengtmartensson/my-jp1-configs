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
public class BitSpec extends IRStreamItem {
    
    private IRStream irStream;
    
    // Number of bits encoded
    private int noBits;
    
    private IRStream[] bitCodes;
    
    private static int computeNoBits(int n) {
        int x = n-1;
        int m;
        for (m = 0; x != 0; m++)
            x >>= 1;
        return m;
    }
    
    public BitSpec(String str, IRStream irs) {
        irStream = irs;
        str = str.replaceAll("[<>]", "");
        String[] s = str.split("\\|");
        bitCodes = new IRStream[s.length];
        for (int i = 0; i < s.length; i++) {
            bitCodes[i] = new IRStream(s[i]);
        }
        noBits = computeNoBits(s.length);
    }
    
    public BitSpec(String str) {
        this(str, null);
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < bitCodes.length; i++)
            s += (i > 0 ? "; " : "") + "bitCodes[" + i + "]=" + bitCodes[i];
        return s;
    }
    
    public int getNoBits() {
        return noBits;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BitSpec bitSpec = new BitSpec("<-1,2|3,-4>");
        System.out.println(bitSpec);
        
        System.out.println(computeNoBits(2));
        System.out.println(computeNoBits(3));
        System.out.println(computeNoBits(4));
        System.out.println(computeNoBits(5));
    }
}
