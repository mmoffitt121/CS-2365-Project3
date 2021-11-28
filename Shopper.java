import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
  ---====================================================================---
  NAME : Shopper
  PURPOSE : Model in the Controller-View-Model pattern. Controls the inner
  workings of the program, and navigation throughout the program.
  ---====================================================================---
*/
public class Shopper
{
  private LoginGUI logingui;
  private ShopperGUI shoppergui;
  
  private Customer customer;

  private ArrayList<Customer> customers;
  private ArrayList<Product> products;
  private ArrayList<Cart> carts;
  
  private Cart cart;
  
  private boolean incart;

  /**
  ---====================================================================---
  NAME : Run
  PURPOSE : Opens the Login GUI, called when program starts
  ---====================================================================---
  */
  public void Run()
  {
    logingui = new LoginGUI(this);
    logingui.setVisible(true);
    ReadCustomers();
  }

  /**
  ---====================================================================---
  NAME : LogIn
  PURPOSE : Logs in to the program, opens the shopper gui, readies data
  ---====================================================================---
  */
  public void LogIn()
  {
    try
    {
      if (customers.contains(new Customer(logingui.GetNameEntry(), logingui.GetRewardEntry())))
      {
    	String user = logingui.GetNameEntry();
    	int userrewardnumber = logingui.GetRewardEntry();
    	customer = customers.get(customers.indexOf(new Customer(user, userrewardnumber)));
        shoppergui = new ShopperGUI(this);
        logingui.setVisible(false);
        shoppergui.setVisible(true);
        ReadProducts();
        ReadCarts();
        cart = GetCart(customer);
        shoppergui.DisplaySearchResults(this, products);
        shoppergui.DisplayCartResults(this, cart, customer);
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

  /**
  ---====================================================================---
  NAME : ReadCustomers
  PURPOSE : Reads the customers from an input file to the system.
  INVARIANT : Requires customers.txt to be present and readable.
  ---====================================================================---
  */
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

  /**
  ---====================================================================---
  NAME : ReadProducts
  PURPOSE : Reads the products from an input file to the system.
  INVARIANT : Requires products.txt to be present and readable.
  ---====================================================================---
  */
  public void ReadProducts()
  {
    products = new ArrayList<Product>();
    try
    {
      Scanner scn = new Scanner(new File("products.txt"), "utf-8");

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

  /**
  ---====================================================================---
  NAME : ReadCarts
  PURPOSE : Reads the carts from an input file to the system.
  INVARIANT : Requires carts.txt to be present and readable.
  ---====================================================================---
  */
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
  
  /**
  ---====================================================================---
  NAME : WriteCarts
  PURPOSE : Saves cart data to carts.txt
  ---====================================================================---
  */
  public void WriteCarts()
  {
	  try
	  {
		  File cartfile = new File("carts.txt");
		  cartfile.delete();
		  cartfile.createNewFile();
		 
		  for (int i = carts.size()-1; i >= 0; i--)
		  {
			  if (carts.get(i).Items().size() == 0)
			  {
				  carts.remove(i);
			  }
		  }
		  
		  PrintWriter writer = new PrintWriter(cartfile);
		  writer.println(carts.size());
		  
		  for (Cart c : carts)
		  {
			  writer.println(c.RewNumber());
			  
			  ArrayList<Product> printed = new ArrayList<Product>();
			  ArrayList<Integer> count = new ArrayList<Integer>();
			  for (int i = 0; i < c.Items().size(); i++)
			  {
				  if (printed.contains(c.Items().get(i)))
				  {
					  count.set(printed.indexOf(c.Items().get(i)), count.get(printed.indexOf(c.Items().get(i))) + 1);
				  }
				  else
				  {
					  printed.add(c.Items().get(i));
					  count.add(1);
				  }
			  }
			  
			  writer.println(printed.size());
			  
			  for (int i = 0; i < printed.size(); i++)
			  {
				  writer.print(printed.get(i).Number() + " ");
				  writer.println(count.get(i));
			  }
		  }
		  
		  writer.close();
	  }
	  catch (IOException e)
	  {
		  return;
	  }
  }
  
  /**
  ---====================================================================---
  NAME : WriteCustomers
  PURPOSE : Saves customer data to customers.txt
  ---====================================================================---
  */
  public void WriteCustomers()
  {
	  try
	  {
		  File cfile = new File("customers.txt");
		  cfile.delete();
		  cfile.createNewFile();
		  
		  PrintWriter writer = new PrintWriter(cfile);
		  writer.println(customers.size());
		  
		  for (Customer c : customers)
		  {
			  writer.println(c.FName());
			  writer.println(c.LName());
			  if (c.elite())
			  {
				  writer.println(c.RewNumber() + " Yes");
				  EliteCustomer cc = (EliteCustomer)c;
				  writer.println(cc.GetPercentDiscount());
			  }
			  else
			  {
				  writer.println(c.RewNumber() + " No");
				  RegularCustomer cc = (RegularCustomer)c;
				  writer.println(cc.GetStars());
			  }
			  writer.println(c.Email());
			  writer.println(c.PhNumber());
		  }
		  
		  writer.close();
	  }
	  catch (IOException e)
	  {
		  return;
	  }
  }
  
  /**
  ---====================================================================---
  NAME : Search
  PURPOSE : Searches the product files, updates the gui.
  ---====================================================================---
  */
  public void Search()
  {
    String kwd = shoppergui.GetSearchTerm();

    ArrayList<Product> results = new ArrayList<Product>();
    for (Product i : products)
    {
      if (i.Match(kwd))
      {
        results.add(i);
      }
    }

    shoppergui.EmptySearch();
    shoppergui.DisplaySearchResults(this, results);
  }
  
  /**
  ---====================================================================---
  NAME : GetCart
  PURPOSE : Gets cart associated with a specific customer
  ---====================================================================---
  */
  public Cart GetCart(Customer cust)
  {
	  if (carts.contains(new Cart(cust.RewNumber())))
	  {
		  return carts.get(carts.indexOf(new Cart(cust.RewNumber())));
	  }
	  else
	  {
		  Cart toreturn = new Cart(cust.RewNumber());
		  carts.add(toreturn);
		  return toreturn;
	  }
  }

  /**
  ---====================================================================---
  NAME : GetProductOfId
  PURPOSE : Gets product of input id
  ---====================================================================---
  */
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
  
  /**
  ---====================================================================---
  NAME : GetUser
  PURPOSE : Returns the customer held in the shopper
  ---====================================================================---
  */
  public Customer GetUser()
  {
	  return customer;
  }

  // Displays cart to user
  public void DisplayCart()
  {
	  incart = true;
    shoppergui.DisplayCart();
  }
  
  // Displays shop to user
  public void DisplayShop()
  {
	  incart = false;
	  shoppergui.DisplayShop();
  }
  
  // Checks if item in cart
  public boolean InCart()
  {
	  return incart;
  }
  
  /**
  ---====================================================================---
  NAME : AddToCart
  PURPOSE : Adds specified product to cart amounttoadd amount of times
  ---====================================================================---
  */
  public void AddToCart(Product toadd, int amounttoadd)
  {
	  for (int i = 0; i < amounttoadd; i++)
	  {
		  cart.AddItem(toadd);
	  }
	  shoppergui.DisplayCartResults(this, cart, customer);
  }
  
  /**
  ---====================================================================---
  NAME : RemoveCartItem
  PURPOSE : Removes specified item from cart
  ---====================================================================---
  */
  public void RemoveCartItem(Product prod)
  {
	  cart.RemoveItem(prod);
	  shoppergui.DisplayCartResults(this, cart, customer);
  }

  /**
  ---====================================================================---
  NAME : GetImage
  PURPOSE : Gets image from path
  ---====================================================================---
  */
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
  
  /**
  ---====================================================================---
  NAME : StartRegistration
  PURPOSE : Displays the registration panel
  ---====================================================================---
  */
  public void StartRegistration()
  {
	  logingui.DisplayRegistration();
  }
  
  /**
  ---====================================================================---
  NAME : Register
  PURPOSE : Grabs all items from registration panel, creates new customer
  ---====================================================================---
  */
  public void Register()
  {
	  String fname = logingui.GetFirstName().trim();
	  String lname = logingui.GetLastName().trim();
	  String email = logingui.GetEmail().trim();
	  String phnum = logingui.GetPhoneNumber().trim();
	  boolean elite = logingui.GetElite();
	  
	  int rnumber = GetNewRewardsNumber();
	  
	  if (lname.equals(""))
	  {
		  return;
	  }
	  
	  Customer cst;
	  if (elite)
	  {
		  cst = new EliteCustomer(fname, lname, rnumber, elite, 0, email, phnum);
	  }
	  else
	  {
		  cst = new RegularCustomer(fname, lname, rnumber, elite, 0, email, phnum);
	  }
	  
	  logingui.DisplayNumber(rnumber);
	  
	  customers.add(cst);
  	  customer = cst;
      shoppergui = new ShopperGUI(this);
      logingui.setVisible(false);
      shoppergui.setVisible(true);
      ReadProducts();
      ReadCarts();
      cart = GetCart(customer);
      shoppergui.DisplaySearchResults(this, products);
      shoppergui.DisplayCartResults(this, cart, customer);
  }
  
  /**
  ---====================================================================---
  NAME : GetNewRewardsNumber
  PURPOSE : Generates new rewards number
  ---====================================================================---
  */
  public int GetNewRewardsNumber()
  {
	  int newnum = 1;
	  ArrayList<Integer> nums = new ArrayList<Integer>();
	  
	  for (Customer c : customers)
	  {
		nums.add(c.RewNumber());  
	  }
	  
	  while (true)
	  {
		  if (nums.contains(newnum))
		  {
			newnum++;  
		  }
		  else
		  {
			  break;
		  }
	  }
	  
	  return newnum;
  }
  
  /**
  ---====================================================================---
  NAME : LogOut
  PURPOSE : Displays the registration panel
  ---====================================================================---
  */
  public void LogOut()
  {
    Collections.sort(customers);
	  Collections.sort(carts);
	  WriteCarts();
	  WriteCustomers();
	  System.exit(0);
  }
}