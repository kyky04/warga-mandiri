package co.id.wargamandiri.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.alamat.KotaResponse;
import co.id.wargamandiri.models.alamat.ProvinsiResponse;
import co.id.wargamandiri.models.toko.ShowToko;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataTokoFragment extends Fragment {


    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_phone_toko)
    EditText etPhoneToko;
    @BindView(R.id.et_deskripsi_toko)
    EditText etDeskripsiToko;
    @BindView(R.id.et_alamat_toko)
    EditText etAlamatToko;
    @BindView(R.id.et_fb)
    EditText etFb;
    @BindView(R.id.et_wa)
    EditText etWa;
    @BindView(R.id.et_line)
    EditText etLine;
    @BindView(R.id.et_bbm)
    EditText etBbm;
    Unbinder unbinder;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    Session session;
    @BindView(R.id.et_pesan_pembuka)
    EditText etPesanPembuka;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.et_district)
    EditText etDistrict;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    List<String> districtList = new ArrayList<>();
    List<String> cityList = new ArrayList<>();
    List<Integer> cityListID = new ArrayList<>();
    int id_provinsi = 0;
    int id_kota = 0;

    public KelolaDataTokoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toko, container, false);
        unbinder = ButterKnife.bind(this, view);
        session = new Session(getActivity());
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getToko(session.getUser().getData().getIdToko());
            }
        });
        getToko(session.getUser().getData().getIdToko());


        etDeskripsiToko.setScroller(new Scroller(getActivity()));
        etDeskripsiToko.setMaxLines(10);
        etDeskripsiToko.setVerticalScrollBarEnabled(true);
        etDeskripsiToko.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getToko(int id) {
        refresh.setRefreshing(true);
        AndroidNetworking.get(WEB_URL + "api/backend/toko/{id_toko}")
                .addPathParameter("id_toko", String.valueOf(id))
                .build()
                .getAsObject(ShowToko.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        try {
                            if (response instanceof ShowToko) {
                                etNamaToko.setText(((ShowToko) response).getData().getNama());
                                etAlamatToko.setText(((ShowToko) response).getData().getAlamat());
                                etDeskripsiToko.setText(((ShowToko) response).getData().getDeskripsi());
                                etPesanPembuka.setText(((ShowToko) response).getData().getPesanPembuka());
                                etPhoneToko.setText(((ShowToko) response).getData().getNomorTelepon());
                                etWa.setText(((ShowToko) response).getData().getWhatsapp());
                                etBbm.setText(((ShowToko) response).getData().getBbm());
                                etLine.setText(((ShowToko) response).getData().getInstagram());
                                etFb.setText(((ShowToko) response).getData().getFacebook());
                                etFb.setText(((ShowToko) response).getData().getFacebook());
                                etCity.setText(((ShowToko) response).getData().getNamaKota());
                                etDistrict.setText(((ShowToko) response).getData().getNamaProvinsi());

//                                session.getUser().getData().setToko(session.getUser().getData());
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }

    private void updateToko(final int id) {
        refresh.setRefreshing(true);
        AndroidNetworking.put(WEB_URL + "api/backend/toko/{id_toko}")
                .addPathParameter("id_toko", String.valueOf(id))
                .addBodyParameter("nama", etNamaToko.getText().toString())
                .addBodyParameter("deskripsi", etDeskripsiToko.getText().toString())
                .addBodyParameter("pesan_pembuka", etPesanPembuka.getText().toString())
                .addBodyParameter("alamat", etAlamatToko.getText().toString())
                .addBodyParameter("nomor_telepon", etPhoneToko.getText().toString())
                .addBodyParameter("whatsapp", etWa.getText().toString())
                .addBodyParameter("facebook", etFb.getText().toString())
                .addBodyParameter("bbm", etBbm.getText().toString())
                .addBodyParameter("instagram", etLine.getText().toString())
                .addBodyParameter("id_provinsi", String.valueOf(id_provinsi))
                .addBodyParameter("id_kota", String.valueOf(id_kota))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refresh.setRefreshing(false);
                        try {
                            getToko(id);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Data Gagal Diupdate", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(true);
                    }
                });
    }

    public void dialogDistrict() {
        cityList.clear();
        cityListID.clear();
        ProvinsiResponse province = null;
        try {
            String myJson = inputStreamToString(getActivity().getAssets().open("provinces.json"));
            province = new Gson().fromJson(myJson, ProvinsiResponse.class);
            for (int i = 0; i < province.getData().size(); i++) {
                districtList.add(province.getData().get(i).getProvince());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] items = new String[districtList.size()];
        items = districtList.toArray(items);

        final String[] finalItems = items;
        final ProvinsiResponse finalProvince = province;
        CommonUtil.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etDistrict.setText(finalItems[which]);
                etDistrict.setError(null);

                if (Integer.parseInt(finalProvince.getData().get(which).getProvinceId()) != id_provinsi) {
                    etCity.setText("");
                }

                try {
                    String myJson = inputStreamToString(getActivity().getAssets().open("regencies.json"));
                    KotaResponse regency = new Gson().fromJson(myJson, KotaResponse.class);
                    for (int i = 0; i < regency.getData().size(); i++) {
                        Log.d("ID", "onClick: " + regency.getData().get(i).getProvinceId() + " " + finalProvince.getData().get(which).getProvinceId());
                        if (regency.getData().get(i).getProvinceId().equals(finalProvince.getData().get(which).getProvinceId())) {
                            cityList.add(regency.getData().get(i).getCityName());
                            cityListID.add(Integer.valueOf(regency.getData().get(i).getCityId()));
                            id_provinsi = Integer.parseInt(regency.getData().get(i).getProvinceId());

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void dialogCity() {

        String[] items = new String[cityList.size()];
        items = cityList.toArray(items);
        final String[] finalItems = items;

        CommonUtil.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etCity.setText(finalItems[which]);
                etCity.setError(null);
                id_kota = cityListID.get(which);
            }
        });
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }



    @OnClick({R.id.et_district, R.id.et_city, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_district:
                dialogDistrict();
                break;
            case R.id.et_city:
                if (id_provinsi == 0) {
                    Toast.makeText(getActivity(), "Isi provinsi", Toast.LENGTH_SHORT).show();
                } else {
                    dialogCity();
                }
                break;
            case R.id.btn_update:
                updateToko(session.getUser().getData().getIdToko());
                break;
        }
    }
}
