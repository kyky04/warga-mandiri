package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadPengumumaResponse{

	@SerializedName("data")
	private DataItemPengumuman data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemPengumuman data){
		this.data = data;
	}

	public DataItemPengumuman getData(){
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
			"UploadPengumumaResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}