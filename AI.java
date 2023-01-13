package connect4;

import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class AI {
	
	public String checkWin(String[][] board) {
		// repeat from game since it's structured differently in game and I have to generate another board using notation
		// check vertical wins
		for (int col=0; col<7; col++) {
			for (int row=0; row<3; row++) {
				if ((board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col]).equals("oooo")) {return "o";} 
				else if ((board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col]).equals("xxxx")) {return "x";}
			}
		}
		
		// check horizontal wins
		for (int row=0; row<6; row++) {
			for (int col=0; col<4; col++) {
				if ((board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3]).equals("oooo")) {return "o";}
				else if ((board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3]).equals("xxxx")) {return "x";}
			}
		}
		
		// diagonals
		for (int i=0; i<3; i++) {
			for (int j=0; j<4; j++) {
				if ((board[i][j]+board[i+1][j+1]+board[i+2][j+2]+board[i+3][j+3]).equals("oooo")) {return "o";}
				else if ((board[i][j]+board[i+1][j+1]+board[i+2][j+2]+board[i+3][j+3]).equals("xxxx")) {return "x";}
				else if ((board[5-i][j]+board[4-i][j+1]+board[3-i][j+2]+board[2-i][j+3]).equals("oooo")) {return "o";}
				else if ((board[5-i][j]+board[4-i][j+1]+board[3-i][j+2]+board[2-i][j+3]).equals("xxxx")) {return "x";}
			}
		}
		return null;
	}
	
	public String[][] generateFromNotation(String notation) {
		String[][] board = {{".", ".", ".", ".", ".", ".", "."}, 
							{".", ".", ".", ".", ".", ".", "."},
							{".", ".", ".", ".", ".", ".", "."},
							{".", ".", ".", ".", ".", ".", "."},
							{".", ".", ".", ".", ".", ".", "."},
							{".", ".", ".", ".", ".", ".", "."}};
		// generates a board object from notation
		for (int i=0; i<notation.length(); i++) {
			for (int j=0; j<6; j++) {
				try {
					if (board[5-j][Integer.parseInt(String.valueOf(notation.charAt(i)))] == ".") {
						if (i%2 == 0) {
							board[5-j][Integer.parseInt(String.valueOf(notation.charAt(i)))] = "o";
						} else {
							board[5-j][Integer.parseInt(String.valueOf(notation.charAt(i)))] = "x";
						}
						break;
					}
				} catch (Exception e) {continue;}
			}
		}
		return board;
	}
	
	public int randomPossible(String notation) {
		String[][] board = generateFromNotation(notation);
		for (int col=0; col<6; col++) {
			if (board[0][col].equals(".")) {
				return col;
			}
		}
		return -1;
	}
	
	public int play(String notation) {
		HashMap<String, Integer> optimalMoves = new HashMap<String, Integer>();
		String n;
		String[][] board;
		String sol;

		for (int a=0; a<7; a++) {
			n = notation+a;
			board = generateFromNotation(n);
			sol = checkWin(board);
			if (sol == "x") {
				return a;
			} else {
				for (int b=0; b<7; b++) {
					n = notation+a+""+b;
					board = generateFromNotation(n);
					sol = checkWin(board);
					if (sol == "o") {
						optimalMoves.put(n, -2);
						break;
					} else if (sol == "x") {
						optimalMoves.put(n, 2);
						break;
					} else {
						for (int c=0; c<7; c++) {
							n = notation+a+""+b+""+c;
							board = generateFromNotation(n);
							sol = checkWin(board);
							if (sol == "o") {
								optimalMoves.put(n, -1);
								break;
							} else if (sol == "x") {
								optimalMoves.put(n, 1);
								break;
							} else {
								optimalMoves.put(n, 0);
							}
						}
					}
				}
			}
		}
		
		HashMap<String, String> bestMove = new HashMap<String, String>();
		bestMove.put("seq", null);
		bestMove.put("score", "0");
		
		for (String key : optimalMoves.keySet()) {
			if (optimalMoves.get(key) >= Integer.valueOf(bestMove.get("score"))) {
				bestMove.put("seq", key);
				bestMove.put("score", Integer.toString(optimalMoves.get(key)));
			} else if (optimalMoves.get(key) == -2) {
				
				// stack prevention
				String c = key.replace(notation, "");
				if (c.charAt(0) == c.charAt(1)) {continue;}
				else {
					String best = key.replace(notation.substring(0, notation.length()-1), "");
					int solution = Integer.parseInt(String.valueOf(best.charAt(best.length()-1)));
					
					board = generateFromNotation(notation);
					if (board[0][solution] != ".") {return randomPossible(notation);}
					else {return Integer.parseInt(String.valueOf(best.charAt(best.length()-1)));}
				}
			}
		}
		
		String seq = null;
		if (Integer.valueOf(bestMove.get("score")) == 0) {
			for (String key: optimalMoves.keySet()) {
				if (optimalMoves.get(key) == 0 && (((int)(Math.random()*(3-1))+1) == 1)) {
					seq = key;
					break;
				}
				seq = bestMove.get("seq");
			}
		} else {
			seq = bestMove.get("seq");
		}

		if (seq == null) {
			return randomPossible(notation);
		} else {
			String best = seq.replace(notation, "");
			return Integer.parseInt(String.valueOf(best.charAt(0)));
		}
	}
}
