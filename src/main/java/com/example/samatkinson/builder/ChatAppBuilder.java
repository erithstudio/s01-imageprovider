package com.example.samatkinson.builder;

import com.example.samatkinson.WizChatApplication;
import com.example.samatkinson.data.UserStore;

public class ChatAppBuilder {
    public ChatAppBuilder withUserStore(UserStore store) {
        return null;
    }

    public WizChatApplication build() {
        return new WizChatApplication();
    }
}
