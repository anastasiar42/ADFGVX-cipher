package ie.atu.sw;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Menu {
	private String key = ""; // Placeholder for the key
	private FileHandler fileHandler = new FileHandler();
	private ADFGVXCipher cipher;

	// This method is the entry point for the menu-based interaction with the
	// application.
	public void start() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printMenu();
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-7] > ");

			try {
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				switch (choice) {
				case 1:
					fileHandler.getInputFileDirectory();
					break;
				case 2:
					fileHandler.getOutputFileDirectory();
					break;
				case 3:
					System.out.print("Enter Key: ");
					key = scanner.nextLine();
					break;
				case 4:
					cipher = new ADFGVXCipher(key);
					processFiles(true); // Encrypt files
					break;
				case 5:
					cipher = new ADFGVXCipher(key);
					processFiles(false); // Decrypt files
					break;
				case 6:
					System.out.println("Exiting...");
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please select a valid option.");
				}
			} catch (Exception e) {
				System.err.println("Error: Invalid input or unexpected exception occurred.");
				scanner.nextLine(); // Clear the input buffer in case of incorrect input
			}
		}
	}

	// This method processes files in the input directory, either encrypting or
	// decrypting them.
	private void processFiles(boolean encrypt) {

		// Retrieve input and output file directories from FileHandler
		String inputFileDirectory = fileHandler.getInputFileDirectoryPath();
		String outputFileDirectory = fileHandler.getOutputFileDirectoryPath();

		File inputDirectory = new File(inputFileDirectory);
		System.out.println("Input Directory: " + inputFileDirectory);
		File outputDirectory = new File(outputFileDirectory);
		System.out.println("Output Directory: " + outputFileDirectory);

		File[] txtFiles = inputDirectory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");
			}
		});

		if (txtFiles == null || txtFiles.length == 0) {
			System.out.println("Error: No .txt files found in the specified input directory.");
			return;
		}

		// Check if the output directory exists, and create it if it doesn't.
		if (!fileHandler.createOutputDirectory()) {
			System.out.println("Error: Unable to create output directory.");
			return;
		}

		for (File file : txtFiles) {
			if (file.isFile() && file.canRead()) {
				try {
					String content = Files.readString(file.toPath());
					String processedContent;
					if (encrypt) {
						processedContent = cipher.encrypt(content);
					} else {
						processedContent = cipher.decrypt(content);
					}

					String outputFileName = (encrypt ? "encrypted_" : "decrypted_") + file.getName();
					File outputFile = new File(outputDirectory.getPath() + File.separator + outputFileName);
					Files.write(outputFile.toPath(), processedContent.getBytes());

					System.out.println("Processed: " + file.getName() + " -> " + outputFileName);
				} catch (IOException e) {
					System.out.println("Error processing file: " + file.getName() + ". Error: " + e.getMessage());
					e.printStackTrace();
				}

			}
		}
	}

	// The main method creates an instance of the Menu class and starts the menu.
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.start();
	}

	// This method prints the main menu options.
	private void printMenu() {
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*  ATU - Higher Diploma in Science in Software Development *");
		System.out.println("*                                                          *");
		System.out.println("*               ADFGVX File Encryption                     *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Input File Directory");
		System.out.println("(2) Specify Output File Directory");
		System.out.println("(3) Set Key");
		System.out.println("(4) Encrypt");
		System.out.println("(5) Decrypt");
		System.out.println("(6) Quit");
	}
}
