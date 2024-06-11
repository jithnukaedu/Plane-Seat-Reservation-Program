import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor

    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information

    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: " + price);
        System.out.println("Person Information:");
        person.printInfo();
    }

    // Method to save ticket information to a file

    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("Ticket Information:");
            fileWriter.write("Row: " + row);
            fileWriter.write("Seat: " + seat);
            fileWriter.write("Price: " + price);
            fileWriter.write("Person Information:");
            fileWriter.write("Name: " + person.getName());
            fileWriter.write("Surname: " + person.getSurname());
            fileWriter.write("Email: " + person.getEmail());
            fileWriter.close();
            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving ticket information to file.");
            e.printStackTrace();
        }
    }
}
