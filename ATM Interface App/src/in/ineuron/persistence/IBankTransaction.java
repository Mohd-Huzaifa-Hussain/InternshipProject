package in.ineuron.persistence;

import java.io.IOException;

import in.ineuron.model.AccountHolder;

public interface IBankTransaction {

	public String openAccount(AccountHolder accHolder) throws Exception ;

	public String validateAccUser(String userId, Integer pin) throws IOException;

	public void showTransactionHistory(String userId) throws IOException;

	public String withdrawMoney(String userId) throws IOException;

	public String depositMoney(String userId) throws IOException;

	public String transferMoney(String userId)throws IOException;

	public String showBalance(String userId) throws IOException;

}
