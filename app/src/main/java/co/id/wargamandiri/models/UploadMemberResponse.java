package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadMemberResponse {

	@SerializedName("data")
	private DataItemMember data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemMember data){
		this.data = data;
	}

	public DataItemMember getData(){
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
			"UploadKategoriResponse{" +
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}