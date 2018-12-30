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
import co.id.wargamandiri.adapter.PengumumanAdapter;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.PengumumanResponse;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.NEWS;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataPengumumanFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    PengumumanAdapter adapter;

    Session session;

    public KelolaDataPengumumanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_toko, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PengumumanAdapter(getActivity());
        getPengumuman();
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPengumuman();
            }
        });

        adapter.setOnItemClick(new PengumumanAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemEdit(int position) {
                showEditDialogFullscreen(adapter.getItem(position));
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
        showDialogFullscreen();
    }

    private void getPengumuman() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(NEWS)
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .build()
                .getAsObject(PengumumanResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof PengumumanResponse) {
                            if(((PengumumanResponse) response).getData().size() > 0){
                                adapter.swap(((PengumumanResponse) response).getData());
                                recyclerView.setAdapter(adapter);
                            }else {
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


    private void showDialogFullscreen() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogPengumumanFragment newFragment = new DialogPengumumanFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogPengumumanFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getPengumuman();
            }
        });
    }

    private void showEditDialogFullscreen(DataItemPengumuman itemPengumuman) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogPengumumanFragment newFragment = DialogPengumumanFragment.newInstance(itemPengumuman);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogPengumumanFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getPengumuman();
            }
        });
    }

    private void delete(int id_banner) {
        AndroidNetworking.delete(NEWS + "/{id_pengumuman}")
                .addPathParameter("id_pengumuman", String.valueOf(id_banner))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Pengumuman Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        getPengumuman();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
