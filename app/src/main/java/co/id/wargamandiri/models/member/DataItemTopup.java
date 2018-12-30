package co.id.wargamandiri.models.member;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItemTopup implements Serializable {

	@SerializedName("bank_pengirim")
	private String bankPengirim;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("rekening_pengirim")
	private String rekeningPengirim;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("created_by")
	private Object createdBy;

	@SerializedName("rekening_tujuan")
	private String rekeningTujuan;

	@SerializedName("nama_pengirim")
	private String namaPengirim;

	@SerializedName("deleted")
	private int deleted;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("bank_tujuan")
	private String bankTujuan;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("id")
	private int id;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private int status;

	public void setBankPengirim(String bankPengirim){
		this.bankPengirim = bankPengirim;
	}

	public String getBankPengirim(){
		return bankPengirim;
	}

	public void setIdToko(int idToko){
		this.idToko = idToko;
	}

	public int getIdToko(){
		return idToko;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setRekeningPengirim(String rekeningPengirim){
		this.rekeningPengirim = rekeningPengirim;
	}

	public String getRekeningPengirim(){
		return rekeningPengirim;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setCreatedBy(Object createdBy){
		this.createdBy = createdBy;
	}

	public Object getCreatedBy(){
		return createdBy;
	}

	public void setRekeningTujuan(String rekeningTujuan){
		this.rekeningTujuan = rekeningTujuan;
	}

	public String getRekeningTujuan(){
		return rekeningTujuan;
	}

	public void setNamaPengirim(String namaPengirim){
		this.namaPengirim = namaPengirim;
	}

	public String getNamaPengirim(){
		return namaPengirim;
	}

	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	public int getDeleted(){
		return deleted;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setBankTujuan(String bankTujuan){
		this.bankTujuan = bankTujuan;
	}

	public String getBankTujuan(){
		return bankTujuan;
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

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
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
			"DataItemTopup{" +
			"bank_pengirim = '" + bankPengirim + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",rekening_pengirim = '" + rekeningPengirim + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",rekening_tujuan = '" + rekeningTujuan + '\'' + 
			",nama_pengirim = '" + namaPengirim + '\'' + 
			",deleted = '" + deleted + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",bank_tujuan = '" + bankTujuan + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",id = '" + id + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}