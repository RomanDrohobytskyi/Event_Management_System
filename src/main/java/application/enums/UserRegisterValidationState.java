package application.enums;

public enum UserRegisterValidationState {
    EMPTY_EMAIL("Sorry, Your email is empty, please check it again"),
    USER_EXIST("Sorry, user with this email already exist!"),
    PASSWORDS_NOT_MATCHING("Sorry, but Your passwords do not match, check it again!"),
    CODE_SENDING_FAILED("We can`t send to You activation code, sorry!"),
    SUCCESS("Success");

    public final String state;

    private UserRegisterValidationState(String state){
        this.state = state;
    }


}
