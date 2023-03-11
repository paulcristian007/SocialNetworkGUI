package com.example.socialnetworkgui.domain;

public class Message {
    private String text;
    private Long idFriendship;
    private Long idUser;
    private Long idx;

    public Message(String text, Long idFriendship, Long idUser) {
        this.text = text;
        this.idFriendship = idFriendship;
        this.idUser = idUser;
    }

    public String getText() {
        return text;
    }

    public Long getIdFriendship() {
        return idFriendship;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }
}
