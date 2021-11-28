/**
  ---====================================================================---
  NAME : EliteCustomer
  PURPOSE : Holds information for an elite customer, inherits customer
  ---====================================================================---
*/
public class EliteCustomer extends Customer
{
  private double percentdiscount;

  // Mutator
  public void SetPercentDiscount(double _percentdiscount)
  {
    percentdiscount = _percentdiscount;
  }

  // Accessor
  public double GetPercentDiscount()
  {
    return percentdiscount;
  }

  // Constructor 
  public EliteCustomer(String _fname, String _lname, int _rewnumber, boolean _elite, double _percentdiscount, String _email, String _phnumber)
  {
    super(_fname, _lname, _rewnumber, _elite, _email, _phnumber);
    SetPercentDiscount(_percentdiscount);
  }
}