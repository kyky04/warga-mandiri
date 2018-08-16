package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadVoucherResponse {

	@SerializedName("data")
	private DataItemVoucher data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemVoucher data){
		this.data = data;
	}

	public DataItemVoucher getData(){
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