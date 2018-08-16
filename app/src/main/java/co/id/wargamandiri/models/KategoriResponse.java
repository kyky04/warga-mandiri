package co.id.wargamandiri.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KategoriResponse {

	@SerializedName("data")
	private List<DataItemKategori> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemKategori> data){
		this.data = data;
	}

	public List<DataItemKategori> getData(){
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
			"PengumumanResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}