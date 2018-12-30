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
import co.id.wargamandiri.adapter.SliderAdapter;
import co.id.wargamandiri.models.BannerResponse;
import co.id.wargamandiri.models.DataItemBanner;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.SLIDER;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaBannerFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAddBanner;
    Unbinder unbinder;

    SliderAdapter adapter;

    Session session;

    public KelolaBannerFragment() {
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
        adapter = new SliderAdapter(getActivity());
        getData();
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        adapter.setOnItemClick(new SliderAdapter.OnItemClick() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onEditClick(int pos) {
                edit(adapter.getItem(pos));
            }

            @Override
            public void onDeleteClick(int pos) {
                delete(adapter.getItem(pos).getId());

            }

        });
        fabAddBanner.setVisibility(View.VISIBLE);
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
        AndroidNetworking.get(SLIDER)
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .build()
                .getAsObject(BannerResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof BannerResponse) {
                            if(((BannerResponse) response).getData().size() > 0){
                                adapter.swap(((BannerResponse) response).getData());
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



    private void upload() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogSliderFragment newFragment = new DialogSliderFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogSliderFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getData();
            }
        });
    }

    private void edit(DataItemBanner banner) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogSliderFragment newFragment = DialogSliderFragment.newInstance(banner);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogSliderFragment.CallbackResult() {
            @Override
            public void sendResult() {
                getData();
            }
        });
    }

    private void delete(int id_banner){
        AndroidNetworking.delete(SLIDER + "/{id_banner}")
                .addPathParameter("id_banner", String.valueOf(id_banner))
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
