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

  public void Run()
  {
    logingui = new LoginGUI(this);
    logingui.setVisible(true);
    ReadCustomers();
    /*customer = customers.get(customers.indexOf(new Customer("Smith", 256791)));
    shoppergui = new ShopperGUI(this);
    shoppergui.setVisible(true);
    ReadProducts();
    ReadCarts();
    cart = GetCart(customer);
    shoppergui.DisplaySearchResults(this, products);
    shoppergui.DisplayCartResults(this, cart);*/
  }

  // Attempts to read 
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
      Scanner scn = new Scanner(new File("products.txt"), "utf-8");

      int amount = scn.nextInt();
      scn.nextLine();
      for (int i = 0; i < amount; i++)
      {
    	
        String name = scn.nextLine(); System.out.println(name);
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
  
  // Writes cart file
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
  
  // Writes customer files
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
  
  // Displays searched products to user
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
  
  public Customer GetUser()
  {
	  return customer;
  }

  public void DisplayCart()
  {
	incart = true;
    shoppergui.DisplayCart();
  }
  
  public void DisplayShop()
  {
	incart = false;
	shoppergui.DisplayShop();
  }
  
  public boolean InCart()
  {
	return incart;
  }
  
  public void AddToCart(Product toadd, int amounttoadd)
  {
	  for (int i = 0; i < amounttoadd; i++)
	  {
		  cart.AddItem(toadd);
	  }
	  shoppergui.DisplayCartResults(this, cart, customer);
  }
  
  public void RemoveCartItem(Product prod)
  {
	  cart.RemoveItem(prod);
	  shoppergui.DisplayCartResults(this, cart, customer);
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
  
  public void StartRegistration()
  {
	  logingui.DisplayRegistration();
  }
  
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
  
  public void LogOut()
  {
	  Collections.sort(customers);
	  Collections.sort(carts);
	  WriteCarts();
	  WriteCustomers();
	  System.exit(0);
  }
}