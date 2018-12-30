package co.id.wargamandiri.models.member;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TopupResponse{

	@SerializedName("data")
	private List<DataItemTopup> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemTopup> data){
		this.data = data;
	}

	public List<DataItemTopup> getData(){
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
			"TopupResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}