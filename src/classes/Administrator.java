package classes;
import java.sql.SQLException;
import java.util.ArrayList;

public class Administrator extends Person {

	public Administrator(String firstName, String secondName, String password) throws SQLException {
	    super(firstName, secondName, password, true); 
	}
	
	public Administrator(int id) throws SQLException {
		super(id, true);
	}
	
	public Administrator() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void deactivateAccount(Account account, boolean active) throws SQLException {
		if (account.getAdministrator().getId() == id)
	        account.setFlagActive(active);	
		else {
			// Error: you cannot deactivate Accounts you do not maintain!
		}
	}
	
	public void createCustomer(String firstName, String secondName, String password) throws SQLException {
		Customer c = new Customer(firstName, secondName, password);
	}
	
	public void createAccount(boolean flagActive, Customer customer, Bank bank, AccountType accountType) throws SQLException {
		Account a = new Account (flagActive, customer, this, bank, accountType);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPasswort(String password) {
		this.password = password;
	}
	
	/*public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}*/
	public int getId() {
		return id;
	}
	/*public void setId(int id) {
		this.id = id;
	}*/
	
	public ArrayList<Account> getAccounts() throws SQLException {
		if (accounts == null) {
			accounts = new ArrayList<Account>();
			String[] column = {"idAccount"};
			String[] condition = {"Administrator_idAdministrator = " + id};
			String[][] value = SQL.select(column, "Account", condition, "and");
			for (int i=0; i<value.length; i++)
			    accounts.add(new Account(Convert.toInt(value[i][0])));
		}
		return accounts;
	}
	
    public void login(String password) throws SQLException{
    	String[] column = {"passwordAdministrator"};
    	String table = "Administrator";
    	String[] condition = {"idAdministrator = " + id};
    	String[][] value = SQL.select(column, table, condition, "and");
    	if (value.length > 0)
    		if (value[0][0].equals(password))
    			setLoggedIn(true);
    		else
    			setLoggedIn(false);
    	else
    		setLoggedIn(false);
    }
    
	public void updateDB() throws SQLException {
		String[] condition = {"idAdministrator = " + id};
		String[] column = new String[3];
		String[] value = new String[3];
		
		column[0] = "firstNameAdministrator";
		column[1] = "secondNameAdministrator";
     	column[2] = "passwordAdministrator";
     	
     	value[0] = firstName;
     	value[1] = secondName;
     	value[2] = password;
     	
     	SQL.update(column, value, "Administrator", condition, "and");
	}
}
