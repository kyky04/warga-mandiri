package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class TokoResponse{

	@SerializedName("provinsi")
	private int provinsi;

	@SerializedName("kota")
	private int kota;

	@SerializedName("line")
	private String line;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("wa")
	private String wa;

	@SerializedName("bbm")
	private String bbm;

	@SerializedName("created_by")
	private Object createdBy;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("id")
	private int id;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("fb")
	private String fb;

	public void setProvinsi(int provinsi){
		this.provinsi = provinsi;
	}

	public int getProvinsi(){
		return provinsi;
	}

	public void setKota(int kota){
		this.kota = kota;
	}

	public int getKota(){
		return kota;
	}

	public void setLine(String line){
		this.line = line;
	}

	public String getLine(){
		return line;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setWa(String wa){
		this.wa = wa;
	}

	public String getWa(){
		return wa;
	}

	public void setBbm(String bbm){
		this.bbm = bbm;
	}

	public String getBbm(){
		return bbm;
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

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy;
	}

	public int getUpdatedBy(){
		return updatedBy;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setFb(String fb){
		this.fb = fb;
	}

	public String getFb(){
		return fb;
	}

	@Override
 	public String toString(){
		return 
			"TokoResponse{" + 
			"provinsi = '" + provinsi + '\'' + 
			",kota = '" + kota + '\'' + 
			",line = '" + line + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",wa = '" + wa + '\'' + 
			",bbm = '" + bbm + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",nama = '" + nama + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",phone = '" + phone + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",id = '" + id + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",fb = '" + fb + '\'' + 
			"}";
		}
}