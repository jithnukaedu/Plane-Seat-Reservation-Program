import java.util.Scanner;

public class PlaneManagement {
    // Define the seat prices matrix
    public static final int[][] SEAT_PRICES = {
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180}
    };

    // Define the seat matrix
    public static final int ROWS = 4;
    public static final int[] SEATS_PER_ROW = {14, 12, 12, 14};
    public static final int[][] seats = new int[ROWS][];

    public static final Scanner scanner = new Scanner(System.in);
    public static final Ticket[] tickets = new Ticket[52];

    // Main Method
    public static void main(String[] args) {
        initializeSeats();  // Calling the method to initialize seats
        System.out.println("Welcome to the Plane Management application");   // Display welcome message
        int choice;
        do {  // Show the Menu
            System.out.println("***********************************************************");
            System.out.println("*                        Menu Option                      *");
            System.out.println("***********************************************************");
            System.out.println("           1) Buy a seat");
            System.out.println("           2) Cancel a seat");
            System.out.println("           3) Find first seat available");
            System.out.println("           4) Show seating plan");
            System.out.println("           5) Print tickets information and total sale");
            System.out.println("           6) Search ticket");
            System.out.println("           0) Quit");
            System.out.println("************************************************************");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            // Calling the methods based on user choice
            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    break;
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
        while (choice != 0); // Continue until user chooses to quit
        scanner.close();
    }

    // Method to initialize seats
    public static void initializeSeats() {
        for (int i = 0; i < ROWS; i++) {
            seats[i] = new int[SEATS_PER_ROW[i]];
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = 0;
            }
        }
    }

    // Method to buy a seat
    public static void buy_seat() {
        System.out.print("Enter the row letter (A-D): ");
        char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
        int row = rowLetter - 'A'; // Convert row letter to array index
        if (row < 0 || row >= ROWS) {
            System.out.println("Invalid Row. Please enter a valid Row (A-D).");
            return;
        }

        System.out.print("Enter the seat number: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();
        if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[row]) {
            System.out.println("Invalid seat number for row " + rowLetter + ". Please enter a valid seat number.");
            return;
        }

        // Check if seat is already sold
        if (seats[row][seatNumber - 1] == 1) {
            System.out.println("Seat " + rowLetter + seatNumber + " is already sold.");
            return;
        }

        // Prompt user to enter passenger details
        System.out.print("Enter passenger's name: ");
        String name = scanner.next();
        System.out.print("Enter passenger's surname: ");
        String surname = scanner.next();
        System.out.print("Enter passenger's email: ");
        String email = scanner.next();

        // Create Person and Ticket objects
        Person person = new Person(name, surname, email);
        int price = SEAT_PRICES[row][seatNumber - 1];
        Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);
        ticket.save();

        // Store ticket object in tickets array
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] == null) {
                tickets[i] = ticket;
                break;
            }
        }

        // Mark seat as occupied
        seats[row][seatNumber - 1] = 1;
        System.out.println("Seat " + rowLetter + seatNumber + " has been successfully booked.");
    }

    // Method to cancel a seat reservation
    public static void cancel_seat() {
        System.out.print("Enter the row letter (A-D): ");
        char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
        int row = rowLetter - 'A'; // Convert row letter to array index
        if (row < 0 || row >= ROWS) {
            System.out.println("Invalid row. Please enter a valid row (A-D).");
            return;
        }

        System.out.print("Enter the seat number: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();
        if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[row]) {
            System.out.println("Invalid seat number for row " + rowLetter + ". Please enter a valid seat number.");
            return;
        }

        if (seats[row][seatNumber - 1] == 0) {
            System.out.println("Seat " + rowLetter + seatNumber + " is not occupied.");
            return;
        }

        // Cancel reservation and update seats matrix
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] != null && tickets[i].getSeat() == seatNumber && tickets[i].getRow() == rowLetter) {
                tickets[i] = null;
                break;
            }
        }
        seats[row][seatNumber - 1] = 0;
        System.out.println("Seat reservation for " + rowLetter + seatNumber + " has been successfully canceled.");
    }

    // Method to find the first available seat
    public static void find_first_available() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) {
                if (seats[i][j] == 0) {
                    char rowLetter = (char) ('A' + i);
                    System.out.println("The first available seat is: " + rowLetter + (j + 1));
                    return;
                }
            }
        }
        System.out.println("Sorry, No available seats.");
    }

    // Method to display the seating plan
    public static void show_seating_plan() {
        System.out.println("Seating Plan:");
        for (int i = 0; i < ROWS; i++) {
            char rowLetter = (char) ('A' + i);
            System.out.print(" ");
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) {
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    // Method to print tickets information and total sale
    public static void print_tickets_info() {
        int totalPrice = 0;
        System.out.println("Tickets Information:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.printTicketInfo();
                totalPrice += ticket.getPrice();  // Calculate and print total sale
            }
        }
        System.out.println("Total Sale: £" + totalPrice);
    }

    // Method to search for a ticket by row and seat number
    public static void search_ticket() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter row letter (A-D): ");
        char rowLetter = scanner.next().toUpperCase().charAt(0);
        System.out.print("Enter seat number: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();

        if (rowLetter < 'A' || rowLetter > 'D' || seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowLetter - 'A']) {
            System.out.println("Invalid row letter or seat number.");
            return;
        }

        // Search for ticket matching input and print its information
        boolean found = false;
        for (Ticket ticket : tickets)
            if (ticket != null && ticket.getRow() == rowLetter && ticket.getSeat() == seatNumber) {
                ticket.printTicketInfo();
                found = true;
                break;
            }
        if (!found) {
            System.out.println("This seat is available.");
        }
    }
}
