package chucknorrisencrypter;

import java.util.Scanner;

public class ChuckNorrisEncrypter {

	private static final Scanner sc = new Scanner(System.in);
	private String binaryString;

	public void encrypt(String inputText) {
		String binaryString = convertCharToBinary(inputText);
		String frequencyString = findFrequencyString(binaryString);
		appendZero(frequencyString);
	}

	public void decrypt(String inputCode) {
		boolean allOk = chuckNorrisToBinary(inputCode);
		if (allOk) {
			convertBinaryStringToDecimal();
		}
	}

	// -------------------------------------------ENCRYPT-----------------------------------------------
	private String convertCharToBinary(String inputText) {
		char[] inputArray = inputText.toCharArray();
		binaryString = ""; // making binaryString empty
		for (int i = 0; i < inputArray.length; i++) {
			String temp = Integer.toBinaryString(inputArray[i]);
			if (temp.length() < 7) {
				while (temp.length() != 7) {
					temp = "0" + temp;
				}
			}
			binaryString += temp;
		}
		return binaryString;
	}

	private String findFrequencyString(String binaryString) {
		// If string is "Bookkeeper" then frequencyString for it will be
		// "B1o2k2e2p1e1r1"
		// Similarly if string is, let's say "10011000111" then frequencyString for it
		// will be "1102120313"

		String frequencyString = "";
		// loop till length of binaryString
		for (int i = 0; i < binaryString.length(); i++) {
			// in each loop find the count of binaryString.charAt(i)
			int count = 1;
			while (i + 1 < binaryString.length() && binaryString.charAt(i) == binaryString.charAt(i + 1)) {
				i++;
				count++;
			}
			frequencyString += binaryString.charAt(i) + "" + count;
		}
		return frequencyString;
	}

	private void appendZero(String frequencyString) {
		String encryptedString = "";
		for (int i = 0; i < frequencyString.length(); i = i + 2) {
			if (frequencyString.charAt(i) == '0') {
				encryptedString += "00 ";
			}
			if (frequencyString.charAt(i) == '1') {
				encryptedString += "0 ";
			}
			encryptedString += "0".repeat(Integer.parseInt(String.valueOf(frequencyString.charAt(i + 1))));
			encryptedString += " ";
		}
		System.out.println("Encoded string:\n" + encryptedString);
	}

	// ----------------------------------------------------------DECRYPT-----------------------------------------------
	public boolean isEncodedInputStringValid(String inputCode) {
		for (int i = 0; i < inputCode.length(); i++) {
			if (inputCode.charAt(i) != '0' && inputCode.charAt(i) != ' ') { // if both fail then false
				return false;
			}
		}
		String inputCodeArray[] = inputCode.split(" ");
		if (inputCodeArray.length % 2 != 0) {
			return false;
		}
		for (int i = 0; i < inputCodeArray.length; i = i + 2) { // alternate i
			if (!inputCodeArray[i].equals("0") && !inputCodeArray[i].equals("00")) {
				return false;
			}
		}
		return true;
	}

	public boolean chuckNorrisToBinary(String inputCode) {
		binaryString = ""; // making binaryString empty
		String inputCodeArray[] = inputCode.split(" ");

		// for (int i = 0; i < inCodeArray.length; i++)
		// System.out.println(inCodeArray[i]);

		for (int i = 0; i <= inputCodeArray.length - 2; i = i + 2) {
			String tempStr = "";
			if (inputCodeArray[i].length() == 1) {
				tempStr += '1';
			}
			if (inputCodeArray[i].length() == 2) {
				tempStr += '0';
			}
			int length = inputCodeArray[i + 1].length();
			binaryString += tempStr.repeat(length);
		}
		int lengthOfDecryptedString = binaryString.length();
		if (lengthOfDecryptedString % 7 != 0) { // if it is not a multiple of 7
			System.out.println("Encoded string is not valid.");
			return false;
		}
		return true;
	}

	private void convertBinaryStringToDecimal() {
		String decryptedString = "";
		int i = 0;
		while (i < binaryString.length()) {
			String strSubstring = binaryString.substring(i, i + 7);
			char tempChar = (char) Integer.parseInt(strSubstring, 2); // converting strSubstring to Decimal and then to
																		// eq. (char)
			decryptedString += tempChar;
			i = i + 7;
		}
		System.out.println("Decoded string:\n" + decryptedString);
	}

	// -----------------------------------DRIVER
	// CODE-----------------------------------------------------//
	public static void main(String[] args) {

		String input;
		ChuckNorrisEncrypter game;
		while (true) {
			System.out.println("Please input operation (encode/decode/exit):");
			String choice = sc.nextLine();
			switch (choice) {
			case "encode":
				System.out.println("Input string:");
				input = sc.nextLine();
				game = new ChuckNorrisEncrypter();
				game.encrypt(input);
				break;
			case "decode":
				System.out.println("Input encoded string:");
				input = sc.nextLine();
				game = new ChuckNorrisEncrypter();
				if (game.isEncodedInputStringValid(input)) {
					game.decrypt(input);
				} else {
					System.out.println("Encoded string is not valid.");
				}
				break;
			case "exit":
				System.out.println("Bye!");
				return;
			default:
				System.out.println("There is no '" + choice + "' operation");
				break;
			}// end of switch
		} // end of while
	}// end of main()
}