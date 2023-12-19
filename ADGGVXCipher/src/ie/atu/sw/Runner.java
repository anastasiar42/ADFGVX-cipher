package ie.atu.sw;

public class Runner {
	public static void main(String[] args) {
		try {
			// Create a new instance of the Menu class
			Menu m = new Menu();

			// Start the menu, which allows the user to interact with the application
			m.start();
		} catch (Exception e) {
			// If an exception is caught, print an error message and the stack trace
			System.err.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
}