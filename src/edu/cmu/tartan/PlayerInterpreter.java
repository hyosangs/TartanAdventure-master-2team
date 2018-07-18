package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;

import java.util.Arrays;

/**
 * This class attempts to interpret player input in a flexible way. It is experimental at best!
 */
public class PlayerInterpreter {

    /**
     * Interpret the input in terms of its action.
     * @param string input string.
     * @return an Action corresponding to the input.
     */
    public Action interpretString(String string) {

        if(string.equals("")) {
            return Action.ACTION_PASS;
        }
        return action(string.toLowerCase().split(" "));
    }

    /**
     * Attempt to select the appropriate action for the given input string
     * @param string the description of what is to be done
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    private Action action(String[] string){
        if(string == null || string.length == 0) {
            return Action.ACTION_PASS;
        }

        Action retVal = null;
        String[] command = getCommendString(string);
        Action action = getActionFromString(command);
        if(action == null){
            return Action.ACTION_ERROR;
        }

        switch(action.type()){
            case TYPE_DIRECTIONAL:
                retVal = action;
                break;

            case TYPE_HASDIRECTOBJECT:
                retVal = getActionHasDirectObject(action, command);
                break;

            case TYPE_HASINDIRECTOBJECT:
                retVal = getActionHasIndirectObject(action, command);
                break;

            case TYPE_HASNOOBJECT:
                retVal = action;
                break;

            case TYPE_UNKNOWN:
                retVal = Action.ACTION_ERROR;
                break;

            default:
                System.out.println("Unknown type");
                retVal = Action.ACTION_ERROR;
                break;
        }

        return retVal;
    }

    private String[] getCommendString(String[] string){
        String[] command = null;
        if(string[0].compareTo("go") == 0 || string[0].compareTo("travel") == 0 || string[0].compareTo("move") == 0){
            command = Arrays.copyOfRange(string, 1, string.length);
        }
        else{
            command = string;
        }

        return command;
    }

    private Action getActionFromString(String[] string){
        String s = string[0];
        Action action = null;

        for( Action a : Action.values()) {
            for(String alias : a.getAliases()) {
                if(s.compareTo(alias) == 0) {
                    action = a;
                    break;
                }
            }
        }

        return action;
    }

    private Action getActionHasDirectObject(Action action, String[] string){
        if(action == null || string == null) {
            return Action.ACTION_ERROR;
        }

        Action retVal=null;
        if(string.length > 1) {
            String d = string[1];
            Item item = Item.getInstance(d);
            // item is the direct object of the action
            action.setDirectObject(item);
            retVal = action;
        }
        else {
            System.out.println("You must supply a direct object.");
            retVal = Action.ACTION_PASS;
        }

        return retVal;
    }

    private Action getActionHasIndirectObject(Action action, String[] string){
        // test if it has indirect object
        // "Take Diamond from Microwave"
        if(action == null || string == null) {
            return Action.ACTION_ERROR;
        }

        Action retVal=null;
        ///////////////////////
        // origin : string.length > 0 --> change : sting.length > 1
        if(string.length > 3) {
            String d = string[1];
            Item item = Item.getInstance(d);
            // item is the direct object of the action
            action.setDirectObject(item);
            String in = string[2];
            if(in.equals("in") || in.equals("from")) {
                String io = string[3];
                Item indob = Item.getInstance(io);
                action.setIndirectObject(indob);
                retVal = action;
            }
            else {
                retVal = Action.ACTION_PASS;
            }
        }
        else {
            System.out.println("You must supply a direct object.");
            retVal = Action.ACTION_ERROR;
        }

        return retVal;
    }

}
