package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadBankResponse {

	@SerializedName("data")
	private DataItemBank data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemBank data){
		this.data = data;
	}

	public DataItemBank getData(){
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