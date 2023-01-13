package connect4;

public class runner {	
	public static void main(String[] args) {
		AI computer = new AI();
		game connect4 = new game();
		
		connect4.start();
		while (true) {
			if (connect4.getTurn()%2 == 0) {
				connect4.showBoard();
				connect4.playTurn(0);
			} else {
				if (connect4.getTurn() == 42) {break;}
				if (connect4.getTurn() == 1) {
					if (connect4.getBoard()[5][3] == null) {connect4.playTurn(1, 3, false);}
					else if (connect4.getBoard()[5][2] == null) {connect4.playTurn(1,2, false);}
					else if (connect4.getBoard()[5][4] == null) {connect4.playTurn(1, 4, false);}
				} else {
					int sol = computer.play(connect4.getNotation());
					if (connect4.getBoard()[0][sol] != null) {
						for (int i=0; i<7; i++) {
							if (connect4.getBoard()[0][i] == null) {
								connect4.playTurn(1, i, false);
								break;
							}
						}
					} else {
						connect4.playTurn(1, sol, false);
					}
				}
			}
			if (connect4.checkWins() != 2) {break;}
		}
		
		connect4.showBoard();
		if (connect4.checkWins() == 0) {System.out.println("You win! "+connect4.getTurn()+" turns.");}
		else if (connect4.checkWins() == 1) {System.out.println("AI wins! "+connect4.getTurn()+" turns.");}
		else {System.out.println("It was a tie!");}
		
		System.out.println("Move's notation: "+connect4.getNotation());
	}
}
