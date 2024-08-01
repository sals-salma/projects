package learn.solar.ui;

import learn.solar.models.Material;

import java.util.Scanner;

public class Console {
    Scanner scn = new Scanner(System.in);
    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String getString( String prompt ){
        System.out.print( prompt );
        return scn.nextLine();
    }
    public int readInt(String prompt) {
        while (true) {
            String value = getString(prompt);
            try {
                int result = Integer.parseInt(value);
                return result;
            } catch (NumberFormatException ex) {
                printf("'%s' is not a valid number.%n", value);
            }
        }
    }

    public String getRequiredString( String prompt ){
        String userInput = getString( prompt );
        while( userInput.isBlank() ){
            userInput = getString(prompt);
        }
        return userInput;
    }

    public String editRequiredString( String prompt, String oldValue ){
        //get a string from the user
        //but if they just press enter, output the original string
        prompt = prompt + " (" + oldValue +  ") : ";
        String response = getString(prompt);
        if( response.isBlank() ){
            response = oldValue;
        }

        return response;
    }

    public int getInt( String prompt ){
        int toReturn = Integer.MIN_VALUE;

        boolean validInput = false;
        while( !validInput ) {
            String userInput = getRequiredString(prompt);
            try {
                toReturn = Integer.parseInt(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {
                //maybe print a message
                //saying "that's not an integer/valid choice
                //maybe do nothing since the user is dumb
                //and could fail an unlimited number of times
                //we'll loop and re-print the prompt anyway
            }
        }

        return toReturn;
    }

    public int editInt(String prompt, int incMin, int incMax, int originalVal) {
        prompt = prompt + " (" + originalVal + "): ";
        int toReturn = Integer.MIN_VALUE;
        boolean validInput = false;
        while( !validInput ){
            String userInput = getString(prompt);
            if(userInput.isEmpty()) return originalVal;
            try{
                toReturn = Integer.parseInt(userInput);
                if( toReturn >= incMin && toReturn <= incMax){
                    validInput = true;
                }
            } catch (NumberFormatException ex){
                //do nothing
            }
        }

        return toReturn;
    }

    public int getInt( String prompt, int incMin, int incMax ){
        int toReturn = Integer.MIN_VALUE;
        boolean validInput = false;
        while( !validInput ){
            toReturn = getInt(prompt);
            if( incMin <= toReturn && toReturn <= incMax ){
                validInput = true;
            }
            //could be shortened to validInput = incMin <= toReturn && toReturn <= incMax
        }

        return toReturn;
    }


    public boolean getBoolean(String prompt) {
        String input = getRequiredString(prompt);
        while (true) {
            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n")){
                return false;
            }
            input = getRequiredString(prompt);
        }
    }

    public boolean editBoolean(String prompt, boolean priorValue) {
        prompt = prompt + " (" + (priorValue ? "y" : "n") +  ") : ";
        String input = getString(prompt);
        while (true){
            if(input.isEmpty()){
                return priorValue;
            }
            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n")){
                return false;
            }
            input = getRequiredString(prompt);
        }
    }
}
