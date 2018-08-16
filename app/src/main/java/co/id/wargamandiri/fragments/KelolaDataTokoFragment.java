package co.id.wargamandiri.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.Toko;
import co.id.wargamandiri.models.TokoResponse;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.services.FastConstans.WEB_URL;

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
    @BindView(R.id.btn_update)
    Button btnUpdate;

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
                .getAsObject(Toko.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        try {
                            if (response instanceof Toko) {
                                etNamaToko.setText(((Toko) response).getNama());
                                etAlamatToko.setText(((Toko) response).getAlamat());
                                etDeskripsiToko.setText(((Toko) response).getDeskripsi());
                                etPhoneToko.setText(((Toko) response).getNomorTelepon());
                                etWa.setText(((Toko) response).getWhatsapp());
                                etBbm.setText(((Toko) response).getBbm());
                                etLine.setText(((Toko) response).getInstagram());
                                etFb.setText(((Toko) response).getFacebook());

                                session.getUser().getData().setToko((Toko) response);
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
                .addBodyParameter("alamat", etAlamatToko.getText().toString())
                .addBodyParameter("nomor_telepon", etPhoneToko.getText().toString())
                .addBodyParameter("whatsapp", etWa.getText().toString())
                .addBodyParameter("facebook", etFb.getText().toString())
                .addBodyParameter("bbm", etBbm.getText().toString())
                .addBodyParameter("instagram", etLine.getText().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refresh.setRefreshing(false);
                        try {
                            getToko(id);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(true);
                    }
                });
    }

    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        updateToko(session.getUser().getData().getIdToko());
    }
}
