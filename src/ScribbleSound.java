//import needed for playing sounds & relevant exceptions
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Class which holds methods to play a sound clip.
 * 
 * Used in ScribbleMain.
 */
public class ScribbleSound {
	/**
	 * Method to play the soundclip
	 * @param fileName, the name of the audio file - must be a .wav file
	 */
	public void soundClip(String fileName) {
		try {
			//initialise file and audio stream
			File file = new File(fileName);
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			
			//initialise audio format and dataline
			AudioFormat format = audio.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			
			//initialise clip
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audio);
			//play the sound
			clip.start();
			//keep program running while playing
			Thread.sleep(clip.getMicrosecondLength()/1000);
		//catch and errors and print error message
		}catch (UnsupportedAudioFileException e){
			System.out.print("Error: The audio file entered is unsupported. "+e);
		}catch (LineUnavailableException e){
			System.out.print("Error: The line cannot be opened. "+e);
		}catch (InterruptedException e){
			System.out.print("Error: Interrupted. "+e);
		}catch (IOException e){
			System.out.print("Error: There has been an error when trying to play a sound. "+e);
		}
	}
}