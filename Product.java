public class Product
{
  private String name;
  private int number;
  private String description;
  private double price;
  private String image;

  // Accessors

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

  // Comparison

  public boolean Match(String input)
  {
    return Name().contains(input);
  }

  public boolean equals(int input)
  {
    return number == input;
  }

  // Constructor

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