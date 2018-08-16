package co.id.wargamandiri.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.TokoResponse;
import co.id.wargamandiri.models.UserResponse;
import co.id.wargamandiri.services.OauthConstans;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataUserFragment extends Fragment {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    Unbinder unbinder;

    public KelolaDataUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUser(1);
            }
        });

        getUser(1);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUser(int id) {
        refresh.setRefreshing(true);
        AndroidNetworking.get(OauthConstans.AUTHENTICATION_SERVER_URL + "api/toko/{id_toko}/pemilik")
                .addPathParameter("id_toko", String.valueOf(id))
                .build()
                .getAsObject(UserResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        try {
                            if(response instanceof UserResponse){
                                etUsername.setText(((UserResponse) response).getNama());
                                etAlamat.setText(((UserResponse) response).getAlamat());
                                etPhone.setText(((UserResponse) response).getNoHp());
                                etEmail.setText(((UserResponse) response).getEmail());
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }
}
