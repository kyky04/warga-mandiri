package co.id.wargamandiri.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
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
import co.id.wargamandiri.adapter.ProdukAdapter;
import co.id.wargamandiri.models.DataItemProduk;
import co.id.wargamandiri.models.ProdukResponse;
import co.id.wargamandiri.utils.Session;
import co.id.wargamandiri.utils.Tools;
import co.id.wargamandiri.views.SpacingItemDecoration;

import static co.id.wargamandiri.data.Constans.PRODUK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterProdukFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    ProdukAdapter adapter;

    Session session;

    public MasterProdukFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_toko, container, false);

        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        adapter = new ProdukAdapter(getActivity());
        recyclerView.setAdapter(new EmptyAdapter(getActivity()));

        getData();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        adapter.setOnItemClick(new ProdukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                upload();
            }

            @Override
            public void onItemEdit(int position) {
                edit(adapter.getItem(position));
            }

            @Override
            public void onItemDelete(int position) {
                delete(adapter.getItem(position).getId());

            }
        });

        fabAdd.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_add_banner)
    public void onViewClicked() {
        upload();
    }

    private void getData() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(PRODUK)
                .addQueryParameter("includes","kategori,gambar")
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .build()
                .getAsObject(ProdukResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof ProdukResponse) {
                            if(((ProdukResponse) response).getData().size() > 0){
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                                recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 8), true));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setNestedScrollingEnabled(false);

                                adapter.swap(((ProdukResponse) response).getData());
                                recyclerView.setAdapter(adapter);
                            }else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            File file = new File(image.getPath());

        }
    }


    private void upload() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogProdukFragment newFragment = new DialogProdukFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogProdukFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getData();
            }
        });
    }

    private void edit(DataItemProduk item) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogProdukFragment newFragment = DialogProdukFragment.newInstance(item);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogProdukFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getData();
            }
        });
    }

    private void delete(int id) {
        AndroidNetworking.delete(PRODUK + "/{id}")
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
