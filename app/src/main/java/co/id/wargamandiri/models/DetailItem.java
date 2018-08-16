package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DetailItem{

	@SerializedName("id_produk")
	private int idProduk;

	@SerializedName("id_order")
	private int idOrder;

	@SerializedName("total")
	private int total;

	@SerializedName("harga")
	private int harga;

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("updated_by")
	private int updatedBy;

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

	public void setIdOrder(int idOrder){
		this.idOrder = idOrder;
	}

	public int getIdOrder(){
		return idOrder;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setHarga(int harga){
		this.harga = harga;
	}

	public int getHarga(){
		return harga;
	}

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	@Override
 	public String toString(){
		return 
			"DetailItem{" + 
			"id_produk = '" + idProduk + '\'' + 
			",id_order = '" + idOrder + '\'' + 
			",total = '" + total + '\'' + 
			",harga = '" + harga + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}