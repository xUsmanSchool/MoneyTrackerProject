import View.ViewFrame;

public class Main {

    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    public Main()
    {

    }

    public void run()
    {
        ViewFrame view = new ViewFrame();
        view.initialize();
    }

}
