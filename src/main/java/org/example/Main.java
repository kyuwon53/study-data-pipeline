package org.example;

import org.example.manager.Manager;

public class Main {
    private Manager manager;

    public void start() {
        manager = new Manager();
        manager.run();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}
