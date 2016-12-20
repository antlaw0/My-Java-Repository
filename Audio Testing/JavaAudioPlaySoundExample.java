/*
Monday December 19 2016
Testing for playing audio. 
Problem:  If play sound file then another file, when execute stop, only latest instance of playing sound is stopped, continuing to send stop command fails. Work-around, put all playing instances in a list or other method to keep track of them and close them.
This should be fine for implementation in game as long as stop long music files before playing another.
Idea, concatinate ".wav" at end of file name so just enter name of file to play without entering '.wav' every time.
Only works with .wavs so far.
Check into warning that this is propriarity api and may be discontinued in later versions, could be issue in long-term projects.

*/
import java.io.*;
import sun.audio.*;
import java.util.*;
/**
 * A simple Java sound file example (i.e., Java code to play a sound file).
 * AudioStream and AudioPlayer code comes from a javaworld.com example.
 * @author alvin alexander, devdaily.com.
 */
 public class JavaAudioPlaySoundExample
{
  static Scanner stringScanner =new Scanner(System.in);
static InputStream inputStream;  
static AudioStream audioStream;
  public static void main(String[] args) 
  {
    boolean looping=true;
	String fileName;
	while(looping) {
		System.out.println("Enter name of sound file to play:  ");
		fileName=stringScanner.nextLine();
		if (fileName.equals ("stop")) {
			try{
			AudioPlayer.player.stop(audioStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (fileName.equals ("quit")) {
			System.exit(0);

		}
		else {
			sound_play(fileName);
		
		}
			
			
			
	}//end of while loop
	
	
	}//end of main method
	
	
	
	
	
	public static void sound_play(String fileName) 
  {
try{		
	// open the sound file as a Java input stream
     inputStream = new FileInputStream(fileName);

    // create an audiostream from the inputstream
     audioStream = new AudioStream(inputStream);

    // play the audio clip with the audioplayer class
    AudioPlayer.player.start(audioStream);
} catch (Exception e) {
	e.printStackTrace();
}
	}//end of sound_play method
	
}