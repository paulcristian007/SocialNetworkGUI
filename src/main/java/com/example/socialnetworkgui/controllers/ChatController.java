package com.example.socialnetworkgui.controllers;
import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.service.ServiceMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ChatController extends FriendsListController {
    private Long lastAddedMsg;
    private ServiceMessages srv;

    @FXML
    private ListView<String> chatView;

    @FXML
    private Button closeChatButton;

    @FXML
    private TextField messageField;

    @FXML
    private Button refreshChatClicked;

    @FXML
    private Button sendMessageButton;

    ObservableList<String> model = FXCollections.observableArrayList();

    @Override
    public void update() {
        List<Message> messages = srv.receiveMessages(lastAddedMsg);
        if (messages.size() > 0) {
            for (Message msg : messages) {
                if (Objects.equals(msg.getIdUser(), idUser))
                    model.add("Me:  " + msg.getText());
                else
                    model.add("You: " + msg.getText());
            }

            lastAddedMsg = messages.get(messages.size() - 1).getIdx();
        }
    }


    @Override
    protected void prepare() {
        chatView.setItems(model);
        srv = createServiceMessages();
        srv.setIdFriendship(idUser, firstName, lastName);
        lastAddedMsg = 0L;

        for (Message msg : srv.receiveMessages(lastAddedMsg)) {
            if (Objects.equals(msg.getIdUser(), idUser))
                model.add("Me:  " + msg.getText());
            else
                model.add("You: " + msg.getText());
            lastAddedMsg++;
         }
    }


    @FXML
    void chatViewClicked(MouseEvent event) {

    }

    @FXML
    void closeChatClicked(ActionEvent event) throws IOException {
        switchScenes(closeChatButton, "FriendsListView.fxml", idUser);
    }

    @FXML
    void refreshChatClicked(ActionEvent event) {
    }

    @FXML
    void sendMessageClicked(ActionEvent event) {
        String msg = messageField.getText();
        messageField.clear();
        model.add("Me: " + msg);
        srv.sendMessage(msg);
        lastAddedMsg++;
        subject.makeNotification();
    }

}
