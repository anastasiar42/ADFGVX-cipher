package ie.atu.sw;

import java.io.File;
import java.util.Scanner;

public class FileHandler {
	private String inputFileDirectory = "";
	private String outputFileDirectory = "";
	private String key = ""; // Placeholder for the key

	// Method to get input file directory path from user input
	public void getInputFileDirectory() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Input File Directory: ");
		inputFileDirectory = scanner.nextLine();
	}

	// Method to get output file directory path from user input
	public void getOutputFileDirectory() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Output File Directory: ");
		outputFileDirectory = scanner.nextLine();
	}

	// Method to retrieve the input file directory path
	public String getInputFileDirectoryPath() {
		return inputFileDirectory;
	}

	// Method to retrieve the output file directory path
	public String getOutputFileDirectoryPath() {
		return outputFileDirectory;
	}

	// Method to create the output directory if it doesn't exist
	public boolean createOutputDirectory() {
		File outputDirectory = new File(outputFileDirectory);
		if (!outputDirectory.exists()) {
			if (!outputDirectory.mkdirs()) {
				System.out.println("Error: Unable to create output directory.");
				return false;
			}
		}
		return true;
	}

	// Method to get the encryption or decryption key
	public String getKey() {
		return key;
	}

	// Method to set the encryption or decryption key
	public void setKey(String key) {
		this.key = key;
	}
}
