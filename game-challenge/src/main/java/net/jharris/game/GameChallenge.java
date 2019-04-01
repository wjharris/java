package net.jharris.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/java-1d-array/problem
 */
public class GameChallenge {

    public Stream<Boolean> doChallenge(Supplier<InputStream> inputStreamSupplier) {
        try {
            var reader = new BufferedReader(new InputStreamReader(inputStreamSupplier.get()));
            var numberOfGames = Integer.valueOf(reader.readLine());
            var gameBoardList = new ArrayList<GameBoard>();
            IntStream.range(0, numberOfGames).forEach(i -> {
                try {
                    var gameDef = reader.readLine();
                    var gameBoard = reader.readLine();
                    var gameDefValules = Arrays.stream(gameDef.split(" "))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()).toArray(new Integer[0]);
                    var board = Arrays.stream(gameBoard.split(" "))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()).toArray(new Integer[0]);
                    gameBoardList.add(new GameBoard(gameDefValules[0], gameDefValules[1], board));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    gameBoardList.add(new GameBoard(3, 1, new Integer[]{0, 1, 1}));
                }
            });

            return gameBoardList.stream().map(GameBoard::playIt);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    class GameBoard {
        private final int n;
        private final int leap;
        private final Integer[] board;

        public GameBoard(int n, int leap, Integer[] board) {
            this.n = n;
            this.leap = leap;
            this.board = board;
        }

        public boolean playIt() {
            int index = 0;
            while (index + leap < n && index + 1 < n) {
                if (board[index + leap] == 0) {
                    index = index + leap;
                } else if (board[index + 1] == 0) {
                    index = index + 1;
                } else {
                    System.out.println("Game could not be completed.");
                    return false;
                }
            }
            System.out.println("Game was completed.");
            return true;
        }
    }
}
