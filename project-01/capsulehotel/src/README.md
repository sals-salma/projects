# Capsule Hotel Plan

## High Level Requirements

1. On start up, the application prompts the administrator for the hotel's capacity. The capacity determines how many
   capsules are available.
2. The administrator may book a guest in an unoccupied numbered capsule.
3. The administrator may check out a guest from an occupied capsule.
4. The administrator may view guests and their capsule numbers in groups of 11.

### Program Start + Main Menu

* display welcome message (call displayWelcome()).
    * on start prompt the user for a required positive number of rooms (loop until the user gets it right).
    * initialize a String[] of rooms (really this is just guest names)
    * display a main menu (call mainMenu() ) in a loop until the user chooses to exit.

    * implement do while loop
    * prompt user to enter name
    * use if statement (input.equals("1")) etc. for when guest picks option
    * prompt user to enter name; have a scanner console + println guest name

### Check In

* user picks option ("1.Check In")
    * prompt user to pick capsule println("Pick from Capsules #[1-100]: ")
    * user picks unoccupied capsule println("Success! First name + Last name + is booked
    + in capsule %s", capsule[])
    * if capsule is occupied (write an if/else statement) println ("Sorry >_< Capsule is occupied")
    + add loop so user is prompted to choose capsule again

### Check Out

* user picks option ("2. Check out")
  * prompt user to enter their capsule #[]
  * using if/else statements if user wishes to check out prompt user to enter capsule #[]
  + print message ("First name + Last name + "checked out from capsule %s", capsule[])
  + if capsule is unoccupied print message ("capsule#[] is unoccupied")
  

### View Guests

* user picks option ("3. View Guests")
  * prompt to pick from capsule#[]
  * create a variable for displaying 5 smaller and larger
  * what happens if user enters a capsule #[] too small or too big to display 
  + 5 smaller or bigger capsules closest to their input?
    * display whatever is available 

## Tasks 
[comment]: <> (Tasks are pretty identical to the break down each section)



* method for intro + main menu
    * implement displayWelcome() (5 minutes)
    * implement getIntFromUser() (25 minutes)
    * initialize String[] for rooms (5 minutes)
    * implement Scanner console = new Scanner(System)

    * implement a do while loop
    * implement mainMenu() (1.5 hours)
    * implement if/else if/else statements for user input (when they're picking their options from main menu)
    * create another if/else statement in while loop for when user wants to exit program
    * make sure to print a message asking player if they really want leave + exit


* method for check in
    * print out header
    * ask for capsule#[]
    * grab it using int variablename Integer.parseInt(console.nextLine()) and print
    * if/else/if for:
        * if capsule #[] unoccupied
        * if capsule #[] occupied
        * if capsule #[] too small or too big end method

* method for check out
    * print out header
    * ask for capsule#[]
    * if/else if/else statement for user checkout and if capsule#[] unoccupied
    * print message + re-prompt user to enter a diff console#[]


* method for view guests
  * prompt to pick from capsule#[]
  * create a variable inBetween to display 11 capsules closest to that capsule#[]:
  + 5 smaller and 5 larger.
  + if user picks a capsule#[] too small or big to display 5 capsules bigger or samller
  + display what is available

[comment]: <> (things to answer for each section)

[comment]: <> (What are the steps?)

[comment]: <> (What is the step sequence?)

[comment]: <> (Which decisions need to be made?)

[comment]: <> (What repeats &#40;loops&#41;?)

[comment]: <> (What data is needed?)

[comment]: <> (Which data types are appropriate?)

[comment]: <> (Does creating a method simplify the problem?)
