package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class KategoriItem{

	@SerializedName("id_produk")
	private int idProduk;

	@SerializedName("id_kategori")
	private int idKategori;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("created_by")
	private int createdBy;

	public void setIdProduk(int idProduk){
		this.idProduk = idProduk;
	}

	public int getIdProduk(){
		return idProduk;
	}

	public void setIdKategori(int idKategori){
		this.idKategori = idKategori;
	}

	public int getIdKategori(){
		return idKategori;
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

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	@Override
 	public String toString(){
		return 
			"KategoriItem{" + 
			"id_produk = '" + idProduk + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}