package parser;

import commands.*;

import java.time.LocalDate;

public class Parser {

    public static Command parse(String userInput) {
        String[] substrings = userInput.split(" ",2);
        String commandWord = substrings[0];
        String arguments = "";
        if(substrings.length > 1) {
            arguments = substrings[1];
        }


        switch (commandWord) {

            case TodoCommand.COMMAND_WORD:
                return prepareTodo(arguments);

            case EventCommand.COMMAND_WORD:
                return prepareEvent(arguments);

            case DeadlineCommand.COMMAND_WORD:
                return prepareDeadline(arguments);

            case DeleteCommand.COMMAND_WORD:
                return prepareDelete(arguments);

//            case FindCommand.COMMAND_WORD:
//                return prepareFind(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case DoneCommand.COMMAND_WORD:
                return prepareDone(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

//            case HelpCommand.COMMAND_WORD: // Fallthrough
            default:
                return new ExitCommand();
        }
    }

    private static Command prepareTodo(String arguments) {
            return new TodoCommand(arguments);
    }

    private static Command prepareEvent(String arguments) {
        //event x /at 2020-01-13
        String[] items = arguments.split(" /at ");
        String desc = "";
        LocalDate date = null;

        try{
            desc = items[0];
            date = LocalDate.parse(items[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect arguments please try again.");
        }

        return new EventCommand(desc, date);
    }

    private static Command prepareDeadline(String arguments) {
        //event x /at 2020-01-13
        String[] items = arguments.split(" /by ");
        String desc = "";
        LocalDate date = null;

        try{
            desc = items[0];
            date = LocalDate.parse(items[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect arguments please try again.");
        }

        return new DeadlineCommand(desc, date);
    }

    private static Command prepareDone(String arguments) {
        final int targetIndex = Integer.parseInt(arguments);
        return new DoneCommand(targetIndex);
    }

    private static Command prepareDelete(String arguments) {
        final int targetIndex = Integer.parseInt(arguments);
        return new DeleteCommand(targetIndex);
    }

    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        ParseException(String message) {
            super(message);
        }
    }
}