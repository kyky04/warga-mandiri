package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageItem implements Serializable {

	@SerializedName("id_produk")
	private int idProduk;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("created_by")
	private int createdBy;

	@SerializedName("status")
	private int status;

	public void setIdProduk(int idProduk){
		this.idProduk = idProduk;
	}

	public int getIdProduk(){
		return idProduk;
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

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
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
			"ImageItem{" + 
			"id_produk = '" + idProduk + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}