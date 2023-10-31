package in.ineuron.service;

import java.io.IOException;
import java.util.Random;

import in.ineuron.model.AccountHolder;
import in.ineuron.persistence.IBankTransaction;
import in.ineuron.persistencefactory.BankTransactionFactory;

public class AccountImpl implements IAccount {

	private IBankTransaction bankTransaction;

	@Override
	public String openAccount(AccountHolder accHolder) throws Exception {

		Integer accountNo = new Random().nextInt(1000, 9999);
		accHolder.setHolderAccNo(accountNo);

		bankTransaction = BankTransactionFactory.getBankTransaction();
		String msg = bankTransaction.openAccount(accHolder);

		if (msg.equals("success"))
			return accountNo.toString();
		else
			return "failure";
	}

	@Override
	public String validateAccUser(String userId, Integer pin) throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();
		return bankTransaction.validateAccUser(userId, pin);
	}

	@Override
	public void showTransactionHistory(String userId) throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();
		bankTransaction.showTransactionHistory(userId);
	}

	@Override
	public String withdrawMoney(String userId) throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();

		return bankTransaction.withdrawMoney(userId);
	}

	@Override
	public String depositMoney(String userId) throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();

		return bankTransaction.depositMoney(userId);
	}

	@Override
	public String transferMoney(String userId)throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();
		return bankTransaction.transferMoney(userId);
	}

	@Override
	public String showBalance(String userId) throws IOException {
		bankTransaction = BankTransactionFactory.getBankTransaction();

		return bankTransaction.showBalance(userId);
	}

}
