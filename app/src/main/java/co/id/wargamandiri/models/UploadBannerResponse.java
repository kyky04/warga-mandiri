package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadBannerResponse{

	@SerializedName("data")
	private DataItemBanner data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemBanner data){
		this.data = data;
	}

	public DataItemBanner getData(){
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