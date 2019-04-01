package net.jharris.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/java-regex/problem
 */
public class RegexChallenge {

    public Stream<Boolean> doChallenge(Supplier<InputStream> inputStreamSupplier) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamSupplier.get()));
            String line = null;
            var ipList = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                ipList.add(line);
            }

            //Pattern is as follows, repeated 4 times
            //Is it 0 or 1 in first position or none at all
            //Atleast one number, followed by a possible number
            //OR
            //2 followed by a 0-4 digit and a digit
            //OR
            //25 followed by a 0-5 digit
            var pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

            return ipList.stream().map(ip -> pattern.matcher(ip).matches());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
