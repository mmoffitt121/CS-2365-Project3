import java.util.ArrayList;

public class Cart
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

  public Cart(int _rewnumber)
  {
    SetRewNumber(_rewnumber);

    items = new ArrayList<Product>();
  }
}