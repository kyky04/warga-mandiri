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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.AdapterUser;
import co.id.wargamandiri.models.UserResponse;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.services.FastConstans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataMemberFragment extends Fragment {


    @BindView(R.id.rv_banner)
    RecyclerView rvBanner;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAddBanner;
    Unbinder unbinder;

    AdapterUser adapterBanner;

    Session session;

    public KelolaDataMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_toko, container, false);
        unbinder = ButterKnife.bind(this, view);


        rvBanner.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBanner = new AdapterUser(getActivity());
        getAllMember(1);
        rvBanner.setAdapter(adapterBanner);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMember(1);
            }
        });

        adapterBanner.setOnItemClick(new AdapterUser.OnItemClick() {
            @Override
            public void onItemEditClick(int pos, UserResponse dataItemBanner) {
                showEditDialogFullscreen(dataItemBanner);
            }

            @Override
            public void onItemDeleteClick(int pos, UserResponse dataItemBanner) {
                deleteBanner(dataItemBanner.getId());
            }
        });
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

    private void getAllMember(int id_toko) {
        refresh.setRefreshing(true);
        AndroidNetworking.get(WEB_URL + "api/backend/member")
                .addPathParameter("id_toko", String.valueOf(id_toko))
                .build()
                .getAsObjectList(UserResponse.class, new ParsedRequestListener<List<UserResponse>>() {
                    @Override
                    public void onResponse(List<UserResponse> response) {
                        refresh.setRefreshing(false);
                        adapterBanner.swap(response);
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
            public void sendResult(Object obj) {
                getAllMember(1);
            }
        });
    }

    private void showEditDialogFullscreen(UserResponse itemPengumuman) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogMemberFragment newFragment = DialogMemberFragment.newInstance(itemPengumuman);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogMemberFragment.CallbackResult() {
            @Override
            public void sendResult(Object obj) {
                getAllMember(1);
            }
        });
    }

    private void deleteBanner(int id_banner) {
        AndroidNetworking.delete(WEB_URL + "api/master/pengumuman/{id_pengumuman}")
                .addPathParameter("id_pengumuman", String.valueOf(id_banner))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "DataItemPengumuman Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        getAllMember(1);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
