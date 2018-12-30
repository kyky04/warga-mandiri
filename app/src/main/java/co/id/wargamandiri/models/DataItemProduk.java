package co.id.wargamandiri.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItemProduk implements Serializable {

	@SerializedName("gambar")
	private List<ImageItem> image;

	@SerializedName("gambar_utama")
	private String gambarUtama;

	@SerializedName("id_toko")
	private int idToko;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("kategori")
	private DataItemKategori kategori;

	@SerializedName("stok")
	private int stok;

	@SerializedName("created_by")
	private int createdBy;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private int harga;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("updated_by")
	private Object updatedBy;

	@SerializedName("id")
	private int id;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("harga_diskon")
	private int hargaDiskon;

	@SerializedName("status")
	private int status;

	public void setImage(List<ImageItem> image){
		this.image = image;
	}

	public List<ImageItem> getImage(){
		return image;
	}

	public void setGambarUtama(String gambarUtama){
		this.gambarUtama = gambarUtama;
	}

	public String getGambarUtama(){
		return gambarUtama;
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

	public void setKategori(DataItemKategori kategori){
		this.kategori = kategori;
	}

	public DataItemKategori getKategori(){
		return kategori;
	}

	public void setStok(int stok){
		this.stok = stok;
	}

	public int getStok(){
		return stok;
	}

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setHarga(int harga){
		this.harga = harga;
	}

	public int getHarga(){
		return harga;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setHargaDiskon(int hargaDiskon){
		this.hargaDiskon = hargaDiskon;
	}

	public int getHargaDiskon(){
		return hargaDiskon;
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
			"DataItemProduk{" +
			"image = '" + image + '\'' + 
			",gambar_utama = '" + gambarUtama + '\'' + 
			",id_toko = '" + idToko + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",stok = '" + stok + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",nama = '" + nama + '\'' + 
			",harga = '" + harga + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",id = '" + id + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",harga_diskon = '" + hargaDiskon + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}