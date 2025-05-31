package org.example;

import org.example.db.DBInitializer;
import org.example.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        new DBInitializer();
        new LoginFrame();
    }
}
