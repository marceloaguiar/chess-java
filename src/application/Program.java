package application;

import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();		

		while (!chessMatch.getCheckMate()) {

			try {
				UI.clearScreen();
				//UI.printBoard(chessMatch.getPieces());
				UI.printMatch(chessMatch);
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),possibleMoves);
				

				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				chessMatch.performChessMove(source, target);
								
				if (chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
						System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}				
			} 
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (RuntimeException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}					
		}
		UI.clearScreen();
		UI.printMatch(chessMatch);

	}
}
