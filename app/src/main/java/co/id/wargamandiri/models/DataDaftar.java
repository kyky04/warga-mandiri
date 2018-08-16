package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DataDaftar {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("name")
	private String name;

	@SerializedName("admin")
	private boolean admin;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private int status;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdToko(int idToko){
		this.idToko = idToko;
	}

	public int getIdToko(){
		return idToko;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAdmin(boolean admin){
		this.admin = admin;
	}

	public boolean isAdmin(){
		return admin;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataDaftar{" +
			"updated_at = '" + updatedAt + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",name = '" + name + '\'' + 
			",admin = '" + admin + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}