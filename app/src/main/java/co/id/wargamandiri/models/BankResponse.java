package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BankResponse{

	@SerializedName("data")
	private List<DataItemBank> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemBank> data){
		this.data = data;
	}

	public List<DataItemBank> getData(){
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
			"BankResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}