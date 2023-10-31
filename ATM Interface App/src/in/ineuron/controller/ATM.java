package in.ineuron.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

import in.ineuron.model.AccountHolder;
import in.ineuron.model.Bank;
import in.ineuron.service.IAccount;
import in.ineuron.servicefactory.AccountFactory;

public class ATM {

	private static IAccount account;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("********Welcome To NKTB Bank************");

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nPlease Choose The Correction Option From Below");
			System.out.println("Enter 1 - Register");
			System.out.println("Enter 2 - Login");
			System.out.println("Enter 3 - Exit");

			try {
				String option = scanner.next();
				if (Integer.parseInt(option) == 1)
					registerUser();
				else if (Integer.parseInt(option) == 2)
					login();
				else if (Integer.parseInt(option) == 3) {
					System.out.println("*******Thank You For Using This App**********");
					System.exit(0);
				} else
					System.out.println("Wrong Input! Try Again...");

			} catch (Exception e) {
				continue;
			}

		}

	}

	@SuppressWarnings("resource")
	private static void login() {

		account = AccountFactory.getAccount();

		Scanner scanner = new Scanner(System.in);
		String msg = "";

		System.out.print("Enter Your UserID :: ");
		String userId = scanner.next();

		System.out.print("Enter Your Pin :: ");
		Integer password = scanner.nextInt();

		try {
			msg = account.validateAccUser(userId, password);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (msg.equals("valid")) {
			System.out.println("Login Successful....");
			while (true) {
				System.out.println("\nPlease Select The Option To Do The Operation\n");
				System.out.println("1.Show Transaction History");
				System.out.println("2.Withdraw Money");
				System.out.println("3.Deposit Money");
				System.out.println("4.Transfer Money");
				System.out.println("5.Show Balance");
				System.out.println("6.Account Deatails");
				System.out.println("7.Quit");
				System.out.print("\nEnter Any One Choice[1/2/3/4/5/6/7] :: ");

				Integer choice = scanner.nextInt();

				switch (choice) {
				case 1:
					try {
						account.showTransactionHistory(userId);
					} catch (IOException e) {
						System.out.println("SomeThing Went Wrong! Try Again...");
					}
					break;
				case 2:
					try {
						String balance = account.withdrawMoney(userId);
						System.out.println("Your Balance Is :: " + balance);
					} catch (IOException e) {
						System.out.println("SomeThing Went Wrong! Try Again...");
					}
					break;
				case 3:
					try {
						String money = account.depositMoney(userId);
						System.out.println("Your Balance Is :: " + money);
					} catch (IOException e) {
						System.out.println("SomeThing Went Wrong! Try Again...");
					}
					break;
				case 4:
					try {
						String balance = account.transferMoney(userId);
						System.out.println("Your Balance Is :: " + balance);
					} catch (IOException e) {
						System.out.println("SomeThing Went Wrong! Try Again...");
					}
					break;
				case 5:

					try {
						String balance = account.showBalance(userId);
						System.out.println("Your Balance Is :: " + balance);
					} catch (IOException e) {
						System.out.println("SomeThing Went Wrong! Try Again...");
					}
					break;

				case 6:

					accountDetails(userId);
					break;
				case 7:
					System.out.println("*******Thank You For Using This App**********");
					System.exit(0);
				default:
					System.out.println("Invalid Option! Try Again");
				}
				System.out.print("\nDo You Want To Continue [Y/N] :: ");
				String input = scanner.next();
				if ("y".equalsIgnoreCase(input))
					continue;
				else
					break;
			}
		} else {
			System.out.println("Login Failed! Invalid UserId Or Password...");

		}

	}

	private static void accountDetails(String userId) {
		try {
			File f = new File("src/in/ineuron/commons");
			String[] names = f.list();
			for (String name : names) {
				File file = new File("src/in/ineuron/commons/" + name);
				FileInputStream fis = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fis);
				if (properties.getProperty("userId").equals(userId)) {
					System.out.println();
					System.out.println("ACCOUNT DETAILS");
					System.out.println("===============");
					System.out.println("Account Number :: " + properties.getProperty("holderAccNo"));
					System.out.println("Name           :: " + properties.getProperty("holderName"));
					System.out.println("Age            :: " + properties.getProperty("holderAge"));
					System.out.println("Address        :: " + properties.getProperty("holderAddress"));
					System.out.println("Balance        :: " + properties.getProperty("balance"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bank bank = new Bank();
		System.out.println();
		System.out.println("BANK DETAILS");
		System.out.println("============");
		System.out.println("Bank Name      :: " + bank.getBankName());
		System.out.println("Branch         :: " + bank.getBankBranch());
		System.out.println("IFSC Code      :: " + bank.getBankIfscCode());
		System.out.println("Bank Address   :: " + bank.getBankAddress());
	}

	private static void registerUser() {

		account = AccountFactory.getAccount();

		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				AccountHolder accountHolder = new AccountHolder();

				System.out.print("Enter Your Name :: ");
				String name = scanner.readLine();
				accountHolder.setHolderName(name);
				
				

				while (true) {
					try {

						System.out.print("Enter Your Age ::");
						String age = scanner.readLine();
						accountHolder.setHolderAge(Integer.parseInt(age));
						break;
					} catch (Exception e) {
						System.out.println("Invalid Age! Try Again\n");
						continue;
					}
				}

				System.out.print("Enter Your Address :: ");
				String address = scanner.readLine();
				accountHolder.setHolderAddress(address);

				while (true) {
					try {
						System.out.print("Set Your UserID (Should Start With Name e.g. Hussain123) :: ");
						String userId = scanner.readLine();
						if (userId.startsWith(name) != true)
							throw new Exception();
						else {
							accountHolder.setUserId(userId);
							break;
						}
					} catch (Exception e) {
						System.out.println("Invalid UserID! Try Again\n");
						continue;
					}
				}

				while (true) {
					try {
						System.out.print("Set Your Pin (Only 4 Digit Number) :: ");
						String password = scanner.readLine();
						Integer pin = Integer.parseInt(password);

						if (Integer.parseInt(password) >= 1000 && Integer.parseInt(password) <= 9999) { // Encryption
							pin += 8888;
							accountHolder.setUserPin(pin);
							break;

						} else {
							throw new Exception();
						}
					} catch (Exception e) {
						System.out.println("Invalid PIN! Try Again\n");
						continue;
					}
				}

				
				
				String msg = account.openAccount(accountHolder);
				if (msg.equalsIgnoreCase(accountHolder.getHolderAccNo().toString())) {
					System.out.println("Registration Successfull");
					System.out.println("Your Account No Is :: " + accountHolder.getHolderAccNo());
					break;
				} else
					System.out.println("Registration Failure!");
			} catch (FileNotFoundException e) {
				System.out.println("OOP's Something went wrong try again!\n");

			} catch (Exception e) {
				System.out.println("OOP's Something went wrong try again!\n");
			}
		}

	}

}
