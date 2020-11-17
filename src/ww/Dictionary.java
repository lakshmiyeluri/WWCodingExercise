package ww;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

	// Method for printing the meaning

	public static void doesFileExist(String path) {

		File meaningsFile = new File(path);
		try {
			Scanner meaningsFileReader = new Scanner(meaningsFile);
			while (meaningsFileReader.hasNextLine()) {
				String wordMeaning = meaningsFileReader.nextLine();
				String[] meanings = wordMeaning.split(" - |, ");
				for (String s : meanings) {
					System.out.println(s);
				}
			}
			meaningsFileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Meanings dictionary file not found");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		doesFileExist("meanings.txt");

	}

}
