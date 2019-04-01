package net.jharris.lambdas;


import com.codepoetics.protonpack.StreamUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChallengeTest {

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

            var challenge = new LambdaChallenge();
            var valueStream = challenge.doChallenge(() -> valuesStream);
            var testStream = results.stream();

            StreamUtils.zip(valueStream, testStream, ResultsComparison::new)
                    .forEach(resultsComparison -> Assert.assertTrue(resultsComparison.getErrorMessage(), resultsComparison.isPass()));
        } finally {
            valuesStream.close();
            resultsStream.close();
        }
    }

    class ResultsComparison {
        private final String test1;
        private final String test2;

        public ResultsComparison(String test1, String test2) {
            this.test1 = test1;
            this.test2 = test2;
        }

        public boolean isPass() {
            return test1.equals(test2);
        }

        private String getErrorMessage() {
            return "Was expecting " + test1 + " but got " + test2;
        }
    }
}
