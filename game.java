package connect4;
import java.util.*;

public class game {
	private String[][] board = {{null, null, null, null, null, null, null}, 
								{null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null}};
	private int turns = 0;
	private String positionNotation = "";
	
	public int getTurn() {return this.turns;}
	public String getNotation() {return this.positionNotation;}
	public String[][] getBoard() {return board;}
	
	public String color(String color) {
		switch(color) {
			case "red": return "\u001B[31m";
			case "blue": return "\u001B[34m";
			case "yellow":  return "\u001B[33m";
			case "esc": return "\u001B[0m";
			default: return "";
		}
	}
	
	public int input(String text) {
		// more python
		Scanner in = new Scanner(System.in);
		System.out.println(text);
		return in.nextInt();
	}
	
	public void showBoard() {
		System.out.println(color("blue")+"1 2 3 4 5 6 7"+color("esc"));
		for (int row=0; row<this.board.length; row++) {
			for (int col=0; col<this.board[row].length; col++) {
				if (this.board[row][col] == null) {
					System.out.print(". ");
				} else {
					System.out.print(this.board[row][col]+" ");
				}
			}
			System.out.println();
		}
	}
	
	public void start() {
		// python formatting LOL
		System.out.println(
		"Welcome to "
		+color("blue")+"connect 4"+color("esc")
		+". You can win in "
		+color("blue")+"connect 4"+color("esc")
		+" by getting 4 in a row. \nThere are 2 players in the game, "
		+color("red")+"red"+color("esc")
		+" and "
		+color("yellow")+"yellow"+color("esc")
		+". You are "
		+color("red")+"red"+color("esc")
		+" while the \nai is "
		+color("yellow")+"yellow"+color("esc")
		+".");
	}
	
	public void move(int col, int player) {
		for (int i=0; i<6; i++) {
			if (board[5-i][col] == null) {
				if (player == 0) {
					board[5-i][col] = color("red")+"o"+color("esc");
				} else {
					board[5-i][col] = color("yellow")+"o"+color("esc");
				}
				return;
			}
		}
	}
	
	public int checkWins() {
		String redSeq = color("red")+"o"+color("esc")+color("red")+"o"+color("esc")+color("red")+"o"+color("esc")+color("red")+"o"+color("esc");
		String yellowSeq = color("yellow")+"o"+color("esc")+color("yellow")+"o"+color("esc")+color("yellow")+"o"+color("esc")+color("yellow")+"o"+color("esc");
		
		// check vertical wins
		for (int col=0; col<7; col++) {
			for (int row=0; row<3; row++) {
				if ((board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col]).equals(redSeq)) {return 0;}
				else if ((board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col]).equals(yellowSeq)) {return 1;}
			}
		}
		
		// check horizontal wins
		for (int row=0; row<6; row++) {
			for (int col=0; col<4; col++) {
			if ((board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3]).equals(redSeq)) {return 0;}
				else if ((board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3]).equals(yellowSeq)) {return 1;}
			}
		}
		
		// diagonals
		for (int i=0; i<3; i++) {
			for (int j=0; j<4; j++) {
				if ((board[i][j]+board[i+1][j+1]+board[i+2][j+2]+board[i+3][j+3]).equals(redSeq)) {return 0;}
				else if ((board[i][j]+board[i+1][j+1]+board[i+2][j+2]+board[i+3][j+3]).equals(yellowSeq)) {return 1;}
				else if ((board[5-i][j]+board[4-i][j+1]+board[3-i][j+2]+board[2-i][j+3]).equals(redSeq)) {return 0;}
				else if ((board[5-i][j]+board[4-i][j+1]+board[3-i][j+2]+board[2-i][j+3]).equals(yellowSeq)) {return 1;}
			}
		}
		
		if (this.turns == 42) {return 3;}
		return 2;
	}
	
	public void playTurn(int player) {
		int col = 99;
		do {
			col = input("Which column do you want to move in?")-1;
		} while ((col < 0 || col > 6) || board[0][col] != null);
		move(col, player);
		positionNotation = positionNotation+(col+"");
		turns += 1;
	}
	
	public void playTurn(int player, int col, boolean showBoard) {
		// for AI implementation
		move(col, player);
		positionNotation = positionNotation+(col+"");
		turns += 1;
		
		if (showBoard) {showBoard();}
	}
}
