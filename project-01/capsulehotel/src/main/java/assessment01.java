import java.util.Scanner;

public class assessment01 {
    public static void main(String[] args) {
        //1. Welcome User
        System.out.println("Welcome to Capsule Hotel");
        System.out.println("Enter the number of capsules available: ");
        //2. Take user input for hotel capacity
        Scanner console = new Scanner(System.in);
        int size = Integer.parseInt(console.nextLine()); //size is capacity picked by user
        System.out.println("There are " + size + " unoccupied capsules ready to be booked.");
        //3. Create array of rooms w/ specified capacity
        String rooms[] = new String[size]; //room picked based on size

        boolean exit = false;
        String input;
        //4. Display Main Menu and take user input for menu selection
        //4.1 If selection is check in then go to checkIn method
            //
        //4.2 If selection is check out then go to checkOut method
        //4.3 If selection is view guests then go to viewGuest method
        //4.4 If selection is exit, ask user if they want to exit
            // if they do say goodbye
            // if not return to main menu
        do {
            System.out.println("Main Menu");
            System.out.println("1. Check In");
            System.out.println("2. Check Out");
            System.out.println("3. View Guest");
            System.out.println("4. Exit");

            System.out.print("Select options [1-4]: ");
            input = console.nextLine();

            if (input.equals("1")) {
                checkIn(console, rooms);
            } else if (input.equals("2")) {
                checkOut(console, rooms);
            } else if (input.equals("3")) {
                viewGuests(rooms, console);
            } else if (input.equals("4")) {
                System.out.println("Are you sure you want to exit? (y/n)");
                input = console.nextLine();
                if (input.equals("y")) {
                    exit = true;
                    System.out.println("Goodbye");
                }
            } else {
                System.out.println("I don't understand that option");
            }
        } while (!exit);
    }


    public static void checkIn(Scanner console, String[] rooms ) {

        System.out.println("Check In");
        int selectedRoom;
        System.out.println("Enter your name:");
        String name = console.nextLine();
        System.out.println("Your name is: " + name);
        System.out.println("Pick a capsule number:");
        selectedRoom = Integer.parseInt(console.nextLine());
        if (selectedRoom < 1 || selectedRoom > rooms.length) {         // we want to make sure our room isn't outside the ranfe
            System.out.println("This room is outside of the range");
        } else if ( rooms[selectedRoom -1] != null ) {                  //
            System.out.println("Sorry. Room is occupied");
        } else {
            rooms[selectedRoom - 1] = name; // replaces null with name
            System.out.println("Success :) \n"
                    + name + " is booked in capsule " + selectedRoom--);
        }
    }
    public static void checkOut(Scanner console, String[] rooms) {

        System.out.println("Check Out");
        System.out.println("Pick a capsule number:");
        int selectedRoom = Integer.parseInt(console.nextLine());
        if (selectedRoom < 1 || selectedRoom > rooms.length) {
            System.out.println("Sorry this room is outside of the range");
        } else if (rooms[selectedRoom - 1] == null ) {
            System.out.println("Sorry this room is unoccupied");
        } else {
            System.out.println("Success :)\n" + rooms[selectedRoom - 1] + " checked out from capsule " + selectedRoom--);
            rooms[selectedRoom - 1] = null; // no name = null
        }
    }


    public static void viewGuests(String[] rooms, Scanner console) {

        System.out.println("View Guest");

        int selectRoom;
       do {
           System.out.print("Select capsule number: ");
           selectRoom = Integer.parseInt(console.nextLine());
           if (selectRoom < 1 || selectRoom > rooms.length) {
               System.out.println("This number is not in the range. Try again.");
           }
       } while (selectRoom < 1 || selectRoom > rooms.length);

        int displayRange = 5;
        int minVariable = (selectRoom - 1) - displayRange;
        int maxVariable = (selectRoom - 1) + displayRange;
        if (minVariable < 0){
            minVariable = 0;
        }
        if (maxVariable >= rooms.length){
            maxVariable = rooms.length - 1;
        }

        for (int i = minVariable; i <= maxVariable; i++) {
            System.out.printf("Capsule #%s: %s%n",
                    i + 1, rooms[i] == null ? "[empty]" : rooms[i]);
        }
    }
}
