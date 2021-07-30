package clothes;

public class Client {
	private int memberno;
	private String name;
	private String birth;
	private String gender;
	private String password;
	private String phone;
	private String addr;
	private String account;
	private String pname;
	private String psize;
	private String pcolor;
	private String pprice;
	
	public int getMemberno() {
		return memberno;
	}
	public void setMemberno(int memberno) {
		this.memberno = memberno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPsize() {
		return psize;
	}
	public void setPsize(String psize) {
		this.psize = psize;
	}
	public String getPcolor() {
		return pcolor;
	}
	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}
	public String getPprice() {
		return pprice;
	}
	public void setPprice(String pprice) {
		this.pprice = pprice;
	}
	
	
	@Override
	public String toString() {
		return "Client [memberno=" + memberno + ", name=" + name + ", birth=" + birth + ", gender=" + gender
				+ ", password=" + password + ", phone=" + phone + ", addr=" + addr + ", account=" + account + ", pname="
				+ pname + ", psize=" + psize + ", pcolor=" + pcolor + ", pprice=" + pprice + "]";
	}
	
	
	public Client(int memberno, String name, String birth, String gender, String password, String phone, String addr,
			String account, String pname, String psize, String pcolor, String pprice) {
		super();
		this.memberno = memberno;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.password = password;
		this.phone = phone;
		this.addr = addr;
		this.account = account;
		this.pname = pname;
		this.psize = psize;
		this.pcolor = pcolor;
		this.pprice = pprice;
	}
	
	
	public Client(int memberno, String name, String birth, String gender, String password, String phone, String addr,
			String account) {
		super();
		this.memberno = memberno;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.password = password;
		this.phone = phone;
		this.addr = addr;
		this.account = account;
	}
	public Client() {
		super();
	}
		
}
