package classes;
import java.sql.Date;
import java.sql.SQLException;

public class Transaction {
    private int id;
    private int amount;
    private String description;
    private Date date;
    private Account incomingAccount;
    private Account outgoingAccount;    
    private boolean shownIncoming;
    private boolean shownOutgoing;
    
    public Transaction(int amount, String description, Date date, Account incomingAccount, Account outgoingAccount) throws SQLException {
        this.id = SQL.getID("idTransaction", "Transaction");
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.shownIncoming = false;
        this.shownOutgoing = false;
        this.incomingAccount = incomingAccount;
        incomingAccount.add(this);
        this.outgoingAccount = outgoingAccount;
        outgoingAccount.add(this);
        
        String[] value = new String[8];
        value[0] = "" + id;
        value[1] = "" + amount;
        value[2] = description;
        value[3] = date.toString();
        value[4] = "" + incomingAccount.getId();
        value[5] = "" + outgoingAccount.getId();
        value[6] = " ";
        value[7] = " ";
        SQL.insert(value, "Transaction");
    }
    
    public Transaction(int id) throws SQLException {
     	
    	String[] column = {"amountTransaction", "descriptionTransaction", "dateTransaction", "shownIncomingTransaction", "shownOutgoingTransaction"};
        String[] condition = {"idTransaction = " + id};
        String[][] value = SQL.select(column, "Transaction", condition, "and");
        
        if (value.length == 0)
        	return;
        this.id = id;
        this.amount = Convert.toInt(value[0][0]);
        this.description = value[0][1];
        String s = value[0][2].substring(0,10);
        this.date = Date.valueOf(value[0][2].substring(0, 10));
        this.shownIncoming = value[0][3].equals("X");
        this.shownOutgoing = value[0][4].equals("X");
    }
    
    public Transaction() {
    	
    }
    
	public int getId() {
		return id;
	}
	
	/*public void setId(int id) {
		this.id = id;
	}*/
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
		}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
		}
	
	public boolean isShownIncoming() {
		return shownIncoming;
	}
	
	public void setShownIncoming(boolean shownIncoming) {
		this.shownIncoming = shownIncoming;
		}
	
	public boolean isShownOutgoing() {
		return shownOutgoing;
	}
	
	public void setShownOutgoing(boolean shownOutgoing) {
		this.shownOutgoing = shownOutgoing;
	}
	
	public Account getIncomingAccount() throws SQLException {
		
		if (incomingAccount == null) {
			String[] column = {"incomingAccount_idAccount"};
			String[] condition = {"idTransaction = " + id};
			String[][] value = SQL.select(column, "Transaction", condition, "and");
			incomingAccount = new Account(Convert.toInt(value[0][0]));
		}
		return incomingAccount;
	}
	
	public void setIncomingAccount(Account incomingAccount) {
		this.incomingAccount = incomingAccount;
		}
	
	public Account getOutgoingAccount() throws SQLException {
		if (outgoingAccount == null) {
			String[] column = {"outgoingAccount_idAccount"};
			String[] condition = {"idTransaction = " + id};
			String[][] value = SQL.select(column, "Transaction", condition, "and");
			outgoingAccount = new Account(Convert.toInt(value[0][0]));
		}
		return outgoingAccount;
	}
	
	public void setOutgoingAccount(Account outgoingAccount) {
		this.outgoingAccount = outgoingAccount;
		}
	
	public void updateDB() throws SQLException {
		String[] condition = {"idTransaction = " + id};
		String[] column = new String[7];
		String[] value = new String[7];
		
		column[0] = "amountTransaction";
		column[1] = "descriptionTransaction";
		column[2] = "dateTransaction";
		column[3] = "shownIncomingTransaction";
		column[4] = "shownOutgoingTransaction";
		column[5] = "incomingAccount_idAccount";
		column[6] = "outgoingAccount_idAccount";
		
		value[0] = "" + amount;
		value[1] = description;
		value[2] = date.toString();
		if (shownIncoming)
			value[3] = "X";
		else
			value[3] = " ";
		if (shownOutgoing)
			value[4] = "X";
		else
			value[4] = " ";
		value[5] = "" + incomingAccount.getId();
		value[6] = "" + outgoingAccount.getId();
	}
}
