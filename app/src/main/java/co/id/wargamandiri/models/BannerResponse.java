package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BannerResponse{

	@SerializedName("data")
	private List<DataItemBanner> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemBanner> data){
		this.data = data;
	}

	public List<DataItemBanner> getData(){
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
			"BannerResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}