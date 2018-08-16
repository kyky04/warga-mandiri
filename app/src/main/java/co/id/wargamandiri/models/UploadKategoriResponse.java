package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

public class UploadKategoriResponse {

	@SerializedName("data")
	private DataItemKategori data;

	@SerializedName("status")
	private boolean status;

	public void setData(DataItemKategori data){
		this.data = data;
	}

	public DataItemKategori getData(){
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