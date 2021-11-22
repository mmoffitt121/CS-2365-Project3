public class RegularCustomer extends Customer
{
  private int stars;

  // Mutator
  public void SetStars(int _stars)
  {
    stars = _stars;
  }

  // Accessor
  public int GetStars()
  {
    return stars;
  }

  // Constructor 
  public RegularCustomer(String _fname, String _lname, int _rewnumber, boolean _elite, int _stars, String _email, String _phnumber)
  {
    super(_fname, _lname, _rewnumber, _elite, _email, _phnumber);
    SetStars(_stars);
  }
}