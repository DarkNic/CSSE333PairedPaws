
public abstract class Animal {

	private String name;
	private String age;
	private String gender;
	private int animalID;
	private boolean neutered_Spayed;
	private int stage;
	private boolean houseTrained; 
	private String intakeDate;
	
	public Animal(int animalID) {
		this.animalID=animalID;
	}
	
	public abstract void setInfo();
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setGender(String gender) {
		this.gender=gender;
	}
	
	public void setAge(String age) {
		this.age=age;
	}
	
	public void setNS(boolean ns) {
		this.neutered_Spayed=ns;
	}
	
	public void setHouseTrained(boolean yN) {
		this.houseTrained=yN;
	}
	
	public String getName() {
		return this.name;
	}
}
