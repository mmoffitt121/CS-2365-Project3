import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CartItemGUI extends JPanel
{
  public static int PRODUCT_PANEL_WIDTH = 780;
  public static int PRODUCT_PANEL_HEIGHT = 230;
  
  private String font;

  private JPanel holdingpanel;
  private JLabel image;
  
  private Product product;

  CartItemGUI(Shopper shopper, Product _product, int amount)
  {
	product = _product;  
	 
    setPreferredSize(new Dimension(PRODUCT_PANEL_WIDTH, PRODUCT_PANEL_HEIGHT));
    
    font = "Baskerville Old Face";

    holdingpanel = new JPanel();
    holdingpanel.setLayout(new FlowLayout());
    add(holdingpanel);
    add(holdingpanel);

    JPanel content = new JPanel();
    content.setLayout(new FlowLayout());
    holdingpanel.add(content);

    BufferedImage splashimage = shopper.GetImage("graphics/" + product.Image());
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
    
    JLabel amountlabel = new JLabel("<html>Amount: " + amount + "</html>");
    amountlabel.setVerticalAlignment(JLabel.TOP);
    amountlabel.setFont(new Font(font, Font.PLAIN, 16));
    amountlabel.setPreferredSize(new Dimension(100, 20));
    namebox.add(amountlabel);
    
    JButton addtocart = new JButton("Remove");
    addtocart.setPreferredSize(new Dimension(460, 20));
    addtocart.addActionListener(new DelButtonListener(shopper));
    namebox.add(addtocart);
  }
  
  public class DelButtonListener implements ActionListener
  {
    Shopper shopper;
    public void actionPerformed(ActionEvent e)
    {
      shopper.RemoveCartItem(product);
    }

    public DelButtonListener (Shopper _shopper)
    {
      shopper = _shopper;
    }
  }
}