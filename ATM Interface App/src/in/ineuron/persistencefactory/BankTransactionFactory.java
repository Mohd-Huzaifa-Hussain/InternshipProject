package in.ineuron.persistencefactory;

import in.ineuron.persistence.BankTransactionImpl;
import in.ineuron.persistence.IBankTransaction;

public class BankTransactionFactory {

	private BankTransactionFactory() {
	}
	
	private static IBankTransaction bankTransaction = null;
	
	public static IBankTransaction getBankTransaction() {
		if (bankTransaction == null) 
			bankTransaction = new BankTransactionImpl();
	
		return bankTransaction;
	}
}
