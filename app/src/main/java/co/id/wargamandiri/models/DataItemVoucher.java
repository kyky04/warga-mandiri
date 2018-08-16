package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DataItemVoucher {

	@SerializedName("jumlah_potongan")
	private int jumlahPotongan;

	@SerializedName("minimal_belanja")
	private int minimalBelanja;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("kode_voucher")
	private String kodeVoucher;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("expire_at")
	private String expireAt;

	@SerializedName("id")
	private int id;

	@SerializedName("start_at")
	private String startAt;

	@SerializedName("persen")
	private String persen;

	@SerializedName("created_by")
	private int createdBy;

	public void setJumlahPotongan(int jumlahPotongan){
		this.jumlahPotongan = jumlahPotongan;
	}

	public int getJumlahPotongan(){
		return jumlahPotongan;
	}

	public void setMinimalBelanja(int minimalBelanja){
		this.minimalBelanja = minimalBelanja;
	}

	public int getMinimalBelanja(){
		return minimalBelanja;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setKodeVoucher(String kodeVoucher){
		this.kodeVoucher = kodeVoucher;
	}

	public String getKodeVoucher(){
		return kodeVoucher;
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

	public void setExpireAt(String expireAt){
		this.expireAt = expireAt;
	}

	public String getExpireAt(){
		return expireAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStartAt(String startAt){
		this.startAt = startAt;
	}

	public String getStartAt(){
		return startAt;
	}

	public void setPersen(String persen){
		this.persen = persen;
	}

	public String getPersen(){
		return persen;
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
			"DataItemVoucher{" +
			"jumlah_potongan = '" + jumlahPotongan + '\'' + 
			",minimal_belanja = '" + minimalBelanja + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",kode_voucher = '" + kodeVoucher + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",expire_at = '" + expireAt + '\'' + 
			",id = '" + id + '\'' + 
			",start_at = '" + startAt + '\'' + 
			",persen = '" + persen + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}