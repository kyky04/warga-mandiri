package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DataItemVoucher {

	@SerializedName("potongan")
	private int jumlahPotongan;

	@SerializedName("minimal_belanja")
	private int minimalBelanja;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("kode")
	private String kodeVoucher;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("tanggal_akhir")
	private String tanggal_akhir;

	@SerializedName("id")
	private int id;

	@SerializedName("tanggal_awal")
	private String tanggal_awal;

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

	public void setTanggal_akhir(String tanggal_akhir){
		this.tanggal_akhir = tanggal_akhir;
	}

	public String getTanggal_akhir(){
		return tanggal_akhir;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTanggal_awal(String tanggal_awal){
		this.tanggal_awal = tanggal_awal;
	}

	public String getTanggal_awal(){
		return tanggal_awal;
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
			",expire_at = '" + tanggal_akhir + '\'' +
			",id = '" + id + '\'' + 
			",start_at = '" + tanggal_awal + '\'' +
			",persen = '" + persen + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			"}";
		}
}