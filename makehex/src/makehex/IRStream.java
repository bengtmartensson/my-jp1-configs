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

import java.util.ArrayList;

/**
 * This class ...
 *
 * @author Bengt Martensson
 */
public class IRStream extends IRStreamItem {

    //@Override
    boolean stringOk(String s) {
        return s.startsWith("(");
    }
    
    private String[] str;
    
    ArrayList<IRStreamItem> irstreamItems = new ArrayList<IRStreamItem>();
    
    private RepeatMarker repeatMarker;
    
    public IRStream(String s) {
        if (s == null || s.isEmpty()) {
            str = new String[0];
            return;
        }
        s = s.replaceAll("\\s", "");
        String bareIrstream;
        if (s.matches("^\\(.*\\)[\\d\\*\\+]*$")) {
            repeatMarker = new RepeatMarker(s.substring(s.lastIndexOf(')') + 1));
            bareIrstream = s.substring(1, s.lastIndexOf(')'));
        } else
            bareIrstream = s;
            
        str = bareIrstream.split(",");
        
        int stringIndex = 0;
        do {
            int next;
            if (bareIrstream.charAt(stringIndex) == '<') {
                // bitspec + irstream
                next = bareIrstream.indexOf('>', stringIndex);
                next = IRPUtils.searchMatchingRightPara(next+1, bareIrstream);
                next = bareIrstream.indexOf(',', next) - 1;
                //String chunk = bareIrstream.substring(stringIndex, next);
            } else if (bareIrstream.charAt(stringIndex) == '(') {
                // irstream or expression
                next = IRPUtils.searchMatchingRightPara(stringIndex, bareIrstream);
                next = bareIrstream.indexOf(',', next) - 1;
            } else {
                // everything else
                next = bareIrstream.indexOf(',', stringIndex) - 1;
                //String chunk = bareIrstream.substring(stringIndex, next);
                //System.out.println(bar)
            }
            String chunk = next < 0 ? bareIrstream.substring(stringIndex)
                    : bareIrstream.substring(stringIndex, next+1);
            stringIndex = next < 0 ? Integer.MAX_VALUE : next+2;
            System.out.println(chunk);
            
        } while (stringIndex < bareIrstream.length()-1);
        
    }
    
    @Override
    public String toString() {
        return str.length == 0 ? "<Empty>" : ( "(" + IRPUtils.join(str, '$') + ")" + repeatMarker);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //(16,-8,D:8,S:8,F:8,~F:8,1,-78,(16,-4,1,-173)*)
        System.out.println(new IRStream(""));
        System.out.println(new IRStream("(16,-4,1,       -173) *    "));
        //System.out.println(new IRStream("(16,-4,1,-173)"));
        System.out.println(new IRStream("(8,-4, 2:8,32:8,D:8,S:8,F:8,(D^S^F):8,1,-173)+")); 
        System.out.println(new IRStream(" ( 16,-8,<1,2|3,4>(16,-4,1,-173)*,D:8,S: 8,F:8,~F:8,1,-78,(16,-4,1,-173)*)  "));
    }

    
}
