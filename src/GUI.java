import java.awt.*;
import java.io.Serializable;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**
 * Most simple Zuul graphical user interface class.
 * It can be used as an alternative to the console text interface.
 * Four buttons allow the player to walk to a neighboring room.
 * The text area gives feedback to the user.
 *
 * @author Wolfgang Renz
 * @version 2019.05.16
 */
public class GUI implements PlayerUI, Serializable
{
    private Player player;
    private JTextArea textArea;
    private JTextField textField;
    DefaultCaret caret;

    /**
     * Constructor for objects of class GUI
     */
    public GUI(Player p)
    {
        player = p;
        System.out.println("Gui for player: "+ "\"" + player.getName()+ "\" created!");
        createFrame(player.getName(), 30, 40);
    }

    private void createFrame(String name, int len, int wid)
    {
        JFrame frame= new JFrame(name);
        frame.setVisible(false);

        textArea = new JTextArea(len, wid);
        caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Container contentPane= frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);        
        
        //direction buttons panel
        JPanel directionButtons = new JPanel();        
        BoxLayout boxLayout = new BoxLayout(directionButtons, BoxLayout.Y_AXIS);
        directionButtons.setLayout(boxLayout);        
        directionButtons.add(activeButton("north"));
        directionButtons.add(activeButton("south"));
        directionButtons.add(activeButton("east"));
        directionButtons.add(activeButton("west"));
        contentPane.add(directionButtons, BorderLayout.EAST);

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem item1, item2;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Menu");
        item1 = new JMenuItem("New Player");
        item2 = new JMenuItem("Quit game");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menu.add(item1);
        menu.add(item2);

        item1.addActionListener(e -> {
        String newName;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter new player name:");
        newName = scan.nextLine();
        player.addMe();
        });

        item2.addActionListener(e -> {
            QuitCommand quitCmd = new QuitCommand();
            quitCmd.setSecondWord(null);
            quitCmd.execute(player);
            closeWindow(frame);
        });

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        textField = new JTextField();
        contentPane.add(textField, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private JButton activeButton(String direction)
    {
        JButton button = new JButton(direction);
        button.addActionListener(new ActionListener()
        // anonymous class definition implementing the ActionListener Interface
            {   
                public void actionPerformed(ActionEvent e)
                {
                    GoCommand cmd= new GoCommand();
                    cmd.setSecondWord(button.getLabel());
                    try {
                        cmd.execute(player);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }  
        );
        return button;
    }

    public void println(String s)
    {
        textArea.append(s+ "\n");
    }

    @Override
    public void start() {

    }

    public void closeWindow(JFrame frameToClose){
        frameToClose.dispose();
    }
}
