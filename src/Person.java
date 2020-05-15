public class Person {
	private String userName;
	private String password;
	private String name;
	private ContactInformation contactInfo;

	public void setUserName(String user) {
		this.userName = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactInfo(String phone, String email, String address, String zip) {
		ContactInformation info = new ContactInformation();
		info.setInfo(phone, email, address, zip);
		this.contactInfo = info;
	}

	public void setContactInfo(ContactInformation info) {
		this.contactInfo = info;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getName() {
		return this.name;
	}

	public ContactInformation getContact() {
		return this.contactInfo;
	}
}