package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DataItemBanner {

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("created_by")
	private int createdBy;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

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

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy;
	}

	public int getUpdatedBy(){
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

	@Override
 	public String toString(){
		return 
			"DataItemBanner{" +
			"keterangan = '" + keterangan + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}