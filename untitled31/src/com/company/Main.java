package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	// write your code here


        class InvalidMoveException extends Exception {}
        class InvalidBoardStateException extends RuntimeException {}

        class Board {
            private int n, n2;
            private char[] a;
            private static final char pchar[] = {'-', 'X', 'O'};

            public static char getPChar(int i) {
                return pchar[i];
            }

            public Board(int size) {
                n = size;
                n2 = n*n;
                a = new char[n2];
                for(int i=0; i<n2; ++i) {
                    a[i] = pchar[0];
                }
            }

            public int getSize() {return n;}

            public String toString() {
                return new String(a, 0, 3) + "\n" + new String(a, 3, 3) + "\n" + new String(a, 6, 3);
            }

            public char getCell(int i) {
                return a[i];
            }
            public char getCell(int i, int j) {
                return a[i*n + j];
            }

            public void setCell(int i, char ch) throws InvalidMoveException {
                if(i >= n2 || a[i] != pchar[0] || (ch != pchar[1] && ch != pchar[2])) {
                    throw new InvalidMoveException();
                }
                else {
                    a[i] = ch;
                }
            }

            /**
             Returns 0 if player 1 wins, 1 if player 2 wins, -1 if no one wins.
             Throws InvalidBoardStateException if there are 2 winners.
             */
            public int getWinner() {
                char ch = getWinnerChar();
                if(ch == pchar[0]) {
                    return -1;
                }
                else if(ch == pchar[1]) {
                    return 0;
                }
                else {
                    return 1;
                }
            }

            /**
             Returns pchar[1] if player 1 is winner, pchar[2] if player 2 is winner and pchar[0] if tie.
             Throws InvalidBoardStateException if there are 2 winners.
             */
            public char getWinnerChar() {
                char result = pchar[0];

                // check rows
                for(int i=0; i<n; ++i) {
                    char ch = getCell(i, 0);
                    if(ch != pchar[0]) {
                        boolean winningRow = true;
                        for(int j=1; j<n; ++j) {
                            if(getCell(i, j) != ch) {
                                winningRow = false;
                            }
                        }
                        if(winningRow) {
                            if(result != pchar[0] && result != ch) {
                                throw new InvalidBoardStateException();
                            }
                            result = ch;
                        }
                    }
                }

                // check columns
                for(int j=0; j<n; ++j) {
                    char ch = getCell(0, j);
                    if(ch != pchar[0]) {
                        boolean winningCol = true;
                        for(int i=1; i<n; ++i) {
                            if(getCell(i, j) != ch) {
                                winningCol = false;
                            }
                        }
                        if(winningCol) {
                            if(result != pchar[0] && result != ch) {
                                throw new InvalidBoardStateException();
                            }
                            result = ch;
                        }
                    }
                }

                // check diagonals
                char ch = getCell(0, 0);
                if(ch != pchar[0]) {
                    boolean winningDiag = true;
                    for(int i=1; i<n; ++i) {
                        if(ch != getCell(i, i)) {
                            winningDiag = false;
                        }
                    }
                    if(winningDiag) {
                        if(result != pchar[0] && result != ch) {
                            throw new InvalidBoardStateException();
                        }
                        result = ch;
                    }
                }
                ch = getCell(n-1, 0);
                if(ch != pchar[0]) {
                    boolean winningDiag = true;
                    for(int i=1; i<n; ++i) {
                        if(ch != getCell(n-1-i, i)) {
                            winningDiag = false;
                        }
                    }
                    if(winningDiag) {
                        if(result != pchar[0] && result != ch) {
                            throw new InvalidBoardStateException();
                        }
                        result = ch;
                    }
                }

                return result;
            }
        }

        class n
        {
            private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            public static int readInt() throws IOException {
                return Integer.parseInt(br.readLine());
            }

            public static void main(String[] args) throws IOException
            {
                final int n = 3;
                int activePlayer = 0;
                Board board = new Board(n);
                int turni=0;
                for(turni=0; turni<n*n; ++turni) {
                    System.out.println();
                    System.out.println(board.toString());

                    while(true) {
                        System.out.print("" + board.getPChar(activePlayer+1) + "> ");
                        int index = readInt();
                        try {
                            board.setCell(index, board.getPChar(activePlayer+1));
                            break;
                        }
                        catch(InvalidMoveException e) {
                            System.out.println("Invalid move.");
                        }
                    }

                    int winner = board.getWinner();
                    if(winner >= 0) {
                        System.out.println("Player " + board.getPChar(activePlayer+1) + " wins.");
                        break;
                    }

                    activePlayer = 1 - activePlayer;
                }
                if(turni == n*n) {
                    System.out.println("Tie");
                }
            }
        }
    }
}
