package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MemberResponse{

	@SerializedName("data")
	private List<DataItemMember> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemMember> data){
		this.data = data;
	}

	public List<DataItemMember> getData(){
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
			"MemberResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}