import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoginGUI extends JFrame
{
  public static final int START_WIDTH = 300;
  public static final int START_HEIGHT = 360;

  public static final int CHARS_VISIBLE_IN_TEXTBOX = 8;

  public static final int BOX_WIDTH = 200;
  public static final int BOX_HEIGHT = 20;

  private JTextField usernamefield;
  private JTextField rewardnumberfield;

  private JLabel splashimagelabel;

  // --==================================-- //
  // GUI building                           //
  // --==================================-- //

  // GUI builder
  // takes a shopper class as input, builds the GUI of the program
  private void BuildGUI(Shopper shopper)
  {
    setSize(START_WIDTH, START_HEIGHT);
    setResizable(false);
    setTitle("Shopper PRO 6.2");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel holdingpanel = new JPanel();
    holdingpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    holdingpanel.setLayout(new BorderLayout());
    add(holdingpanel);

    JPanel loginpanel = new JPanel();
    loginpanel.setLayout(new FlowLayout());
    holdingpanel.add(loginpanel);

    BufferedImage splashimage = shopper.GetImage("graphics/Home Depot Man.png");
    if (splashimage != null)
    {
      splashimagelabel = new JLabel(new ImageIcon(splashimage));
      loginpanel.add(splashimagelabel);
    }

    JLabel usernamelabel = new JLabel("Last Name:         ");
    loginpanel.add(usernamelabel);
    usernamefield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    loginpanel.add(usernamefield);

    JLabel rewardlabel = new JLabel("Reward Number:");
    loginpanel.add(rewardlabel);
    rewardnumberfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    loginpanel.add(rewardnumberfield);

    JButton loginbutton = new JButton("Log in");
    loginbutton.addActionListener(new LogInButtonListener(shopper));
    loginbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(loginbutton);
  }

  public void DisplayIncorrect(Shopper shopper)
  {
    BufferedImage splashimage = shopper.GetImage("graphics/Home Depot Man Informs You Of Your Mistake.png");
    if (splashimage != null)
    {
      splashimagelabel.setIcon(new ImageIcon(splashimage));
      //loginpanel.add(splashimagelabel);
    }
  }


  // --==================================-- //
  // Value return                           //
  // --==================================-- //

  // Returns the values in the input boxes

  public String GetNameEntry()
  {
    return usernamefield.getText();
  }

  public int GetRewardEntry()
  {
    return Integer.parseInt(rewardnumberfield.getText());
  }


  // --==================================-- //
  // Constructor                            //
  // --==================================-- //

  // LoginGUI constructor
  // takes a shopper class as input to use as its parent.
  public LoginGUI(Shopper shopper)
  {
    super();

    BuildGUI(shopper);
  }


  // --==================================-- //
  // Event Listeners                        //
  // --==================================-- //

  // Login button listener. Fires when login button is pressed.
  public class LogInButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.LogIn();
    }

    public LogInButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}
