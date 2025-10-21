//imports
//imports need for file handling
//for writing to file
import java.io.FileOutputStream;
import java.io.PrintWriter;
//for reading in from a file
import java.io.FileReader;
import java.io.BufferedReader;
//for exceptions
import java.io.FileNotFoundException;
import java.io.IOException;
//other imports
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class in the Scribble games, here almost all methods are held, this includes:
 * display, score, save, load, startGame, checkGameEnd, gameEnd, instuctions, help and the menu.
 * This class includes the main method.
 * 
 * This class uses; ScribbleSound, PlusWord and Player
 */
public class ScribbleMain {
	//initialise values for the class
	String[][] board = new String[15][15];
	private int maxTurn;
	private int maxScore;
	private int turn;
	
	/**
	 * The constructor method.
	 * Here this initialises the board to be 0 in all values and sets all other fields to be empty.
	 */
	public ScribbleMain(){
		//initialise board
		for (int i=0; i<board.length; i++){
			for (int j=0; j<board[i].length; j++){
				board[i][j] = "~";
			}
		}
		//initialise others
		maxTurn = 0;
		maxScore = 0;
		turn = 0;
	}

	
	/**
	 * Method to display the entire board, with corresponding row/col numbers and the title.
	 * 
	 * No values are returned.
	 */
	public void display() {
		//initialise coord array
		int[] coords = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		//print title
		System.out.print("\t\t\tScribble!\t\t\t\n");
		//start displaying the board
		for (int i=0; i<board.length; i++){
			//if its the start, print the x-axis coords
			if (i==0) {
				System.out.print("\t");
				//print names by iterating through the coods array
				for(int row =0; row<coords.length; row++) {
					if (row < 10) {
						System.out.print(coords[row]+"  ");
					}else {
						System.out.print(""+coords[row]+" ");
					}
				}
				System.out.println("\n\t--------------------------------------------");
				System.out.println();
			}
			//print the y-axis coord
			if (i < 10) {
				System.out.print(coords[i]+" |\t");
			}else {
				System.out.print(coords[i]+"|\t");
			}
			for (int j=0; j<board[i].length; j++){
				System.out.print(board[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Method to keep score for each player. 
	 * Score is determined according to the letters in the word entered. 
	 * Each letter has a different score value.
	 * 
	 * @param word, the word entered to the scoreboard.
	 * 
	 * @return total, the total score found for this word to be added to the players main score.
	 */
	public static int score(String word) {
		//initialise letter to be each letter in the given word
		String[] letter = word.split("");
		//initialise corresponding abc and points arrays
		String[] abc = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		int[] points = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		//initialise done for length of word
		String[] done = new String[letter.length];
		for (int len = 0; len <done.length; len++){
			done[len] = "no";
		}
		//initialise total, i and at for use in loop
		int total = 0;
		int at = 0;
		int i = 0;

		//to add to total
		//runs for length of WORD
		for (int pointInWord = 0; pointInWord<letter.length; pointInWord++) {
			//runs for length of ABC array, has check for if the current check has gone above the number of letters available
			while ((i <26) && i < points.length && (done[(done.length)-1] != "t")) {
				if (letter[at].equals(abc[i])) {
					total = total + points[i];
					done[at] = "t";
				}
				i++;
				//if the letter is found, set new values to find next
				if (done[at] == "t") {
					i = 0;
					at = at + 1;
				}
			}
		}
		return total;
	}
	
	/**
	 * Method to save the current file to a new file.
	 * 
	 * @param file, the file to save the current game to.
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * 
	 * There are no values returned.
	 */
	private void save(String file, ArrayList <Player> players) {
		//initialise values
		FileOutputStream outputStream;
		PrintWriter printWriter = null;
		try {
			//initialise new outputStream and printWriter
			outputStream = new FileOutputStream(file);
			printWriter = new PrintWriter(outputStream);
			//place in number of players
			printWriter.print(players.size());
			printWriter.print("\n");
			//place in player names (separated by a comma)
			for (int lenN=0; lenN < players.size(); lenN++) {
				printWriter.print(players.get(lenN).getName() + ",");
			}
			printWriter.print("\n");
			//place in player scores (separated by a comma)
			for (int lenS=0; lenS < players.size(); lenS++) {
				printWriter.print(players.get(lenS).getScore() + ",");
			}
			printWriter.print("\n");
			//place in max turns for this game
			printWriter.print(maxTurn);
			printWriter.print("\n");
			//place in turns played
			printWriter.print(turn-1);
			printWriter.print("\n");
			//place in max score for this game
			printWriter.print(maxScore);
			printWriter.print("\n");
			
			//place in board
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) {
					//write each piece of data stored into the file with a comma separating
					printWriter.print(board[i][j]+",");
				}
				//add a new line after each row
				printWriter.print("\n");
			}
			System.out.print("\nFileSaved");
		//catch error messages
		}catch (IOException e) {
			//display error message
			System.out.print("Error has occured: "+e);
		}finally {
			if (printWriter != null)
				//empty and then close the printWriter
				printWriter.flush();
                printWriter.close();
		}
	}
	
	/**
	 * Method to load in a previous file.
	 * In general, the file saved in must be a previously saved game.
	 * 
	 * @param file, the file to save the current game to.
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * 
	 * @return load, if the file has been loaded in successfully or not.
	 */
	public boolean load(String file, ArrayList <Player> players) {
		//initialise values
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String nextL;
		Boolean load = false;
		
		try {
			//initialise new file reader for given reader and fileReader
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			//read in the values for setting up the players
			//number of players
			int noPlayers = Integer.parseInt(bufferedReader.readLine());
			//names
			nextL = bufferedReader.readLine();
			String[] names = nextL.split(",");
			//scores
			nextL = bufferedReader.readLine();
			String[] scores = nextL.split(",");
			//max turns
			maxTurn = Integer.parseInt(bufferedReader.readLine());
			//turns played
			turn = Integer.parseInt(bufferedReader.readLine());
			//max score
			maxScore = Integer.parseInt(bufferedReader.readLine());
			//clear any already existing players
			players.clear();
			//for the number of players, add new instances with scores and names attached
			for (int p = 0; p < noPlayers; p++) {
				Player plr = new Player(names[p]);
				players.add(plr);
				players.get(p).setScore(Integer.parseInt(scores[p]));
			}
			
			//read in first line and split the input by its commas, storing in a new String array, temp
			nextL = bufferedReader.readLine();
			String[] temp = nextL.split(",");
			
			//while another line exists
			while (nextL != null) {
				//enter the info from the file into a 2D array
				for (int i=0; i<board.length; i++) {
					//ensure the next line read in is split by its commas
					temp = nextL.split(",");
					for (int j=0; j<board[i].length; j++) {
						//enter each value in turn to the board
						String toAdd = temp[j];
						board[i][j] = toAdd;
					}
					//read in next line
					nextL = bufferedReader.readLine();
				}
			}
		//catch error messages
		}catch (FileNotFoundException e) {
			//display error message
			System.out.print("Error has occured: The file is not found. "+e);
			System.exit(0);
		}catch (IOException e) {
			//display error message
			System.out.print("Error has occured: "+e);
			System.exit(0);
		}finally {
			//if the file was opened then close the file
			if (bufferedReader != null) {
				try {
					//close the file
					bufferedReader.close();
					load = true;
				}catch (IOException e) {
					//display error message
					System.out.print("Error has occured closing the file: "+e);
					System.exit(0);
				}
			}
		}
		return load;
	}
	
	/**
	 * Method to run the main game. 
	 * This will ask for the number of players, assign each player a name from the user.
	 * Then ask the user for the max turns and max score they want to play to.
	 * (These will not be done if the game has been loaded in from a previous save).
	 * If the game has been loaded in, the max turns, score and number of players playing will be displayed to the user.
	 * Following this, the game will begin.
	 * In turn, the current board will be displayed then players will get add their word to the board. 
	 * Scores will then be totalled up and it will be checked if the game should end.
	 * Once all players have gone, if the game is not ending, a menu will be displayed to ask if they are ready to continue, want to view the menu or end their turn.
	 * The game will proceed as told.
	 * 
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * @param load, if the current game being played is from a loaded in game.
	 * 
	 * No values are returned.
	 */
	public void startGame(ArrayList <Player> players, boolean load) {
		//initialise Scanner
		Scanner n = new Scanner(System.in);
		//create instance of PlusWord class
		PlusWord newW = new PlusWord();
		//initialise ongoing for menu output
		boolean ongoing = true;
		//set up other values
		int noPlayers = 0;
		
		//if the game is not previously loaded in
		if (!load) {
			//ask for number of players
			System.out.println("Please enter the number of players (2-4): ");
			noPlayers = Integer.parseInt(n.nextLine());
			//input check
			while ((noPlayers <2) || (noPlayers >4)) {
				System.out.println("Entered value is not between 2 and 4. \nPlease re-enter the number of players (2-4): ");
				noPlayers = Integer.parseInt(n.nextLine());
			}
			
			for (int i=0; i<noPlayers;i++) {
				System.out.println("Please enter the name of player "+(i+1)+": ");
				String name = n.nextLine();
				Player plr = new Player(name);
				players.add(plr);
			}
			//ask for max Score
			System.out.println("Enter the score to get to win: ");
			maxScore = Integer.parseInt(n.nextLine());
			//ask for max number of turns
			System.out.println("Enter the max number of turns in this game: ");
			maxTurn = Integer.parseInt(n.nextLine());
		}else{
			//set values to those read in if coming from previous load
			noPlayers = players.size();
			System.out.println("The previous game has been loaded.");
			System.out.println("The score needed to win is: "+maxScore+"\nThe number of turns left to play are: "+((maxTurn)-(turn/players.size()))+"\nEnjoy!");
			//correct turn for gameplay
			turn ++;
		}
		
		//play game
		boolean done = false;
		//print out the empty board
		display();
		//while game is not done
		while (!done) {
			for (int i = 0; i < noPlayers; i++) {
				System.out.println("Player "+(i+1)+"  -  "+players.get(i).getName());
				String word = newW.addWord(board, turn);
				//add to current players score
				int toAdd = score(word);
				int currentScore = players.get(i).getScore();
				int newScore = currentScore + toAdd;
				players.get(i).setScore(newScore);
				//add 1 to turns
				turn ++;
				//display current grid
				display();
				//check if the game is finished
				done = checkGameEnd(newScore, players);
			}
			turn++;
			//give end of turn options IF the turn is not done yet
			if (done) {
				gameEnd(players);
			}
			//display current score if not finished
			for (int j=0; j<noPlayers; j++) {
				System.out.println(players.get(j).getName()+"'s current score: "+players.get(j).getScore());
			}
			System.out.print("Press 1 to end game, 2 to view menu or 3 to continue :");
			int choice = Integer.parseInt(n.nextLine());
			while ((choice != 1) && (choice != 2) && (choice !=3)) {
				System.out.print("Value entered must be 1,2 or 3.\nPress 1 to end game, 2 to view menu or 3 to continue :");
				choice = Integer.parseInt(n.nextLine());
			}
			//a switch loop is used to execute the asked for method
			switch (choice) {
			case 1:
				//End game manually
				gameEnd(players);
				break;
			case 2:
				//View menu
				menu(ongoing, players);
				break;
			case 3:
				//continue
				break;
			}
		}
		//once done, end game
		gameEnd(players);
	}
	
	//METHOD NEEDS TESTING
	/**
	 * Method used to check if the game should be ended or not.
	 * The game ends according to the entered values from the user if: no more spaces or the players reach the score or turns they previously set.
	 * 
	 * @param score, the total score for the current player.
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * 
	 * @return end, if the game should end or not.
	 */
	public boolean checkGameEnd(int score, ArrayList <Player> players) {
		//initialise values
		boolean end = false;
		
		//first check for empty spaces
		int emptyBoxes = 0;
		//for all places in the array
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {
				//if the current box is empty, add 1 to counter
				if (board[i][j].equals("~")) {
					emptyBoxes = emptyBoxes + 1;
				}
			}
		}
		if (emptyBoxes == 0) {
			System.out.print("There are no boxes left to fill on the board.");
			end = true;
			return end;
		}
		
		//next check if the maximum number of turns entered have been played
		if (maxTurn != 1) {
			if (turn > (maxTurn*players.size())) {
				System.out.print("All turns have been played.");
				end = true;
				return end;
			}
		}else if (turn == players.size()){
			System.out.print("All turns have been played.");
			end = true;
			return end;
		}
		
		//finally check the scores for each player
		if (score >= maxScore){
			end = true;
			return end;
		}
		//return end if no return has happened yet
		return end;
	}
	
	
	/**
	 * Method to display all outputs on game end. 
	 * The end result is displayed in a sodium which the players, their scores and end places following.
	 * There is also a sound played as the winners are announced
	 * 
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * 
	 * \no values are returned
	 */
	public void gameEnd(ArrayList <Player> players) {
		//create an instance of the sound class
		ScribbleSound soundEffect = new ScribbleSound();
		//initialise variables
		ArrayList<Integer> place = new ArrayList<Integer>();
		boolean swapped = true;
		int temp = 0;
		int tempPlace = 0;
		String tempName = "";
		int drawCheck = 1;
		//set up how many places are needed
		place.add(0);
		place.add(1);
		place.add(2);
		//if there is more than 3 players, add more
		if (players.size() > 3){
			for (int j = 2; j < players.size(); j++) {
				place.add(j+1);
			}
		}
		
		//organise into first to last
		for (int p = 0; p < players.size(); p++) {
			swapped = false;
			for (int i = 1; i < players.size(); i++) {
				if (players.get(i-1).getScore() < players.get(i).getScore()) {
					//organise scores
					temp = players.get(i-1).getScore();
					players.get(i-1).setScore(players.get(i).getScore());
					players.get(i).setScore(temp);
					//organise places accordingly
					tempPlace = place.get(i-1);
					place.set(i-1, place.get(i));
					place.set(i, tempPlace);
					//organise names
					tempName = players.get(i-1).getName();
					players.get(i-1).setName(players.get(i).getName());
					players.get(i).setName(tempName);
					//update swapped
					swapped = true;
				//check if there is a draw between all players
				}else if (players.get(i-1).getScore() == players.get(i).getScore()){
					drawCheck ++;
				}
			}
			if (!swapped) {
				break;
			}
		}
		
		//start printing out the ending placements
		System.out.print("\n\nAnd the winner is.....\n\n\n");
		//play sound by calling sound class
		soundEffect.soundClip("victory.wav");
		//print out placing alike a podium, varying from 2 players to 3. There is also a different output if there a draw.
		if (players.size() < 3) {
			//different output if its a draw
			if (drawCheck == players.size()) {
				System.out.print("          Its a draw!             \n");		
				System.out.print("                                       \n");
				System.out.print("     Player "+(place.get(1)+1)+"    Player "+(place.get(0)+1)+"  \n");
				System.out.print("     --------- ---------             \n");
				System.out.print("    |    2    |    1    |            \n");
				System.out.print("    |         |         |            \n");
			}else {
				System.out.print("               Player "+(place.get(0)+1)+"                     \n");		
				System.out.print("               ---------               \n");
				System.out.print("     Player "+(place.get(1)+1)+" |    1    |              \n");
				System.out.print("     ---------          |              \n");
				System.out.print("    |    2    |                        \n");
				System.out.print("    |         |         |              \n");
			}
		//print for 3
		}else {
			if (drawCheck == players.size()) {
				System.out.print("               Its a draw!             \n");		
				System.out.print("                                       \n");
				System.out.print("     Player "+(place.get(1)+1)+"    Player "+(place.get(0)+1)+"     Player "+(place.get(2)+1)+"\n");
				System.out.print("     --------- --------- ---------     \n");
				System.out.print("    |    2    |    1    |    3    |    \n");
				System.out.print("    |         |         |         |    \n");
			}else{
				System.out.print("               Player "+(place.get(0)+1)+"                     \n");		
				System.out.print("               ---------               \n");
				System.out.print("     Player "+(place.get(1)+1)+" |    1    |              \n");
				System.out.print("     ---------          | Player "+(place.get(2)+1)+"     \n");
				System.out.print("    |    2    |          ---------     \n");
				System.out.print("    |         |         |    3    |    \n");
			}
		}
		
		//print out detailed names and scores
		for (int i = 0; i < players.size(); i++) {
			System.out.print("\nPlace: "+(i+1));
			System.out.print("\nPlayer "+(place.get(i)+1)+": "+players.get(i).getName()+", Final Score:"+players.get(i).getScore());
		}
		
		//end system
		System.exit(0);
	}
	
	
	/**
	 * Method to print instructions to the user.
	 * 
	 * No values enter and none return.
	 */
	public void instructions() {
		//print out instructions
		System.out.println("Scribbles Instructions: ");
		System.out.println("The Main Game:");
		System.out.println("1.\tOn starting the game, choose the number of players you wish to play with (from 2 players to 4. \n\tThen enter the players names as prompted. \n2.\tFor each players turn,\n\t2.1.\t Enter the word you wish to play. \n\t2.2.\tEnter the y coord and then x coord in turn of the place the word should start from.\n\t2.3.\tFinally, enter if the word should be placed horizontally or vertically from the point specified to start.\n\t\t NOTE: If it is the first turn and the first players turn, the placed word MUST intersect (7,7)");
		System.out.println("3.\tOn the players finishing their turns, the menu will be displayed with the same opition as the begining but, there will also be a choice to save the current board to a file. This allows you to reload the board in future.");
		System.out.println("\nLoading in a Previous Game:");
		System.out.println("1.\tSelect option 4 on the menu when its presented. \n2.\t Enter the name of your previously saved file. \n3.\tYour file is now loaded in and ready for you to continue playing!");
		System.out.println("\nSaving the Current Game:");
		System.out.println("1.\tWhen the menu presents itself select option 5. \n2.\tOn its selection, you will be asked to enter the name of the file you wish to save the file to, this must end with a .txt.\n3.\tThe file is now saved and ready to be loaded in future.");
		System.out.println("\n");
	}
	
	
	/**
	 * Method to print out help to the user.
	 * 
	 * No values enter and none return.
	 */
	public void help() {
		//print out help 
		System.out.println("Help: ");
		System.out.println("Controls: ");
		System.out.println("- The game is played using your keyboars, the only keys needed are your letter keys.");
		System.out.println("\n\nFAQ's: ");
		System.out.println(" - How do I win?\n\t -The game is won by aquiring the most points through playing different words on the board. \n\t Each letter has a diffrent value of points. \n\t To see how the game is played open the instructions from the menu.");
		System.out.println(" - What is each letter worth?\n\t - 1 point - A, E, I, L, N, O, R, S, T, U\n\t - 2 points - D, G\n\t - 3 points - B, C, M, P\n\t - 4 points - F, H, V, W, Y\n\t - 5 points - K\n\t - 8 points - J, X\n\t - 10 points - Q, Z\n");
		System.out.println("\nCommon Errors: ");
		System.out.println(" - There is a gap after entering my word for the first time. \n\t This is due to the dictioary API being called for the first time. Wait a couple seconds and the program will continue.");
		System.out.println("\n");
	}
	
	/**
	 * The menu method - shows at start and is available throughout
	 * This allows the user to access help, instructions and load a previous file.
	 * If the game is already running, there will also be an option to save the current game or to continue.
	 *
	 * @param ongoing, if the game has already begun or not
	 * @param players, the players currently in the game, this is an array of the object Player.
	 * 
	 * No values are returned.
	 */
	public void menu(boolean ongoing, ArrayList <Player> players) {
		//initialise Scanner
		Scanner n = new Scanner(System.in);
		//initialise values
		int choice = 0;
		boolean load = false;
		
		//if the game has not began, print welcome statement and first 4 choices.
		if (!ongoing) {
			System.out.println("Welcome to Scribble!");
			System.out.println("Select your option from the following: ");
			System.out.println("1. Start New Game \n2. View Instructions \n3. View Help \n4. Load Previous Game");
			choice = Integer.parseInt(n.nextLine());
			//check choice is within parameters
			while (choice > 4) {
				System.out.print("The entered value is not within bounds, please re-enter: ");
				choice = Integer.parseInt(n.nextLine());
			}
		//if the game has began print all options
		}else {
			System.out.println("Select your option from the following: ");
			System.out.println("1. Start New Game \n2. View Instructions \n3. View Help \n4. Load Previous Game \n5. Save The Current Game to New File\n6. Continue Current Game");
			choice = Integer.parseInt(n.nextLine());
			//check choice is within parameters
			while (choice > 6) {
				System.out.print("The entered value is not within bounds, please re-enter: ");
				choice = Integer.parseInt(n.nextLine());
			}
		}
		
		//a switch loop is used to execute the asked for method
		switch (choice) {
		case 1:
			// start new game
			startGame(players, load);
			break;
		case 2:
			//instructions
			instructions();
			menu(ongoing, players);
			break;
		case 3:
			//help
			help();
			menu(ongoing, players);
			break;
		case 4:
			//load file
			System.out.print("Enter the name of the file your previous game is saved in: ");
			String file = n.nextLine();
			load = load(file, players);
			startGame(players, load);
			break;
		case 5:
			//save current game as a file
			System.out.print("Enter the name of the file you wish to save the current game to: ");
			String newFile = n.nextLine();
			for (int i =0; i<board.length; i++) {
				for (int j=0; j<board.length; j++) {
					System.out.print(board[i][j]);
				}
			}
			save(newFile, players);
			//end run of program
			System.exit(0);
			break;
		//case for if ongoing - there's validation to stop this from being chosen in first iteration
		case 6 :
			break;
		}
	}
	
	
	/**
	 * The main method. The game is started from here.
	 */
	public class Main {
		public static void main(String[] args) {
			//initialise needed variables
			ScribbleMain newG = new ScribbleMain();
			boolean ongoing = false;
			ArrayList<Player> players = new ArrayList<Player>();
			//start the game by calling the menu
			newG.menu(ongoing, players);
		}
	}
}

