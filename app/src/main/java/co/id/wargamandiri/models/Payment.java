package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class Payment{

	@SerializedName("id_order")
	private int idOrder;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("jenis")
	private Object jenis;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("nomor_akun")
	private String nomorAkun;

	@SerializedName("created_by")
	private Object createdBy;

	@SerializedName("akun")
	private String akun;

	public void setIdOrder(int idOrder){
		this.idOrder = idOrder;
	}

	public int getIdOrder(){
		return idOrder;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setJenis(Object jenis){
		this.jenis = jenis;
	}

	public Object getJenis(){
		return jenis;
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

	public void setNomorAkun(String nomorAkun){
		this.nomorAkun = nomorAkun;
	}

	public String getNomorAkun(){
		return nomorAkun;
	}

	public void setCreatedBy(Object createdBy){
		this.createdBy = createdBy;
	}

	public Object getCreatedBy(){
		return createdBy;
	}

	public void setAkun(String akun){
		this.akun = akun;
	}

	public String getAkun(){
		return akun;
	}

	@Override
 	public String toString(){
		return 
			"Payment{" + 
			"id_order = '" + idOrder + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",jenis = '" + jenis + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",nomor_akun = '" + nomorAkun + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",akun = '" + akun + '\'' + 
			"}";
		}
}