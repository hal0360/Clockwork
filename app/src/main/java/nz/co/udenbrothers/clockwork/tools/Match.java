package nz.co.udenbrothers.clockwork.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 26/08/2017.
 */

public class Match {
    public static boolean mail(String s){
        Matcher m = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*$").matcher(s);
        return m.matches( );
    }
}
