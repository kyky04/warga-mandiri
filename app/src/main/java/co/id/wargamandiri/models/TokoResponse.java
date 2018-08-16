package co.id.wargamandiri.models;

public class TokoResponse{
	private String whatsapp;
	private Object namaProvinsi;
	private Object latitude;
	private String createdAt;
	private Object idKota;
	private String instagram;
	private Object idProvinsi;
	private String twitter;
	private String updatedAt;
	private boolean pos;
	private int id;
	private String email;
	private Object longitude;
	private boolean jne;
	private boolean tiki;
	private String facebook;
	private String kodePos;
	private String bbm;
	private Object createdBy;
	private String alamat;
	private String biayaCod;
	private String nama;
	private String nomor_telepon;
	private Object updatedBy;
	private boolean cod;
	private String deskripsi;
	private Object namaKota;

	public void setWhatsapp(String whatsapp){
		this.whatsapp = whatsapp;
	}

	public String getWhatsapp(){
		return whatsapp;
	}

	public void setNamaProvinsi(Object namaProvinsi){
		this.namaProvinsi = namaProvinsi;
	}

	public Object getNamaProvinsi(){
		return namaProvinsi;
	}

	public void setLatitude(Object latitude){
		this.latitude = latitude;
	}

	public Object getLatitude(){
		return latitude;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIdKota(Object idKota){
		this.idKota = idKota;
	}

	public Object getIdKota(){
		return idKota;
	}

	public void setInstagram(String instagram){
		this.instagram = instagram;
	}

	public String getInstagram(){
		return instagram;
	}

	public void setIdProvinsi(Object idProvinsi){
		this.idProvinsi = idProvinsi;
	}

	public Object getIdProvinsi(){
		return idProvinsi;
	}

	public void setTwitter(String twitter){
		this.twitter = twitter;
	}

	public String getTwitter(){
		return twitter;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPos(boolean pos){
		this.pos = pos;
	}

	public boolean isPos(){
		return pos;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}

	public void setJne(boolean jne){
		this.jne = jne;
	}

	public boolean isJne(){
		return jne;
	}

	public void setTiki(boolean tiki){
		this.tiki = tiki;
	}

	public boolean isTiki(){
		return tiki;
	}

	public void setFacebook(String facebook){
		this.facebook = facebook;
	}

	public String getFacebook(){
		return facebook;
	}

	public void setKodePos(String kodePos){
		this.kodePos = kodePos;
	}

	public String getKodePos(){
		return kodePos;
	}

	public void setBbm(String bbm){
		this.bbm = bbm;
	}

	public String getBbm(){
		return bbm;
	}

	public void setCreatedBy(Object createdBy){
		this.createdBy = createdBy;
	}

	public Object getCreatedBy(){
		return createdBy;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setBiayaCod(String biayaCod){
		this.biayaCod = biayaCod;
	}

	public String getBiayaCod(){
		return biayaCod;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNomor_telepon(String nomor_telepon){
		this.nomor_telepon = nomor_telepon;
	}

	public String getNomor_telepon(){
		return nomor_telepon;
	}

	public void setUpdatedBy(Object updatedBy){
		this.updatedBy = updatedBy;
	}

	public Object getUpdatedBy(){
		return updatedBy;
	}

	public void setCod(boolean cod){
		this.cod = cod;
	}

	public boolean isCod(){
		return cod;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setNamaKota(Object namaKota){
		this.namaKota = namaKota;
	}

	public Object getNamaKota(){
		return namaKota;
	}

	@Override
 	public String toString(){
		return 
			"TokoResponse{" + 
			"whatsapp = '" + whatsapp + '\'' + 
			",nama_provinsi = '" + namaProvinsi + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_kota = '" + idKota + '\'' + 
			",instagram = '" + instagram + '\'' + 
			",id_provinsi = '" + idProvinsi + '\'' + 
			",twitter = '" + twitter + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",pos = '" + pos + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",jne = '" + jne + '\'' + 
			",tiki = '" + tiki + '\'' + 
			",facebook = '" + facebook + '\'' + 
			",kode_pos = '" + kodePos + '\'' + 
			",bbm = '" + bbm + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",biaya_cod = '" + biayaCod + '\'' + 
			",nama = '" + nama + '\'' + 
			",nomor_telepon = '" + nomor_telepon + '\'' +
			",updated_by = '" + updatedBy + '\'' + 
			",cod = '" + cod + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",nama_kota = '" + namaKota + '\'' + 
			"}";
		}
}
