package start;

import presentation.Controller;
import presentation.MainView;

public class Start {
    public static void main(String[] args) {

        MainView mainView = new MainView();
        Controller controller = new Controller();
        controller.Initialize(mainView);

    }
}
