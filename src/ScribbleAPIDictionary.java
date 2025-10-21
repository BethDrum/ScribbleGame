//import for API/checking exceptions
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.io.IOException;
/**
 * Class to use a dictionary API and find if a word exists or not.
 *
 * Used in ScribbleMain.
 */
public class ScribbleAPIDictionary {
	
	/**
	 * Method to call the API
	 * 
	 * @param word, the word to be checked if real or not.
	 * @return gotVal, if the API finds a matching word. 
	 * If there is no match false is returned - else true is returned.
	 */
	public boolean callAPI(String word){
		//initialise values
		boolean gotVal = false;
		
		//builder created, headers set to API host
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/"+word)).header("X-RapidAPI-Host", "https://dictionaryapi.dev/").method("GET", HttpRequest.BodyPublishers.noBody()).build();
		//save the response from API
		HttpResponse<String> response = null;
		
		//send API call
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		//catch errors and display any error messages
		}catch (IOException e) {
			System.out.print("Error: "+e);
		}catch (InterruptedException e) {
			System.out.print("Error, interrupted: "+e);
		}
		//store response in a string, then check if this include no definitions,
		String temp = response.body();
		if ((!temp.contains("No Definitions Found"))) {
			//set got value to true if there IS a definition found
			gotVal = true;
		}
		return gotVal;
	}
}

