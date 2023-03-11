package com.example.socialnetworkgui.validation;
import com.example.socialnetworkgui.domain.User;

public class UserValidator implements Validator<User> {
    String validateName(String firstName) {
        String msg = "";
        char letter = firstName.charAt(0);
        if (!(Character.compare(letter, 'A') >= 0 && Character.compare(letter, 'Z') <= 0))
            msg += "First letter of first name must be upper case!\n";

        for (int i = 1; i < firstName.length();  i++) {
            char ch = firstName.charAt(i);
            if (!(Character.compare(ch, 'a') >= 0 && Character.compare(ch, 'z') <= 0)) {
                msg += "You can use only letter in first name!\n";
                break;
            }
        }

        return msg;
    }

    String validateAge(int age) {
        String msg = "";
        if (age < 18)
            msg += "You are not old enough to join the Social Network\n";
        return msg;
    }

    String validatePassword(String password) {
        String msg = "";
        if (password.length() < 6)
            msg += "Password must be at least 6 characters long!\n";
        return msg;
    }

    @Override
    public void validate(User user) throws ValidationException  {
        String msg = "";
        msg += validateName(user.getFirstName());
        msg += validateName(user.getLastName());
        msg += validatePassword(user.getPassword());
        msg += validateAge(user.getAge());

        if (msg.length() > 0)
            throw new ValidationException(msg);
    }
}
