package com.example.socialnetworkgui.validation;
import com.example.socialnetworkgui.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        String msg = "";
        Long id1 = friendship.getId1();
        Long id2 = friendship.getId2();

        if (id1 == null || id2 == null) msg += "The searched user does not exist\n";
        else if (id1.equals(id2)) msg += "You cannot become friend with yourself\n";
        if (msg.length() > 0) throw new ValidationException(msg);
    }
}
