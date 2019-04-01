package net.jharris.regex;

import com.codepoetics.protonpack.StreamUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RegexChallengeText {

    @Test
    public void testChallenge() throws Exception {
        InputStream valuesStream = this.getClass().getClassLoader().getResourceAsStream("input.txt");
        InputStream resultsStream = this.getClass().getClassLoader().getResourceAsStream("results.txt");

        try {

            var resultsReader = new BufferedReader(new InputStreamReader(resultsStream));
            String line;
            var results = new ArrayList<String>();
            while ((line = resultsReader.readLine()) != null) {
                results.add(line);
            }

            var challenge = new RegexChallenge();
            var valueStream = challenge.doChallenge(() -> valuesStream);
            var testStream = results.stream().map(Boolean::valueOf);

            StreamUtils.zip(valueStream, testStream, (bool1, bool2) -> bool1 == bool2)
                    .forEach(resultsComparison -> Assert.assertTrue(resultsComparison));
        } finally {
            valuesStream.close();
            resultsStream.close();
        }
    }

}
