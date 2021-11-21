import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Shopper
{
  private LoginGUI logingui;
  private ShopperGUI shoppergui;

  private ArrayList<Customer> customers;
  private ArrayList<Product> products;
  private ArrayList<Cart> carts;

  public void Run()
  {
    logingui = new LoginGUI(this);
    //logingui.setVisible(true);
    ReadCustomers();
    shoppergui = new ShopperGUI(this, "Smith", 256791);
    shoppergui.setVisible(true);
    ReadProducts();
    ReadCarts();
    shoppergui.DisplaySearchResults(this, products);
  }

  // Attempts to read 
  public void LogIn()
  {
    try
    {
      if (customers.contains(new Customer(logingui.GetNameEntry(), logingui.GetRewardEntry())))
      {
        shoppergui = new ShopperGUI(this, logingui.GetNameEntry(), logingui.GetRewardEntry());
        logingui.setVisible(false);
        shoppergui.setVisible(true);
        ReadProducts();
        ReadCarts();
        shoppergui.DisplaySearchResults(this, products);
      }
      else
      {
        logingui.DisplayIncorrect(this);
      }
    }
    catch (NumberFormatException e)
    {
      logingui.DisplayIncorrect(this);
    }
  }

  // Reads customer file
  public void ReadCustomers()
  {
    customers = new ArrayList<Customer>();
    try
    {
      Scanner scn = new Scanner(new File("customers.txt"));

      int amount = scn.nextInt();
      for (int i = 0; i < amount; i++)
      {
        String fname = scn.next();
        String lname = scn.next();
        int rewnumber = scn.nextInt();

        boolean elite;
        if (scn.next().trim().toLowerCase().equals("yes"))
        {
          elite = true;
        }
        else
        {
          elite = false;
        }

        if (elite)
        {
          customers.add(new EliteCustomer(fname, lname, rewnumber, elite, scn.nextDouble(), scn.next(), scn.next()));
        }
        else
        {
          customers.add(new RegularCustomer(fname, lname, rewnumber, elite, scn.nextInt(), scn.next(), scn.next()));
        }
        
      }
    }
    catch(IOException e)
    {
      System.exit(0);
    }
  }

  // Reads product file
  public void ReadProducts()
  {
    products = new ArrayList<Product>();
    try
    {
      Scanner scn = new Scanner(new File("products.txt"));

      int amount = scn.nextInt();
      scn.nextLine();
      for (int i = 0; i < amount; i++)
      {
        String name = scn.nextLine();
        int number = scn.nextInt();
        scn.nextLine();
        String desc = scn.nextLine();
        double price = scn.nextDouble();
        scn.nextLine();
        String img = scn.nextLine();
        products.add(new Product(name, number, desc, price, img));
      }
    }
    catch(IOException e)
    {
      System.exit(0);
    }
  }

  // Reads cart file
  public void ReadCarts()
  {
    carts = new ArrayList<Cart>();
    try
    {
      Scanner scn = new Scanner(new File("carts.txt"));

      int amount = scn.nextInt();
      scn.nextLine();
      for (int i = 0; i < amount; i++)
      {
        Cart cart = new Cart(scn.nextInt());
        int uniqueitemamount = scn.nextInt();
        for (int j = 0; j < uniqueitemamount; j++)
        {
          int id = scn.nextInt();
          int itemamount = scn.nextInt();
          for (int k = 0; k < itemamount; k++)
          {
            cart.AddItem(GetProductOfId(id));
          }
        }

        carts.add(cart);
      }
    }
    catch(IOException e)
    {
      System.exit(0);
    }
  }

  public void Search()
  {
    /*String kwd = shoppergui.GetSearchTerm();

    ArrayList<Product> results = new ArrayList<Product>();
    for (Product i : products)
    {
      if (i.Match(kwd))
      {
        results.add(i);
      }
    }*/

    shoppergui.EmptySearch();
    /*shoppergui.DisplaySearchResults(this, results);*/
  }

  public Product GetProductOfId(int id)
  {
    for (Product p : products)
    {
      if (p.equals(id))
      {
        return new Product(p);
      }
    }
    return null;
  }

  public void MoveToCart()
  {
    shoppergui.MoveToCart();
  }

  public BufferedImage GetImage(String path)
  {
    try
    {
      return ImageIO.read(new File(path));
    }
    catch (IOException e)
    {
      System.out.println("No image found!");
      return null;
    }
  }
}