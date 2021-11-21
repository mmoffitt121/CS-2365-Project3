import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShopperGUI extends JFrame
{
  public static final int START_WIDTH = 800;
  public static final int START_HEIGHT = 800;

  public static final int CHARS_VISIBLE_IN_TEXTBOX = 62;

  public static final int BOX_WIDTH = 100;
  public static final int BOX_HEIGHT = 18;

  private JPanel holdingpanel;

  private JTextField searchfield;

  private JLabel splashimagelabel;

  private JScrollPane scroller;
  private JPanel searchresults;

  private void BuildGUI(Shopper shopper)
  {
    setSize(START_WIDTH, START_HEIGHT);
    setResizable(false);
    setTitle("Shopper PRO 6.2");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    holdingpanel = new JPanel();
    holdingpanel.setLayout(new BorderLayout());
    add(holdingpanel);

    JPanel loginpanel = new JPanel();
    loginpanel.setLayout(new FlowLayout());
    holdingpanel.add(loginpanel, BorderLayout.CENTER);

    BufferedImage splashimage = shopper.GetImage("graphics/Shopper PRO WIDE.png");
    if (splashimage != null)
    {
      splashimagelabel = new JLabel(new ImageIcon(splashimage));
      holdingpanel.add(splashimagelabel, BorderLayout.NORTH);
    }

    searchfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    loginpanel.add(searchfield);

    JButton cartbutton = new JButton("Cart");
    cartbutton.addActionListener(new CartButtonListener(shopper));
    //cartbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    holdingpanel.add(cartbutton, BorderLayout.SOUTH);

    JButton loginbutton = new JButton("Go.");
    loginbutton.addActionListener(new SearchButtonListener(shopper));
    loginbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(loginbutton);

    searchresults = new JPanel();
    searchresults.setPreferredSize(new Dimension(780, 600));
    scroller = new JScrollPane(searchresults);
    searchresults.setAutoscrolls(true);
    scroller.setPreferredSize(new Dimension(800, 600));
    loginpanel.add(scroller);
  }

  public void DisplaySearchResults(Shopper shopper, ArrayList<Product> results)
  {
    searchresults.setPreferredSize(new Dimension(780, results.size() * 210));
    for (Product p : results)
    {
      searchresults.add(new ProductGUI(shopper, p));
    }
  }

  public void EmptySearch()
  {
    searchresults.removeAll();
    searchresults.revalidate();
    searchresults.repaint();
  }

  public String GetSearchTerm()
  {
    return searchfield.getText();
  }

  public void MoveToCart()
  {
    remove(holdingpanel);
  }

  public ShopperGUI(Shopper shopper, String lastname, int rewardsnumber)
  {
    super();
    setSize(START_WIDTH, START_HEIGHT);
    setTitle("Shopper PRO 6.2");

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    BuildGUI(shopper);
  }

  public class SearchButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.Search();
    }

    public SearchButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }

  public class CartButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.MoveToCart();
    }

    public CartButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}