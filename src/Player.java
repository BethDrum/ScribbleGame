/**
 * A Player object, used in ScribbleMain
 */
public class Player {
	//initialise fields
	private int score;
	private String name;
	
	/**
	 * Base constructor, sets values to be empty
	 */
	public Player() {
		score = 0;
		name = "";
	}
	
	/**
	 * Constructor for when the name is passed in.
	 * 
	 * @param nameN, the new name to be set
	 */
	public Player(String nameN) {
		score = 0;
		name = nameN;
	}
	
	/**
	 * Get method to return the value currently stored in the score field
	 * 
	 * @return score, the current score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get method to return the value currently stored in the name field
	 * 
	 * @return name, the current name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set method to assign a new value to the score field
	 * 
	 * @param scoreN, the new score
	 */
	public void setScore(int scoreN) {
		score = scoreN;
		return;
	}
	
	/**
	 * Set method to assign a new value to the name field
	 *
	 * @param nameN, the new name
	 */
	public void setName(String nameN) {
		name = nameN;
		return;
	}
}
