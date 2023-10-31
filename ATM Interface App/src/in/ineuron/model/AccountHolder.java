package in.ineuron.model;

public class AccountHolder {
	private Integer holderAccNo;
	private String holderName;
	private Integer holderAge;
	private String holderAddress;
	private String userId;
	private Integer userPin;

	private Bank bank;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserPin() {
		return userPin;
	}

	public void setUserPin(Integer userPin) {
		this.userPin = userPin;
	}

	public void setHolderAccNo(Integer holderAccNo) {
		this.holderAccNo = holderAccNo;
	}

	public Integer getHolderAccNo() {
		return holderAccNo;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Integer getHolderAge() {
		return holderAge;
	}

	public void setHolderAge(Integer holderAge) {
		this.holderAge = holderAge;
	}

	public String getHolderAddress() {
		return holderAddress;
	}

	public void setHolderAddress(String holderAddress) {
		this.holderAddress = holderAddress;
	}

	@Override
	public String toString() {
		return "AccountHolder [holderAccNo=" + holderAccNo + ", holderName=" + holderName + ", holderAge=" + holderAge
				+ ", holderAddress=" + holderAddress + ", userId=" + userId + ", userPin=" + userPin + ", bank=" + bank
				+ "]";
	}

}
