package org.ebu6304gp42.exception.account;

public class IllegalInputException extends Exception {
    String reason;
    public IllegalInputException(String reason) {
        super();
        this.reason = reason;
    }

    public String getReason(){return reason;}
}
