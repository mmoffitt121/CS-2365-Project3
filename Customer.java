import java.util.Scanner;

/**
  ---====================================================================---
  NAME : Customer
  PURPOSE : Holds information for one customer read from a file. 
  INVARIANT : A customer must have a rewards number and a last name
  ---====================================================================---
*/
public class Customer implements Comparable
{
  private String fname;
  private String lname;
  private int rewnumber;
  private boolean elite;
  private String email;
  private String phnumber;

  /**
    ---====================================================================---
    Mutators
    ---====================================================================---
  */

  public void SetFName(String _fname)
  {
    fname = new String(_fname);
  }

  public void SetLName(String _lname)
  {
    lname = new String(_lname);
  }

  public void SetRewNumber(int _rewnumber)
  {
    rewnumber = _rewnumber;
  }

  public void SetElite(boolean _elite)
  {
    elite = _elite;
  }

  public void SetEmail(String _email)
  {
    email = new String(_email);
  }

  public void SetPhNumber(String _phnumber)
  {
    phnumber = new String(_phnumber);
  }

  /**
    ---====================================================================---
    Accessors
    ---====================================================================---
  */

  public String FName()
  {
    return new String(fname);
  }

  public String LName()
  {
    return new String(lname);
  }

  public int RewNumber()
  {
    return rewnumber;
  }

  public boolean elite()
  {
    return elite;
  }

  public String Email()
  {
    return new String(email);
  }

  public String PhNumber()
  {
    return new String(phnumber);
  }

  /**
    ---====================================================================---
    Comparison
    ---====================================================================---
  */

  // Equals override method, necessary for list operations.
  public boolean equals(Object _cust)
  {
    Customer cust = (Customer)_cust;
    if (LName().equals(cust.LName()) && RewNumber() == cust.RewNumber())
    {
      return true;
    }

    return false;
  }
  
  // compareTo override method, necessary for list operations.
  public int compareTo(Object _cust) 
  {
	Customer cust = (Customer)_cust;
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

  /**
    ---====================================================================---
    Constructors
    ---====================================================================---
  */

  public Customer(String _fname, String _lname, int _rewnumber, boolean _elite, String _email, String _phnumber)
  {
    SetFName(_fname);
    SetLName(_lname);
    SetRewNumber(_rewnumber);
    SetElite(_elite);
    SetEmail(_email);
    SetPhNumber(_phnumber);
  }

  public Customer(String _lname, int _rewnumber)
  {
    SetLName(_lname);
    SetRewNumber(_rewnumber);
  }
}