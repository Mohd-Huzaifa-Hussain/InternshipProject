package in.ineuron.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Properties;

import in.ineuron.model.AccountHolder;

public class BankTransactionImpl implements IBankTransaction {

	@Override
	public String openAccount(AccountHolder accHolder) throws Exception {

		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			if (name.startsWith(accHolder.getHolderName())) {
				System.out.println("Acccount Already Exist");
				throw new Exception();
			}
		}

		Integer accNo = accHolder.getHolderAccNo();
		String name = accHolder.getHolderName();
		Integer age = accHolder.getHolderAge();
		String address = accHolder.getHolderAddress();
		String id = accHolder.getUserId();
		Integer userPin = accHolder.getUserPin();
		Double balance = 0.0;

		try {
			File file = new File("src\\in\\ineuron\\commons\\" + name + ".properties");
			PrintWriter out = new PrintWriter(file);
			out.println("holderAccNo=" + accNo);
			out.println("holderName=" + name);
			out.println("holderAge=" + age);
			out.println("holderAddress=" + address);
			out.println("userId=" + id);
			out.println("userPin=" + userPin);
			out.println("balance=" + balance);

			out.flush();
			out.close();

			FileOutputStream histFile = new FileOutputStream("src/in/ineuron/commons2/" + name + "history.properties",
					true);
			PrintWriter out1 = new PrintWriter(histFile);
			out1.println(LocalDate.now() + " :---> ACCOUNT CREATED");
			System.out.println(LocalDate.now() + " :---> ACCOUNT CREATED");

			out1.flush();
			out1.close();

			return "success";

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "failure";
		}

	}

	@Override
	public String validateAccUser(String userId, Integer pin) throws IOException {

		String msg = "";
		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			FileInputStream fis = new FileInputStream("src/in/ineuron/commons/" + name);
			Properties properties = new Properties();
			properties.load(fis);

			if (properties.getProperty("userId").equals(userId)) {
				Integer getPin = Integer.parseInt(properties.getProperty("userPin"));
				getPin -= 8888;
				if (getPin.equals(pin)) {
					msg = "valid";
					break;
				}
			} else {
				msg = "invalid";
				continue;
			}

		}
		return msg;
	}

	@Override
	public void showTransactionHistory(String userId) throws IOException {

		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			File file = new File("src/in/ineuron/commons/" + name);
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			if (properties.getProperty("userId").equals(userId)) {
				File fileHist = new File(
						"src/in/ineuron/commons2/" + properties.getProperty("holderName") + "history.properties");
				BufferedReader reader = new BufferedReader(new FileReader(fileHist));
				String readLine = reader.readLine();
				System.out.println("\nTRANSACTION HISTORY");
				System.out.println("====================");
				while (readLine != null) {
					System.out.println(readLine);
					readLine = reader.readLine();
				}
				reader.close();
			}

		}
	}

	@Override
	public String withdrawMoney(String userId) throws IOException {
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		Double withdraw = 0.0;
		while (true) {
			try {
				System.out.print("Enter The Amount To WithDraw :: ");
				withdraw = Double.parseDouble(scanner.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid Input! Try Again\n");
				continue;
			}
		}

		Double balance = 0.0;

		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			File file = new File("src/in/ineuron/commons/" + name);
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			if (properties.getProperty("userId").equals(userId)) {
				Double oldBalance = Double.parseDouble(properties.getProperty("balance"));
				if (withdraw > oldBalance) {
					System.out.println("Amount Can Not Be WithDraw Due To InSufficient Balance ");
					return oldBalance.toString();
				} else {

					balance = oldBalance - withdraw;

					PrintWriter out = new PrintWriter(file);
					out.println("holderAccNo=" + properties.getProperty("holderAccNo"));
					out.println("holderName=" + properties.getProperty("holderName"));
					out.println("holderAge=" + properties.getProperty("holderAge"));
					out.println("holderAddress=" + properties.getProperty("holderAddress"));
					out.println("userId=" + properties.getProperty("userId"));
					out.println("userPin=" + properties.getProperty("userPin"));
					out.println("balance=" + balance);

					out.flush();
					out.close();
					System.out.println("Amount " + withdraw + " WithDrawl SuccessFull...");
					System.out.println("Please Take Your Cash...");

					FileOutputStream histFile = new FileOutputStream(
							"src/in/ineuron/commons2/" + properties.getProperty("holderName") + "history.properties",
							true);
					PrintWriter out1 = new PrintWriter(histFile);
					out1.println(LocalDate.now() + " :---> " + withdraw + " DEBITED");
					System.out.println(LocalDate.now() + " :---> " + withdraw + " DEBITED");

					out1.flush();
					out1.close();
				}

			}
		}

		return balance.toString();
	}

	@Override
	public String depositMoney(String userId) throws IOException {
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		Double depAmount = 0.0;
		while (true) {
			try {
				System.out.print("Enter The Amount To Deposit :: ");
				depAmount = Double.parseDouble(scanner.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid Input! Try Again\n");
				continue;
			}
		}

		Double balance = 0.0;

		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			File file = new File("src/in/ineuron/commons/" + name);
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			if (properties.getProperty("userId").equals(userId)) {
				double oldBalance = Double.parseDouble(properties.getProperty("balance"));
				balance = oldBalance + depAmount;

				PrintWriter out = new PrintWriter(file);
				out.println("holderAccNo=" + properties.getProperty("holderAccNo"));
				out.println("holderName=" + properties.getProperty("holderName"));
				out.println("holderAge=" + properties.getProperty("holderAge"));
				out.println("holderAddress=" + properties.getProperty("holderAddress"));
				out.println("userId=" + properties.getProperty("userId"));
				out.println("userPin=" + properties.getProperty("userPin"));
				out.println("balance=" + balance);

				out.flush();
				out.close();
				System.out.println("Amount " + depAmount + " Depsited SuccessFully...");

				FileOutputStream histFile = new FileOutputStream(
						"src/in/ineuron/commons2/" + properties.getProperty("holderName") + "history.properties", true);
				PrintWriter out1 = new PrintWriter(histFile);
				out1.println(LocalDate.now() + " :---> " + depAmount + " CREDITED");
				System.out.println(LocalDate.now() + " :---> " + depAmount + " CREDITED");

				out1.flush();
				out1.close();

			}
		}
		return balance.toString();
	}

	@Override
	public String transferMoney(String userId) throws IOException {

		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		Double transferAmount = 0.0;
		while (true) {
			try {
				System.out.print("Enter The Amount To Send :: ");
				transferAmount = Double.parseDouble(scanner.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid Input! Try Again\n");
				continue;
			}
		}

		Integer accNo = 0;
		while (true) {
			try {
				System.out.print("Enter The Account Number Of Reciever :: ");
				accNo = Integer.parseInt(scanner.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Invalid Input! Try Again\n");
				continue;
			}
		}

		Integer senderAcc = 0;

		Double balance1 = 0.0;

		Double oldBalance = 0.0;

		String senderName = "";

		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			File file = new File("src/in/ineuron/commons/" + name);
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			if (properties.getProperty("userId").equals(userId)) {
				senderAcc = Integer.parseInt(properties.getProperty("holderAccNo"));
				oldBalance = Double.parseDouble(properties.getProperty("balance"));
				if (transferAmount > oldBalance) {
					System.out.println("Amount Can Not Be WithDraw Due To InSufficient Balance ");
					return oldBalance.toString();
				} else {

					senderName = properties.getProperty("holderName");
					balance1 = oldBalance - transferAmount;

					PrintWriter out = new PrintWriter(file);
					out.println("holderAccNo=" + properties.getProperty("holderAccNo"));
					out.println("holderName=" + properties.getProperty("holderName"));
					out.println("holderAge=" + properties.getProperty("holderAge"));
					out.println("holderAddress=" + properties.getProperty("holderAddress"));
					out.println("userId=" + properties.getProperty("userId"));
					out.println("userPin=" + properties.getProperty("userPin"));
					out.println("balance=" + balance1);

					out.flush();
					out.close();

				}

			}
		}
		Double balance2 = 0.0;

		File f1 = new File("src/in/ineuron/commons");
		String[] names1 = f1.list();
		for (String name1 : names1) {
			File file1 = new File("src/in/ineuron/commons/" + name1);
			FileInputStream fis1 = new FileInputStream(file1);
			Properties properties1 = new Properties();
			properties1.load(fis1);
			if (properties1.getProperty("holderAccNo").equals(accNo.toString())) {

				double oldBalance2 = Double.parseDouble(properties1.getProperty("balance"));
				balance2 = oldBalance2 + transferAmount;

				PrintWriter out = new PrintWriter(file1);
				out.println("holderAccNo=" + properties1.getProperty("holderAccNo"));
				out.println("holderName=" + properties1.getProperty("holderName"));
				out.println("holderAge=" + properties1.getProperty("holderAge"));
				out.println("holderAddress=" + properties1.getProperty("holderAddress"));
				out.println("userId=" + properties1.getProperty("userId"));
				out.println("userPin=" + properties1.getProperty("userPin"));
				out.println("balance=" + balance2);

				out.flush();
				out.close();

				FileOutputStream histFile = new FileOutputStream(
						"src/in/ineuron/commons2/" + properties1.getProperty("holderName") + "history.properties",
						true);
				PrintWriter out1 = new PrintWriter(histFile);
				out1.println(LocalDate.now() + " :---> " + transferAmount + " CREDITED FROM ACCOUNT " + senderAcc);

				out1.flush();
				out1.close();
				break;
			}
		}

		File file = new File("src/in/ineuron/commons/" + senderName + ".properties");
		FileInputStream fis = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fis);
		if (balance2 == 0.0) {
			balance1 += transferAmount;

			System.out.println("Invalid Account Number...");
			System.out.println(transferAmount + " NOT TRNSFERRED");

			PrintWriter out = new PrintWriter(file);
			out.println("holderAccNo=" + properties.getProperty("holderAccNo"));
			out.println("holderName=" + properties.getProperty("holderName"));
			out.println("holderAge=" + properties.getProperty("holderAge"));
			out.println("holderAddress=" + properties.getProperty("holderAddress"));
			out.println("userId=" + properties.getProperty("userId"));
			out.println("userPin=" + properties.getProperty("userPin"));
			out.println("balance=" + balance1);

			out.flush();
			out.close();

			FileOutputStream histFile = new FileOutputStream(
					"src/in/ineuron/commons2/" + senderName + "history.properties", true);
			PrintWriter out1 = new PrintWriter(histFile);
			out1.println(LocalDate.now() + " :---> " + transferAmount + " CREDITED BACK DUE TO FAIL");
			out1.flush();
			out1.close();
		} else {
			System.out.println("Amount " + transferAmount + " Transfer SuccessFull...");

			FileOutputStream histFile = new FileOutputStream(
					"src/in/ineuron/commons2/" + properties.getProperty("holderName") + "history.properties", true);
			PrintWriter out1 = new PrintWriter(histFile);
			out1.println(LocalDate.now() + " :---> " + transferAmount + " TRNSFERRED TO ACCOUNT " + accNo);
			System.out.println(LocalDate.now() + " :---> " + transferAmount + " TRNSFERRED TO ACCOUNT " + accNo);

			out1.flush();
			out1.close();
		}
		return balance1.toString();
	}

	@Override
	public String showBalance(String userId) throws IOException {
		Double balance = 0.0;
		File f = new File("src/in/ineuron/commons");
		String[] names = f.list();
		for (String name : names) {
			File file = new File("src/in/ineuron/commons/" + name);
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			if (properties.getProperty("userId").equals(userId)) {
				balance = Double.parseDouble(properties.getProperty("balance"));

			}
		}
		return balance.toString();
	}

}
