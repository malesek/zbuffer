package app;

import view.Window;

import javax.swing.*;

public class AppStart {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            new Controller(window.getPanel());
            window.setVisible(true);
        });
    }

}
