import jdbc.Client;
import jdbc.ClientMenu;

public class Launcher {
    public static void main(String[] args) {
        Client client = new Client();
        ClientMenu clientMenu = new ClientMenu(client);
        clientMenu.start();
    }
}
