//import scanner
import java.util.Scanner;

/**
 * A class to hold functions that check if the word can be added
 * and then add it to the board.
 * 
 * Used in ScribbleMain
 * Uses ScribbleAPIDictionary
 */
public class PlusWord {
	/**
	 * Method used to ask the user for/add a new word to the board.
	 * 
	 * @param board, the board that hold words.
	 * @param turn, the number of turns completed thusfar, turns here are counted after each word added.
	 * 
	 * @return word, the word added to the board.
	 */
	public String addWord(String[][] board, int turn) {
		//initialise scanner and create instance of scribbleDicitonary class
		Scanner n = new Scanner(System.in);
		ScribbleAPIDictionary checkWord = new ScribbleAPIDictionary();
		//initialise other values needed
		int x = 0;
		int y = 0;
		int yCoord = 0;
		int xCoord = 0;
		String word = "";
		String dir = "";

		//get all values in - the word, x and y coordinates and direction
		System.out.print("Enter the word you wish to add: ");
		word = (n.nextLine().toUpperCase());
		//use API to check if entered word is valid
		boolean wordValid = checkWord.callAPI(word);
		while (!wordValid) {
			System.out.print("The word entered is not registered as a actual word. Please re-enter: ");
			word = (n.nextLine().toUpperCase());
			 wordValid = checkWord.callAPI(word);
		}
		//split word into an array for future use
		String[] splitW = word.split("");
		//word input check
		while (splitW.length < 2) {
			System.out.print("The length of the entered word must be greater than 2, please re-enter: ");
			word = (n.nextLine().toUpperCase());
			splitW = word.split("");
		}
		//Ask user to enter y coord & input check	
		System.out.print("Whats the y coord to start placing this word from? ");
		yCoord = Integer.parseInt(n.nextLine());
		while ((yCoord > 14)||(yCoord < 0)) {
			System.out.print("The entered y coord is not within the bounds of the board. Please re-enter: ");
			yCoord = Integer.parseInt(n.nextLine());
		}
		//Ask user to enter x coord & input check
		System.out.print("Whats the x coord to start placing this word from? ");
		xCoord = Integer.parseInt(n.nextLine());
		while ((yCoord > 14)||(yCoord < 0)) {
			System.out.print("The entered x coord is not within the bounds of the board. Please re-enter: ");
			yCoord = Integer.parseInt(n.nextLine());
		}
		//Ask user to enter direction of word placement & input check
		System.out.print("Enter a H for it to be placed horizontally or, a V for it to be placed vertically: ");
		dir = (n.nextLine().toUpperCase());
		while ((!dir.equals("H")) && (!dir.equals("V"))) {
			System.out.print("The entered value must be a H (horizontal) or a V (vertical). Please re-enter: ");
			dir = (n.nextLine().toUpperCase());
		}
		
		//check that the word can be placed
		String canPlace = checkPlacings(splitW, yCoord, xCoord, dir, board, turn);
		//if the word does not place correctly on the board,
		while (!canPlace.equals("Yes")) {
			//Display error according to why word cannot be placed
			if (canPlace.equals("TooBig")) {
				System.out.print("The entered word goes beyond the parameters of the board. \nPlease re-enter word and placings,  ensuring that it will fit within the bounds of the board: \n");
			}else if (turn == 0){
				System.out.print("The entered word must intersect (7,7), \nPlease either a new word or new placings: ");
			}else {
				System.out.print("The entered word does not intersect another and so cannot be placed, \nPlease either a new word or new placings: ");
			}
			//ask the user for the word to add to re-try
			System.out.print("Enter the word you wish to add: ");
			word = (n.nextLine().toUpperCase());
			//check using API if its a word
			wordValid = checkWord.callAPI(word);
			while (!wordValid) {
				System.out.print("The word entered is not registered as a actual word. Please re-enter: ");
				word = (n.nextLine().toUpperCase());
				wordValid = checkWord.callAPI(word);
			}
			splitW = word.split("");
			//word input check
			while (splitW.length < 2) {
				System.out.print("The length of the entered word must be greater than 2, please re-enter: ");
				word = (n.nextLine().toUpperCase());
				splitW = word.split("");
			}
			//Ask user to enter y coord & input check	
			System.out.print("Whats the y coord to start placing this word from? ");
			yCoord = Integer.parseInt(n.nextLine());
			while ((yCoord > 14)||(yCoord < 0)) {
				System.out.print("The entered y coord is not within the bounds of the board. Please re-enter: ");
				yCoord = Integer.parseInt(n.nextLine());
			}
			//Ask user to enter x coord & input check
			System.out.print("Whats the x coord to start placing this word from? ");
			xCoord = Integer.parseInt(n.nextLine());
			while ((yCoord > 14)||(yCoord < 0)) {
				System.out.print("The entered x coord is not within the bounds of the board. Please re-enter: ");
				yCoord = Integer.parseInt(n.nextLine());
			}
			//ask user to enter direction of placement & input check
			System.out.print("Enter a H for it to be placed horizontally or, a V for it to be placed vertically: ");
			dir = (n.nextLine().toUpperCase());
			while ((!dir.equals("H")) && (!dir.equals("V"))) {
				System.out.print("The entered value must be a H (horizontal) or a V (vertical). Please re-enter: ");
				dir = (n.nextLine().toUpperCase());
			}
			//check again if can be placed
			canPlace = checkPlacings(splitW, yCoord, xCoord, dir, board, turn);
		}
		
		//for all places in the board array
		if (canPlace.equals("Yes")){
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) {
					//if the current place is one of the needed boxes,
					if (i == yCoord + y && j == xCoord + x) {
						//check if the word is being placed horizontally or vertically and add to the board
						if (dir.equals("V")) {
							//make sure there are still letters left to add
							if (y < splitW.length) {
								//place in letters
								board[yCoord + y][xCoord] = splitW[y];
								y ++;
							}
						}else {
							if (x < splitW.length) {
								//place in letters
								board[yCoord][xCoord + x] = splitW[x];
								x ++;
							}
						}
					}
				}
			}
		}
		return word;
	}
	
	/**
	 * This method iterates through each letter in the word and finds if it can be placed or not.
	 * If it is the first go, the word must intersect the centre (point (7,7)).
	 * The word cannot be placed (after the first go) if; it does not intersect another word, it goes outside the 
	 * parameters of the board or if it intersects an already placed letter with one not eqaul to it.
	 *
	 * @param word, the entered word, split into an array of the letters contained.
	 * @param xCoord, the x-coordinate of the space on the board to start placing the word from.
	 * @param yCoord, the y-coordinate of the space on the board to start placing the word from.
	 * @param dir, the direction of the word to be added.
	 * @param board, the board that hold words.
	 * @param turn, the number of turns completed thusfar, turns here are counted after each word added.
	 * 
	 * @return canPlace, if the word can be placed or not - if not it also holds why.
	 */
	public String checkPlacings(String[] word, int xCoord, int yCoord, String dir, String[][] board, int turn) {
		//initialise values
		String canPlace = "Nope";
		int x = 0;
		int y = 0;
		boolean middlePassed = false;
		
		//For the length of the word
		for (int lenWord = 0; lenWord < word.length; lenWord++) {
			//alter current space to check if letter matches
			if (dir.equals("V")){
				xCoord = xCoord + x;
			}else if (dir.equals("H")){
				yCoord = yCoord + y;
			}
			
			//ensure the coords to be placed are apart of the board
			if ((xCoord > 14) || (yCoord > 14 )) {
				canPlace = "TooBig";
				return canPlace;
			}
			
			//if the turn is 0, update if the middle has been passed
			if (turn == 0 && xCoord == 7 && yCoord == 7) {
				middlePassed = true;
			}
		
			//check the current space
			//if the letter does not match, return that it cannot be placed
			if (!board[xCoord][yCoord].equals("~")) {
				if (board[xCoord][yCoord].equals(word[lenWord])) {
					canPlace = "Yes";
				}else {
					canPlace = "Nope";
				}
			}
			
			//add to x or y (depending on direction) if this is the first iteration
			if (lenWord == 0){
				if (dir.equals("V")){
					x++;
				}else if (dir.equals("H")){
					y++;
				}
			}
		}
		//if the middle has been passed and its turn one, the word can be placed
		if (turn == 0 && middlePassed) {
			canPlace = "Yes";
		}
		return canPlace;
	}
}
