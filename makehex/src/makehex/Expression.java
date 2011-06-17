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
 * This class corresponds to Chapter 9.
 * An expression is evaluated during execution time with then valid bindings.
 *
 * @author Bengt Martensson
 */
public class Expression {
    
    private String bareExpression;
    
    public Expression(String s) {
        s = s.replaceAll("\\s", "");
        if (s.charAt(0) == '(' && s.endsWith(")"))
            bareExpression = s;
        else
            throw new IllegalArgumentException("Expression not surrounded by parethesis.");
    }
    
    @Override
    public String toString() {
        return bareExpression;
    }
    
    public int evaluate() {
        return evaluate(bareExpression);
    }
    
    public static int evaluate(String s) {
        int pos;
        pos = s.indexOf('|');
        if (pos >= 0)
            return evaluate(s.substring(0,pos)) | evaluate(s.substring(pos+1));
        
        pos = s.indexOf('^');
        if (pos >= 0)
            return evaluate(s.substring(0,pos)) ^ evaluate(s.substring(pos+1));
        
        return 0;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println(new Expression("(jfkdskfjs ( ( fjksdf)"));
        Expression ex = new Expression("(jfkdsk|fjs ( ( fjksdf)");
        System.out.println(ex.evaluate());
        
    }
}
