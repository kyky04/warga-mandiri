package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

import co.id.wargamandiri.models.transaksi.konfirmasi.DataItemKonfirmasi;

public class UploadKonfirmasiResponse {

	@SerializedName("data")
	private DataItemKonfirmasi data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemKonfirmasi data){
		this.data = data;
	}

	public DataItemKonfirmasi getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"UploadBannerResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}