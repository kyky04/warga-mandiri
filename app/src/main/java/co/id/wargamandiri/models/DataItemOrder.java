package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItemOrder {

	@SerializedName("provinsi")
	private int provinsi;

	@SerializedName("kota")
	private int kota;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("metode_bayar")
	private String metodeBayar;

	@SerializedName("created_by")
	private int createdBy;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("ongkos_kirim")
	private String ongkosKirim;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("jasa_pengiriman")
	private String jasaPengiriman;

	@SerializedName("kode")
	private String kode;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("id_voucher")
	private int idVoucher;

	@SerializedName("pembeli")
	private String pembeli;

	@SerializedName("id")
	private int id;

	@SerializedName("id_bank")
	private int idBank;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("detail")
	private List<DetailItem> detail;

	@SerializedName("resi")
	private String resi;

	@SerializedName("status")
	private int status;

	@SerializedName("jenis_pengiriman")
	private String jenisPengiriman;

	public void setProvinsi(int provinsi){
		this.provinsi = provinsi;
	}

	public int getProvinsi(){
		return provinsi;
	}

	public void setKota(int kota){
		this.kota = kota;
	}

	public int getKota(){
		return kota;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
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

	public void setMetodeBayar(String metodeBayar){
		this.metodeBayar = metodeBayar;
	}

	public String getMetodeBayar(){
		return metodeBayar;
	}

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setOngkosKirim(String ongkosKirim){
		this.ongkosKirim = ongkosKirim;
	}

	public String getOngkosKirim(){
		return ongkosKirim;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setJasaPengiriman(String jasaPengiriman){
		this.jasaPengiriman = jasaPengiriman;
	}

	public String getJasaPengiriman(){
		return jasaPengiriman;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy;
	}

	public int getUpdatedBy(){
		return updatedBy;
	}

	public void setIdVoucher(int idVoucher){
		this.idVoucher = idVoucher;
	}

	public int getIdVoucher(){
		return idVoucher;
	}

	public void setPembeli(String pembeli){
		this.pembeli = pembeli;
	}

	public String getPembeli(){
		return pembeli;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdBank(int idBank){
		this.idBank = idBank;
	}

	public int getIdBank(){
		return idBank;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setDetail(List<DetailItem> detail){
		this.detail = detail;
	}

	public List<DetailItem> getDetail(){
		return detail;
	}

	public void setResi(String resi){
		this.resi = resi;
	}

	public String getResi(){
		return resi;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setJenisPengiriman(String jenisPengiriman){
		this.jenisPengiriman = jenisPengiriman;
	}

	public String getJenisPengiriman(){
		return jenisPengiriman;
	}

	@Override
 	public String toString(){
		return 
			"DataItemOrder{" +
			"provinsi = '" + provinsi + '\'' + 
			",kota = '" + kota + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",metode_bayar = '" + metodeBayar + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",ongkos_kirim = '" + ongkosKirim + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",jasa_pengiriman = '" + jasaPengiriman + '\'' + 
			",kode = '" + kode + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",id_voucher = '" + idVoucher + '\'' + 
			",pembeli = '" + pembeli + '\'' + 
			",id = '" + id + '\'' + 
			",id_bank = '" + idBank + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",detail = '" + detail + '\'' + 
			",resi = '" + resi + '\'' + 
			",status = '" + status + '\'' + 
			",jenis_pengiriman = '" + jenisPengiriman + '\'' + 
			"}";
		}
}