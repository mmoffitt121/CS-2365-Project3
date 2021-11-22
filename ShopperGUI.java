import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

// ShopperGUI
public class ShopperGUI extends JFrame
{
  public static final int START_WIDTH = 814;
  public static final int START_HEIGHT = 836;

  public static final int CHARS_VISIBLE_IN_TEXTBOX = 62;

  public static final int BOX_WIDTH = 90;
  public static final int BOX_HEIGHT = 18;
  
  private JPanel outerframe;

  private JPanel shopholdingpanel;
  private JPanel cartholdingpanel;

  private JTextField searchfield;

  private JLabel splashimagelabel;

  private JScrollPane scroller;
  private JPanel searchresults;
  
  private JScrollPane cscroller;
  private JPanel cartitems;
  
  private JLabel total;

  private void BuildShopGUI(Shopper shopper)
  {
	// Window initialization ---
	
    setSize(START_WIDTH, START_HEIGHT);
    setResizable(false);
    setTitle("Shopper PRO 6.2");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
    outerframe = new JPanel();
    outerframe.setLayout(new BorderLayout());
    add(outerframe);
    
    // Shop initialization ---

    shopholdingpanel = new JPanel();
    shopholdingpanel.setLayout(new BorderLayout());
    outerframe.add(shopholdingpanel);

    JPanel loginpanel = new JPanel();
    loginpanel.setLayout(new FlowLayout());
    shopholdingpanel.add(loginpanel, BorderLayout.CENTER);

    BufferedImage splashimage = shopper.GetImage("graphics/Shopper PRO WIDE.png");
    if (splashimage != null)
    {
      splashimagelabel = new JLabel(new ImageIcon(splashimage));
      shopholdingpanel.add(splashimagelabel, BorderLayout.NORTH);
    }
    
    JLabel welcomelabel;
    try
    {
    	welcomelabel = new JLabel("Welcome, " + shopper.GetUser().FName() + " " + shopper.GetUser().LName() + ".");
    }
    catch (Exception e)
    {
    	welcomelabel = new JLabel("Welcome, valued customer.");
    }
    
    welcomelabel.setPreferredSize(new Dimension(685, BOX_HEIGHT));
    loginpanel.add(welcomelabel);
    
    JButton logoutbutton = new JButton("Log Out.");
    logoutbutton.addActionListener(new LogOutListener(shopper));
    logoutbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(logoutbutton);

    searchfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    loginpanel.add(searchfield);

    JButton cartbutton = new JButton("Cart");
    cartbutton.addActionListener(new CartButtonListener(shopper));
    //cartbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    shopholdingpanel.add(cartbutton, BorderLayout.SOUTH);

    JButton loginbutton = new JButton("Go.");
    loginbutton.addActionListener(new SearchButtonListener(shopper));
    loginbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(loginbutton);

    searchresults = new JPanel();
    searchresults.setPreferredSize(new Dimension(780, 600));
    scroller = new JScrollPane(searchresults);
    searchresults.setAutoscrolls(true);
    scroller.setPreferredSize(new Dimension(800, 623));
    scroller.getVerticalScrollBar().setUnitIncrement(20);
    loginpanel.add(scroller);
    
    // Cart initialization ---
    
    cartholdingpanel = new JPanel();
    cartholdingpanel.setLayout(new BorderLayout());

    JPanel cartpanel = new JPanel();
    cartpanel.setLayout(new FlowLayout());
    cartholdingpanel.add(cartpanel, BorderLayout.CENTER);

    BufferedImage cartimage = shopper.GetImage("graphics/Cart.png");
    if (cartimage != null)
    {
      JLabel cartimagelabel = new JLabel(new ImageIcon(cartimage));
      cartholdingpanel.add(cartimagelabel, BorderLayout.NORTH);
    }
    
    JPanel bottombox = new JPanel();
    cartpanel.setLayout(new FlowLayout());
    cartholdingpanel.add(bottombox, BorderLayout.SOUTH);
    
    JButton shopbutton = new JButton("Back to Shopping");
    shopbutton.addActionListener(new CartButtonListener(shopper));
    shopbutton.setPreferredSize(new Dimension(400, 26));
    bottombox.add(shopbutton);
    
    total = new JLabel("             Items in cart: 0000 Total: $ 00.00                   ");
    bottombox.add(total);

    cartitems = new JPanel();
    cartitems.setPreferredSize(new Dimension(780, 1000));
    cscroller = new JScrollPane(cartitems);
    cartitems.setAutoscrolls(true);
    cscroller.setPreferredSize(new Dimension(800, 623));
    cscroller.getVerticalScrollBar().setUnitIncrement(20);
    cartholdingpanel.add(cscroller);
  }

  public void DisplaySearchResults(Shopper shopper, ArrayList<Product> results)
  {
    searchresults.setPreferredSize(new Dimension(780, results.size() * 240));
    for (Product p : results)
    {
      searchresults.add(new ProductGUI(shopper, p));
    }
  }
  
  public void DisplayCartResults(Shopper shopper, Cart results, Customer customer)
  {
	  EmptyCartResults();
	  cartitems.setPreferredSize(new Dimension(780, results.Items().size() * 240));
	  
	  int totalitems = 0;
	  double totalcost = 0;
      ArrayList<Product> printed = new ArrayList<Product>();
	  ArrayList<Integer> count = new ArrayList<Integer>();
	  for (int i = 0; i < results.Items().size(); i++)
	  {
		  totalitems++;
		  totalcost += results.Items().get(i).Price();
		  if (printed.contains(results.Items().get(i)))
		  {
			  count.set(printed.indexOf(results.Items().get(i)), count.get(printed.indexOf(results.Items().get(i))) + 1);
		  }
		  else
		  {
			  printed.add(results.Items().get(i));
			  count.add(1);
		  }
	  }
	  
	  cartitems.setPreferredSize(new Dimension(780, printed.size() * 240));
	  
	  for (int i = 0; i < printed.size(); i++)
	  {
		  cartitems.add(new CartItemGUI(shopper, printed.get(i), count.get(i)));
	  }
	  
	  double discount;
	  if (customer.elite())
	  {
		  discount = ((EliteCustomer)customer).GetPercentDiscount();
	  }
	  else
	  {
		  discount = 0;
	  }
	  
	  totalcost = totalcost - totalcost*discount;
	  
	  total.setText(String.format("   Items in cart: %d    Discount: %%%.0f   Total: $%.2f    ", totalitems, discount * 100, totalcost));
  }

  public void EmptySearch()
  {
    searchresults.removeAll();
    searchresults.revalidate();
    searchresults.repaint();
  }
  
  public void EmptyCartResults()
  {
	 cartitems.removeAll();
	 cartitems.revalidate();
	 cartitems.repaint();
  }
  
  public void EmptyScreen()
  {
	outerframe.removeAll();
	outerframe.revalidate();
	outerframe.repaint();
  }

  public String GetSearchTerm()
  {
    return searchfield.getText();
  }

  public void DisplayCart()
  {
    EmptyScreen();
    outerframe.add(cartholdingpanel);
  }
  
  public void DisplayShop()
  {
	EmptyScreen();
	outerframe.add(shopholdingpanel);  
  }

  public ShopperGUI(Shopper shopper)
  {
    super();
    setSize(START_WIDTH, START_HEIGHT);
    setTitle("Shopper PRO 6.2");

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    BuildShopGUI(shopper);
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
      if (!shopper.InCart())
      {
    	  shopper.DisplayCart();
      }
      else
      {
    	  shopper.DisplayShop();
      }
    }

    public CartButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
  
  public class LogOutListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.LogOut();
    }

    public LogOutListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}