import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
  ---====================================================================---
  NAME : LoginGUI
  PURPOSE : Holds GUI for login panel
  ---====================================================================---
*/
public class LoginGUI extends JFrame
{
  public static final int START_WIDTH = 300;
  public static final int START_HEIGHT = 390;

  public static final int CHARS_VISIBLE_IN_TEXTBOX = 8;

  public static final int BOX_WIDTH = 200;
  public static final int BOX_HEIGHT = 20;

  private JTextField usernamefield;
  private JTextField rewardnumberfield;
  
  private JTextField firstnamefield, lastnamefield, emailfield, phonenumberfield;
  private JCheckBox elitefield;
  
  private JPanel outerpanel;

  private JLabel splashimagelabel;
  
  private JPanel holdingpanel;
  private JPanel registrationpanel;

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
    
    outerpanel = new JPanel();
    outerpanel.setLayout(new BorderLayout());
    add(outerpanel);
    
    // Initialize login panel ---

    holdingpanel = new JPanel();
    holdingpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    holdingpanel.setLayout(new BorderLayout());
    outerpanel.add(holdingpanel);

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
    
    JButton registerbutton = new JButton("Register");
    registerbutton.addActionListener(new RegisterButtonListener(shopper));
    registerbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(registerbutton);
    
    // Initialize registration panel ---
    
    registrationpanel = new JPanel();
    registrationpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    registrationpanel.setLayout(new BorderLayout());
    
    JPanel regpanel = new JPanel();
    regpanel.setLayout(new FlowLayout());
    registrationpanel.add(regpanel);

    BufferedImage regimage = shopper.GetImage("graphics/Registration Day.png");
    if (regimage != null)
    {
      JLabel regimagelabel = new JLabel(new ImageIcon(regimage));
      regpanel.add(regimagelabel);
    }
    
    JLabel firstnamelabel = new JLabel("First Name:         ");
    regpanel.add(firstnamelabel);
    firstnamefield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    regpanel.add(firstnamefield);

    JLabel lastnamelabel = new JLabel("Last Name:         ");
    regpanel.add(lastnamelabel);
    lastnamefield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    regpanel.add(lastnamefield);
    
    JLabel emaillabel = new JLabel("Email Address:   ");
    regpanel.add(emaillabel);
    emailfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    regpanel.add(emailfield);

    JLabel phonenumberlabel = new JLabel("Phone Number:   ");
    regpanel.add(phonenumberlabel);
    phonenumberfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    regpanel.add(phonenumberfield);
    
    JLabel elitelabel = new JLabel("Are you feeling elite?");
    regpanel.add(elitelabel);
    elitefield = new JCheckBox();
    regpanel.add(elitefield);
    
    JButton registerbuttonfinal = new JButton("Register");
    registerbuttonfinal.addActionListener(new RegisterFinalizeButtonListener(shopper));
    registerbuttonfinal.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    regpanel.add(registerbuttonfinal);
  }

  /**
    ---====================================================================---
    NAME : DisplayIncorrect
    PURPOSE : Tells you when you input the incorrect login information,
    displays new image.
    ---====================================================================---
  */
  public void DisplayIncorrect(Shopper shopper)
  {
    BufferedImage splashimage = shopper.GetImage("graphics/Home Depot Man Informs You Of Your Mistake.png");
    if (splashimage != null)
    {
      splashimagelabel.setIcon(new ImageIcon(splashimage));
      //loginpanel.add(splashimagelabel);
    }
  }
  
  /**
    ---====================================================================---
    NAME : DisplayRegistration
    PURPOSE : Displays the registration panel 
    ---====================================================================---
  */
  public void DisplayRegistration()
  {
	  outerpanel.removeAll();
	  outerpanel.revalidate();
	  outerpanel.repaint();
	  outerpanel.add(registrationpanel);
  }
  
  /**
    ---====================================================================---
    NAME : DisplayLogin
    PURPOSE : Displays the login panel 
    ---====================================================================---
  */
  public void DisplayLogin()
  {
	  outerpanel.removeAll();
	  outerpanel.revalidate();
	  outerpanel.repaint();
	  outerpanel.add(holdingpanel);
  }
  
  /**
    ---====================================================================---
    NAME : DisplayNumber
    PURPOSE : Displays the new user's reward number
    ---====================================================================---
  */
  public void DisplayNumber(int rewardsnumber)
  {
	  String r = "" + rewardsnumber;
	  for (int i = r.length(); i < 8; i++)
	  {
		  r = "0" + r;
	  }
	  
	  JOptionPane.showMessageDialog(null, "Your rewards number is: " + r, "Your rewards number", JOptionPane.INFORMATION_MESSAGE);
  }
  
  /**
    ---====================================================================---
    NAME : Getters
    PURPOSE : Grabs values from the registration panel
    ---====================================================================---
  */
  public String GetFirstName()
  {
	  return firstnamefield.getText();
  }
  
  public String GetLastName()
  {
	  return lastnamefield.getText();
  }
  
  public String GetEmail()
  {
	  return emailfield.getText();
  }
  
  public String GetPhoneNumber()
  {
	  return phonenumberfield.getText();
  }
  
  public boolean GetElite()
  {
	  return elitefield.isSelected();
  }


  /**
    ---====================================================================---
    NAME : More getters
    PURPOSE : Grabs values from the login panel
    ---====================================================================---
  */
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
  
  public class RegisterButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.StartRegistration();
    }

    public RegisterButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
  
  public class RegisterFinalizeButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.Register();
    }

    public RegisterFinalizeButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}
