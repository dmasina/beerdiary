package br.blog.masina.beerdiary;

public class BreweryVO {

	public static String[] columns = new String[] { "id", "name", "website", "description" };
	public static String  tableName = "brewery";
	public static String  tableState = "brewery_state";
	
	private int id;
	private String name;
	private String website;
	private String description;
	
	public BreweryVO(int id, String name, String website, String description) {
		super();
		this.id = id;
		this.name = name;
		this.website = website;
		this.description = description;
	}	

	public BreweryVO(String name, String website, String description) {
		super();
		this.name = name;
		this.website = website;
		this.description = description;
	}

	public int checkFields() {		
		
		int result = 0; // Success Checked
		
		if (this.getName().length() == 0) { 
			result = R.id.edt_name;
		
		//} else if (this.getWebsite().length() == 0) { 
		//	result = R.id.edt_website;

		}
		
		return result;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
