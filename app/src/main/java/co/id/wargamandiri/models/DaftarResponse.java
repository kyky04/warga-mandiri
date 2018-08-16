package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class DaftarResponse{

	@SerializedName("data")
	private DataDaftar data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataDaftar data){
		this.data = data;
	}

	public DataDaftar getData(){
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
			"DaftarResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}