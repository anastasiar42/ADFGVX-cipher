package ie.atu.sw;

import java.util.*;

public class ADFGVXCipher {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private String keyword;
	private char[][] keySquare;

	// Constructor to initialize the ADFGVX cipher with a keyword
	public ADFGVXCipher(String keyword) {
		this.keyword = keyword;
		initializeKeySquare(); // Initialize the key square for the cipher
	}

	// Initialize the key square with ADFGVX and the remaining alphabet
	private void initializeKeySquare() {
		keySquare = new char[7][7];

		// Create a string of the remaining alphabet
		String remainingAlphabet = ALPHABET.replace("ADFGVX", "");

		// Fill the first row with blank, A, D, F, G, V, X
		keySquare[0][0] = ' ';
		keySquare[0][1] = 'A';
		keySquare[0][2] = 'D';
		keySquare[0][3] = 'F';
		keySquare[0][4] = 'G';
		keySquare[0][5] = 'V';
		keySquare[0][6] = 'X';

		// Fill the first column with A, D, F, G, V, X
		keySquare[1][0] = 'A';
		keySquare[2][0] = 'D';
		keySquare[3][0] = 'F';
		keySquare[4][0] = 'G';
		keySquare[5][0] = 'V';
		keySquare[6][0] = 'X';

		// Fill the rest of the keySquare with the remaining alphabet in the specified
		// order
		int charIndex = 0;
		for (int row = 1; row < 7; row++) {
			for (int col = 1; col < 7; col++) {
				if (charIndex < remainingAlphabet.length()) {
					keySquare[row][col] = remainingAlphabet.charAt(charIndex);
					charIndex++;
				}
			}
		}
	}

	// Encrypt plaintext using the ADFGVX cipher
	public String encrypt(String plaintext) {
		try {
			String transposedText = columnarTransposition(plaintext);
			String rearrangedText = rearrangeColumns(transposedText);
			return rearrangedText; // Return the encrypted text
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// Perform columnar transposition on the input text
	private String columnarTransposition(String input) {
		StringBuilder encryptedText = new StringBuilder();

		// Remove special characters, convert input to uppercase, and remove whitespace
		input = input.toUpperCase().replaceAll("[^A-Z0-9]", "");

		for (char plainChar : input.toCharArray()) {

			int rowIndex = -1;
			int colIndex = -1;

			// Step 1: Find the position of the plainChar in keySquare
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (keySquare[i][j] == plainChar) {
						rowIndex = i;
						colIndex = j;
						break; // Exit the inner loop
					}
				}
				if (rowIndex != -1 && colIndex != -1) {
					break; // Exit the outer loop as well
				}
			}

			if (rowIndex != -1 && colIndex != -1) {
				// Step 2: Find column[0] value for correspondent row
				char encryptedCharColumn = keySquare[rowIndex][0];
				encryptedText.append(encryptedCharColumn);

				// Step 3: Find row[0] value for correspondent column
				char encryptedCharRow = keySquare[0][colIndex];
				encryptedText.append(encryptedCharRow);
			}
		}

		return encryptedText.toString(); // Return the transposed and encrypted text
	}

	// Rearrange columns of the transposed text based on the keyword
	private String rearrangeColumns(String transposedText) {
		StringBuilder result = new StringBuilder();

		int[] permutationKey = generatePermutationKey(keyword);

		int columns = keyword.length();
		int rows = (int) Math.ceil((double) transposedText.length() / columns);

		for (int col : permutationKey) {
			for (int row = 0; row < rows; row++) {
				int index = col + row * columns; // Calculate index with the rearranged column order
				if (index < transposedText.length()) {
					result.append(transposedText.charAt(index));
				} else {
					result.append('X'); // Fill empty cells with 'X'
				}
			}
		}

		return result.toString(); // Return the rearranged text
	}

	// Generate a permutation key based on the sorted keyword
	private int[] generatePermutationKey(String keyword) {
		String sortedKeyword = sortKeyword(keyword); // Sort the keyword alphabetically
		int[] permutationKey = new int[keyword.length()];

		// Create a mapping of character to its original position in the keyword
		Map<Character, Integer> charToPosition = new HashMap<>();
		for (int i = 0; i < keyword.length(); i++) {
			charToPosition.put(keyword.charAt(i), i);
		}

		// Create the permutation key based on the sorted keyword
		for (int i = 0; i < sortedKeyword.length(); i++) {
			char c = sortedKeyword.charAt(i);
			int originalPosition = charToPosition.get(c);
			permutationKey[i] = originalPosition;
		}

		return permutationKey; // Return the permutation key
	}

	// Sort the characters in the keyword alphabetically
	private String sortKeyword(String keyword) {
		return keyword.chars().mapToObj(c -> (char) c).sorted()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

	// Decrypt ciphertext using the ADFGVX cipher
	public String decrypt(String ciphertext) {
		try {

			String reversedRearrangedText = reverseRearrangedColumns(ciphertext);

			initializeKeySquare(); // Reinitialize the key square for decryption

			String plaintext = convertToPlainText(reversedRearrangedText);

			return plaintext; // Return the decrypted plaintext
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// Reverse the rearranged columns of the ciphertext
	private String reverseRearrangedColumns(String ciphertext) {
		int[] permutationKey = generatePermutationKey(keyword);
		int columns = keyword.length();
		int rows = (int) Math.ceil((double) ciphertext.length() / columns);

		// Create a matrix to represent the rearranged text
		char[][] matrix = new char[rows][columns];
		int charIndex = 0;

		// Fill the matrix with the ciphertext in the rearranged order
		for (int col : permutationKey) {
			for (int row = 0; row < rows; row++) {
				if (charIndex < ciphertext.length()) {
					matrix[row][col] = ciphertext.charAt(charIndex);
					charIndex++;
				}
			}
		}

		// Read the matrix from top to bottom and left to right to get the reversed
		// rearranged text
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (matrix[row][col] != '\u0000') {
					result.append(matrix[row][col]);
				}
			}
		}

		return result.toString(); // Return the reversed rearranged text
	}

	// Convert the reversed rearranged text to plaintext
	private String convertToPlainText(String reversedRearrangedText) {

		StringBuilder plaintext = new StringBuilder();

		for (int i = 0; i < reversedRearrangedText.length(); i += 2) {
			char rowChar = reversedRearrangedText.charAt(i);
			char colChar = reversedRearrangedText.charAt(i + 1);

			int rowIndex = -1;
			int colIndex = -1;

			// Find the row and column indices based on the characters in the current key
			// square
			for (int j = 0; j < 7; j++) {
				if (keySquare[j][0] == rowChar) {
					rowIndex = j;
					break;
				}
			}

			for (int j = 0; j < 7; j++) {
				if (keySquare[0][j] == colChar) {
					colIndex = j;
					break;
				}
			}

			if (rowIndex != -1 && colIndex != -1) {
				plaintext.append(keySquare[rowIndex][colIndex]);
			}
		}
		// Check if the last character is '9' and remove it
		if (plaintext.length() > 0 && plaintext.charAt(plaintext.length() - 1) == '9') {
			plaintext.deleteCharAt(plaintext.length() - 1);
		}

		return plaintext.toString(); // Return the plaintext
	}
}
