package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("provinsi")
	private int provinsi;

	@SerializedName("role")
	private int role;

	@SerializedName("kota")
	private int kota;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("created_by")
	private Object createdBy;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setProvinsi(int provinsi){
		this.provinsi = provinsi;
	}

	public int getProvinsi(){
		return provinsi;
	}

	public void setRole(int role){
		this.role = role;
	}

	public int getRole(){
		return role;
	}

	public void setKota(int kota){
		this.kota = kota;
	}

	public int getKota(){
		return kota;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setIdToko(int idToko){
		this.idToko = idToko;
	}

	public int getIdToko(){
		return idToko;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setCreatedBy(Object createdBy){
		this.createdBy = createdBy;
	}

	public Object getCreatedBy(){
		return createdBy;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setUpdatedBy(Object updatedBy){
		this.updatedBy = updatedBy;
	}

	public Object getUpdatedBy(){
		return updatedBy;
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

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"provinsi = '" + provinsi + '\'' + 
			",role = '" + role + '\'' + 
			",kota = '" + kota + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",nama = '" + nama + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}