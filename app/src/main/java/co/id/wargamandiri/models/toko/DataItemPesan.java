package co.id.wargamandiri.models.toko;

import com.google.gson.annotations.SerializedName;

public class DataItemPesan {

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("isi")
	private String isi;

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

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",isi = '" + isi + '\'' + 
			"}";
		}
}