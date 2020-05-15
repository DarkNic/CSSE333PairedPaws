public class ContactInformation {
	private String phone;
	private String email;
	private String address;
	private String zipcode;

	public ContactInformation() {
	}

	public void setInfo(String phone, String email, String address, String zip) {
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.zipcode = zip;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddress() {
		return this.address;
	}

	public String getZip() {
		return ((this.zipcode));
	}
}
