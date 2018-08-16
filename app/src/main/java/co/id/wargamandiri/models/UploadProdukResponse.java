package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadProdukResponse {

	@SerializedName("data")
	private DataItemProduk data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemProduk data){
		this.data = data;
	}

	public DataItemProduk getData(){
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