package co.id.wargamandiri.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VoucherResponse{

	@SerializedName("data")
	private List<DataItemVoucher> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemVoucher> data){
		this.data = data;
	}

	public List<DataItemVoucher> getData(){
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
			"VoucherResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}