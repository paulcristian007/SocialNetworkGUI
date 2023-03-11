package com.example.socialnetworkgui.domain;
import java.util.Objects;

// Class FriendsRepository - contains 2 users that have become friends.
public class Friendship extends Entity<Long> {
    private Long id1;
    private Long id2;
    private String time;
    private String status;

    public Friendship(Long id1, Long id2, String time, String status) {
        this.id1 = id1;
        this.id2 = id2;
        this.time = time;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object obj)
    {
        // compares the references of the 2 objects
        if (this == obj) return true;

        //
        if (!(obj instanceof Friendship)) return  false;

        // We compare the first name and last name
        Friendship friendship = (Friendship) obj;   // type-cast
        return (Objects.equals(getId1(), friendship.getId1()) &&
                Objects.equals(getId2(), friendship.getId2()))
                || (Objects.equals(getId1(), friendship.getId2()) &&
                Objects.equals(getId2(), friendship.getId1()));
    }
}
