import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProductGUI extends JPanel
{
  public static int PRODUCT_PANEL_WIDTH = 780;
  public static int PRODUCT_PANEL_HEIGHT = 200;

  private JPanel holdingpanel;
  private JLabel image;

  ProductGUI(Shopper shopper, Product product)
  {
    setPreferredSize(new Dimension(PRODUCT_PANEL_WIDTH, PRODUCT_PANEL_HEIGHT));

    holdingpanel = new JPanel();
    //holdingpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    holdingpanel.setLayout(new FlowLayout());
    add(holdingpanel);
    add(holdingpanel);

    JPanel content = new JPanel();
    content.setLayout(new FlowLayout());
    holdingpanel.add(content/*, BorderLayout.CENTER*/);

    BufferedImage splashimage = shopper.GetImage("graphics/Home Depot Man.png");// + product.Image());
    if (splashimage != null)
    {
      image = new JLabel(new ImageIcon(splashimage));
      image.setPreferredSize(new Dimension(200, 200));
      content.add(image);
    }

    JPanel namebox = new JPanel();
    namebox.setLayout(new FlowLayout());
    namebox.setPreferredSize(new Dimension(600, 200));
    content.add(namebox);

    JLabel productname = new JLabel("<html>" + product.Name() + "</html>");
    productname.setVerticalAlignment(JLabel.TOP);
    productname.setFont(new Font("Serif", Font.PLAIN, 14));
    productname.setPreferredSize(new Dimension(580, 30));
    namebox.add(productname);

    JLabel price = new JLabel("<html>$ " + product.Price() + "</html>");
    price.setVerticalAlignment(JLabel.TOP);
    price.setFont(new Font("Serif", Font.PLAIN, 10));
    price.setPreferredSize(new Dimension(580, 10));
    namebox.add(price);

    JLabel desc = new JLabel("<html>" + product.Description() + "</html>");
    desc.setVerticalAlignment(JLabel.TOP);
    desc.setFont(new Font("Serif", Font.PLAIN, 11));
    desc.setPreferredSize(new Dimension(580, 165));
    namebox.add(desc);

    /*searchfield = new JTextField(CHARS_VISIBLE_IN_TEXTBOX);
    loginpanel.add(searchfield);

    JButton cartbutton = new JButton("Cart");
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
}