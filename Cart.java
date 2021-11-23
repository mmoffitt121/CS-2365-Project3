import java.util.ArrayList;

public class Cart implements Comparable
{
  private int rewnumber;
  private ArrayList<Product> items;

  public void SetRewNumber(int _rewnumber)
  {
    rewnumber = _rewnumber;
  }

  public int RewNumber()
  {
    return rewnumber;
  }

  public void AddItem(Product toadd)
  {
    items.add(toadd);
  }

  public void RemoveItem(int toremove)
  {
    items.remove(toremove);
  }
  
  public void RemoveItem(Product toremove)
  {
	  items.remove(toremove);
  }
  
  public boolean equals(Object obj)
  {
	  return ((Cart)obj).RewNumber() == RewNumber();
  }
  
  public ArrayList<Product> Items()
  {
	  return items;
  }

  public Cart(int _rewnumber)
  {
    SetRewNumber(_rewnumber);

    items = new ArrayList<Product>();
  }

  public int compareTo(Object _cart) 
  {
	Cart cust = (Cart)_cart;
	if (RewNumber() > cust.RewNumber())
	{
	  return 1;
	}
	if (RewNumber() < cust.RewNumber())
	{
		return -1;
	}
	return 0;
  }
}