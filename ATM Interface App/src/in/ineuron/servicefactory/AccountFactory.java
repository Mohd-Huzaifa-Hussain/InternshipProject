package in.ineuron.servicefactory;

import in.ineuron.service.AccountImpl;
import in.ineuron.service.IAccount;

public class AccountFactory {

	private AccountFactory() {
	
	}
	
	private static IAccount account = null;
	
	public static IAccount getAccount() {
		
		if (account == null) 
			account = new AccountImpl();
		
		return account;
	}
}
