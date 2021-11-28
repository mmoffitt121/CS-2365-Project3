/**
  ---====================================================================---
  NAME : Product
  PURPOSE : Holds information of one product read from a file
  ---====================================================================---
*/
public class Product
{
  private String name;
  private int number;
  private String description;
  private double price;
  private String image;

  /**
    ---====================================================================---
    Accessors
    ---====================================================================---
  */

  public String Name()
  {
    return new String(name);
  }

  public int Number()
  {
    return number;
  }

  public String Description()
  {
    return new String(description);
  }

  public double Price()
  {
    return price;
  }

  public String Image()
  {
    return new String(image);
  }

  /**
    ---====================================================================---
    Comparison
    ---====================================================================---
  */

  public boolean Match(String input)
  {
    return Name().toLowerCase().contains(input.toLowerCase());
  }

  public boolean equals(int input)
  {
    return number == input;
  }
  
  public boolean equals(Object object)
  {
	  Product obj = (Product)object;
	  return obj.Name().equals(Name());
  }

  /**
    ---====================================================================---
    Constructors
    ---====================================================================---
  */

  public Product(String _name, int _number, String _description, double _price, String _image)
  {
    name = _name;
    number = _number;
    description = _description;
    price = _price;
    image = _image;
  }

  public Product(Product p)
  {
    name = p.Name();
    number = p.Number();
    description = p.Description();
    price = p.Price();
    image = p.Image();
  }
}