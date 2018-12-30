package co.id.wargamandiri.models.alamat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KotaResponse{

	@SerializedName("data")
	private List<DataItemKota> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemKota> data){
		this.data = data;
	}

	public List<DataItemKota> getData(){
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
			"KotaResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}