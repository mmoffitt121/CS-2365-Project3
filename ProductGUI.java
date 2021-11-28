import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
  ---====================================================================---
  NAME : ProductGUI
  PURPOSE : Holds information of one product read from a file
  ---====================================================================---
*/
public class ProductGUI extends JPanel
{
  public static int PRODUCT_PANEL_WIDTH = 780;
  public static int PRODUCT_PANEL_HEIGHT = 230;
  
  public String font;

  private JPanel holdingpanel;
  private JLabel image;
  
  private Product product;
  
  private JTextField addamount;

  /**
  ---====================================================================---
  NAME : ProductGUI
  PURPOSE : Constructor that builds the product GUI 
  ---====================================================================---
  */
  ProductGUI(Shopper shopper, Product _product)
  {
	product = _product;
	
    setPreferredSize(new Dimension(PRODUCT_PANEL_WIDTH, PRODUCT_PANEL_HEIGHT));
    
    font = "Baskerville Old Face";

    holdingpanel = new JPanel();
    //holdingpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    holdingpanel.setLayout(new FlowLayout());
    add(holdingpanel);
    add(holdingpanel);

    JPanel content = new JPanel();
    content.setLayout(new FlowLayout());
    holdingpanel.add(content/*, BorderLayout.CENTER*/);

    BufferedImage splashimage = shopper.GetImage("graphics/" + product.Image());// + product.Image());
    if (splashimage != null)
    {
      image = new JLabel(new ImageIcon(splashimage));
      image.setPreferredSize(new Dimension(200, 200));
      content.add(image);
    }

    JPanel namebox = new JPanel();
    namebox.setLayout(new FlowLayout());
    namebox.setPreferredSize(new Dimension(600, 230));
    content.add(namebox);

    JLabel productname = new JLabel("<html>" + product.Name() + "</html>");
    productname.setVerticalAlignment(JLabel.TOP);
    productname.setFont(new Font(font, Font.PLAIN, 17));
    productname.setPreferredSize(new Dimension(580, 40));
    namebox.add(productname);

    JLabel price = new JLabel("<html>$ " + product.Price() + "</html>");
    price.setVerticalAlignment(JLabel.TOP);
    price.setFont(new Font(font, Font.PLAIN, 10));
    price.setPreferredSize(new Dimension(580, 10));
    namebox.add(price);

    JLabel desc = new JLabel("<html>" + product.Description() + "</html>");
    desc.setVerticalAlignment(JLabel.TOP);
    desc.setFont(new Font(font, Font.PLAIN, 13));
    desc.setPreferredSize(new Dimension(580, 115));
    namebox.add(desc);

    addamount = new JTextField(10);
    addamount.setText("1");
    namebox.add(addamount);
    
    JButton addtocart = new JButton("Add to cart");
    addtocart.setPreferredSize(new Dimension(450, 20));
    addtocart.addActionListener(new AddToCartListener(shopper));
    namebox.add(addtocart);

   /* JButton cartbutton = new JButton("Cart");
    cartbutton.addActionListener(new CartButtonListener(shopper));
    //cartbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    holdingpanel.add(cartbutton, BorderLayout.SOUTH);

    /*JButton loginbutton = new JButton("Go.");
    loginbutton.addActionListener(new SearchButtonListener(shopper));
    loginbutton.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    loginpanel.add(loginbutton);

    searchresults = new JPanel();
    searchresults.setPreferredSize(new Dimension(480, 300));
    scroller = new JScrollPane(searchresults);
    searchresults.setAutoscrolls(true);
    scroller.setPreferredSize(new Dimension(500, 300));
    loginpanel.add(scroller);*/
  }
  
  /**
  ---====================================================================---
  NAME : AddToCartListener
  PURPOSE : Listener that listens for the add to cart butotn to be pressed.
  ---====================================================================---
  */
  public class AddToCartListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      try
      {
    	  shopper.AddToCart(product, Integer.parseInt(addamount.getText()));
      }
      catch (Exception ex)
      {
    	  return;
      }
    }

    public AddToCartListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}