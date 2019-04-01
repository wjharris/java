package net.jharris.lambdas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/java-lambda-expressions/problem
 */
public class LambdaChallenge {

    private IntFunction<String> lineReaderFunc(final BufferedReader reader) {
        return (int i) -> {
            try {
                return reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                return "1 1";
            }
        };
    }

    private Predicate<Integer> isOdd = value -> value % 2 != 0;
    private Predicate<Integer> isPrime = value -> {
        if (value == 2 || value == 3) {
            return true;
        }
        if (value % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(value) + 1;
        for (var i = 3; i < sqrt; i += 2) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    };
    private Predicate<Integer>  isPalindrome = value -> {
        var strVal = String.valueOf(value);
        var length = strVal.length();
        if (isOdd.test(length)) {
            int halfWay = Math.round(length/2);
            return strVal.substring(0, halfWay).equals(strVal.substring(halfWay + 1));
        } else {
            return strVal.substring(0, length/2).equals(strVal.substring(length/2));
        }
    };

    private Function<Boolean, String> isOddMessage = bool -> bool ? "ODD" : "EVEN";
    private Function<Boolean, String> isPrimeMessage = bool -> bool ? "PRIME" : "COMPOSITE";
    private Function<Boolean, String> isPalindromeMessage = bool -> bool ? "PALINDROME" : "NOT PALINDROME";

    public Stream<String> doChallenge(Supplier<InputStream> inputStreamSupplier) {
        try {
            InputStream stream = inputStreamSupplier.get();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            int numberOfLines = Integer.valueOf(reader.readLine());
            return IntStream.range(0, numberOfLines)
                    .mapToObj(lineReaderFunc(reader))
                    .map(line -> line.split(" "))
                    .map(values -> Arrays.stream(values).map(Integer::valueOf)
                            .collect(Collectors.toList()))
                    .map(list -> new Condition(list.get(0), list.get(1)))
                    .map(Condition::toResolver)
                    .map(ConditionResolver::resolve);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



    class Condition {
        private static final int EVEN_ODD = 1;
        private static final int PRIME = 2;
        private static final int PALINDROME = 3;

        private int type;
        private int value;

        public Condition(int type, int value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public int getValue() {
            return value;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public ConditionResolver toResolver() {
            switch (type) {
                case EVEN_ODD:
                    return new ConditionResolver(this, isOdd, isOddMessage);
                case PRIME:
                    return new ConditionResolver(this, isPrime, isPrimeMessage);
                case PALINDROME:
                    return new ConditionResolver(this, isPalindrome, isPalindromeMessage);
                default:
                    return new ConditionResolver(this, val -> true, bool -> "ERROR");
            }
        }
    }

    class ConditionResolver {
        private final Condition condition;
        private final Predicate<Integer> predicate;
        private final Function<Boolean, String> resolver;


        public ConditionResolver(Condition condition, Predicate<Integer> predicate, Function<Boolean, String> resolver) {
            this.condition = condition;
            this.predicate = predicate;
            this.resolver = resolver;
        }

        public Predicate<Integer> getPredicate() {
            return predicate;
        }

        public Function<Boolean, String> getResolver() {
            return resolver;
        }

        public Condition getCondition() {
            return condition;
        }

        public String resolve() {
            return resolver.apply(predicate.test(condition.getValue()));
        }
    }

}
