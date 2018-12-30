package co.id.wargamandiri.models.toko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PesanPembukaResponse{

	@SerializedName("data")
	private List<DataItemPesan> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemPesan> data){
		this.data = data;
	}

	public List<DataItemPesan> getData(){
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
			"PesanPembukaResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}