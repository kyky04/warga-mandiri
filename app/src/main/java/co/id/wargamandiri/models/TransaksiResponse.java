package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TransaksiResponse{

	@SerializedName("data")
	private List<DataItemTransaksi> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemTransaksi> data){
		this.data = data;
	}

	public List<DataItemTransaksi> getData(){
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
			"TransaksiResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}