package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DetailItem{

	@SerializedName("id_produk")
	private int idProduk;

	@SerializedName("id_order")
	private int idOrder;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("total")
	private String total;

	@SerializedName("harga")
	private String harga;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("qty")
	private int qty;

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

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setQty(int qty){
		this.qty = qty;
	}

	public int getQty(){
		return qty;
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
			",keterangan = '" + keterangan + '\'' + 
			",total = '" + total + '\'' + 
			",harga = '" + harga + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",qty = '" + qty + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}