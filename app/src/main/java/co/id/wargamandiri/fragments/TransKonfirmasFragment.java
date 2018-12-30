package co.id.wargamandiri.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.EmptyAdapter;
import co.id.wargamandiri.adapter.KonfirmasiAdapter;
import co.id.wargamandiri.models.UploadKonfirmasiResponse;
import co.id.wargamandiri.models.transaksi.konfirmasi.DataItemKonfirmasi;
import co.id.wargamandiri.models.transaksi.konfirmasi.KonfirmasiResponse;
import co.id.wargamandiri.models.transaksi.order.DataItemOrder;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.KATEGORI;
import static co.id.wargamandiri.data.Constans.KONFIRMASI;
import static co.id.wargamandiri.data.Constans.KONFIRMASI_RELATION;
import static co.id.wargamandiri.data.Constans.ORDER;
import static co.id.wargamandiri.data.Constans.ORDER_RELATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransKonfirmasFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAddBanner;
    Unbinder unbinder;

    KonfirmasiAdapter adapter;

    Session session;

    public TransKonfirmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_toko, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());


        adapter = new KonfirmasiAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        getData();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                getData();
            }
        });

        adapter.setOnItemClickListener(new KonfirmasiAdapter.OnItemClick() {
            @Override
            public void onItemClick(int pos) {
                showData(adapter.getItem(pos));
            }

            @Override
            public void onApprove(int pos) {
                approval(1,adapter.getItem(pos).getId());
            }

            @Override
            public void onCancel(int pos) {
                approval(2,adapter.getItem(pos).getId());
            }
        });
        fabAddBanner.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_add_banner)
    public void onViewClicked() {
        addData();
    }

    private void getData() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(KONFIRMASI)
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addQueryParameter("includes", KONFIRMASI_RELATION)
                .build()
                .getAsObject(KonfirmasiResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof KonfirmasiResponse) {
                            if (((KonfirmasiResponse) response).getData().size() > 0) {
                                adapter.swap(((KonfirmasiResponse) response).getData());
                                recyclerView.setAdapter(adapter);
                            } else {
                                recyclerView.setAdapter(new EmptyAdapter(getActivity()));
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR RESPONSE", anError.getErrorDetail());
                        refresh.setRefreshing(false);

                    }
                });
    }

    private void approval(final int approve, int id_konfirmasi) {
        refresh.setRefreshing(true);
        ANRequest.GetRequestBuilder approval;
        if (approve == 1) {
            approval = new ANRequest.GetRequestBuilder(KONFIRMASI + "/{id_konfirmasi}/approve");
        } else {
            approval = new ANRequest.GetRequestBuilder(KONFIRMASI + "/{id_konfirmasi}/tolak");
        }

        approval
                .addPathParameter("id_konfirmasi", String.valueOf(id_konfirmasi))
                .build()
                .getAsObject(UploadKonfirmasiResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof UploadKonfirmasiResponse) {
                            if (((UploadKonfirmasiResponse) response).isStatus()) {
                                String konfirmasi = "";
                                if (approve == 1) {
                                    konfirmasi = "Konfirmasi order di setujui";
                                } else {
                                    konfirmasi = "Konfirmasi order di tolak";
                                }
                                getData();
                                CommonUtil.showToast(getActivity(), konfirmasi);
                            } else {
                                CommonUtil.showToast(getActivity(), "Konfirmasi gagal");
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(getActivity(), "Masalah Koneksi");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            File file = new File(image.getPath());

        }
    }


    private void addData() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogKategoriFragment newFragment = new DialogKategoriFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogKategoriFragment.CallbackResult() {
            @Override
            public void sendResult(Object obj) {
                getData();
            }
        });
    }

    private void showData(DataItemKonfirmasi item) {
        FragmentManager fragmentManager = getFragmentManager();
        DetailKonfirmasiFragment newFragment = DetailKonfirmasiFragment.newInstance(item);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DetailKonfirmasiFragment.CallbackResult() {
            @Override
            public void sendResult() {

            }
        });
    }

    private void delete(int id) {
        AndroidNetworking.delete(KATEGORI + "/{id}")
                .addPathParameter("id", String.valueOf(id))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        getData();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
