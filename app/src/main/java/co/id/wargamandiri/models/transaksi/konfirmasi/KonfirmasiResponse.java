package co.id.wargamandiri.models.transaksi.konfirmasi;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KonfirmasiResponse implements Serializable {

	@SerializedName("data")
	private List<DataItemKonfirmasi> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItemKonfirmasi> data){
		this.data = data;
	}

	public List<DataItemKonfirmasi> getData(){
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
			"KonfirmasiResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}