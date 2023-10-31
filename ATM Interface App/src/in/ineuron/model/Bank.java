package in.ineuron.model;

public class Bank {
	private String bankName = "NKTB";
	private String bankBranch = "Noor";
	private String bankIfscCode = "BIDR00401";
	private String bankAddress = "Bidar";
	

	public String getBankName() {
		return bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public String getBankIfscCode() {
		return bankIfscCode;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	@Override
	public String toString() {
		return "Bank [bankName=" + bankName + ", bankBranch=" + bankBranch + ", bankIfscCode=" + bankIfscCode
				+ ", bankAddress=" + bankAddress + "]";
	}

}
