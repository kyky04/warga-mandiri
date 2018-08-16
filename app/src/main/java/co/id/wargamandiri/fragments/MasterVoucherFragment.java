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
import co.id.wargamandiri.adapter.AdapterVoucher;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.DataItemVoucher;
import co.id.wargamandiri.models.VoucherResponse;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.services.FastConstans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterVoucherFragment extends Fragment {


    @BindView(R.id.rv_banner)
    RecyclerView rvBanner;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.fab_add_banner)
    FloatingActionButton fabAddBanner;
    Unbinder unbinder;

    AdapterVoucher adapterBanner;

    Session session;

    public MasterVoucherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_toko, container, false);
        unbinder = ButterKnife.bind(this, view);


        rvBanner.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBanner = new AdapterVoucher(getActivity());
        getPengumuman();
        rvBanner.setAdapter(adapterBanner);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPengumuman();
            }
        });

        adapterBanner.setOnItemClick(new AdapterVoucher.OnItemClick() {
            @Override
            public void onItemEditClick(int pos, DataItemVoucher dataItemBanner) {
//                showEditDialogFullscreen(dataItemBanner);
            }

            @Override
            public void onItemDeleteClick(int pos, DataItemVoucher dataItemBanner) {
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

    private void getPengumuman() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(WEB_URL + "api/backend/master/voucher")
                .build()
                .getAsObject(VoucherResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof VoucherResponse) {
                            adapterBanner.swap(((VoucherResponse) response).getData());
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
        DialogVoucherFragment newFragment = new DialogVoucherFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogVoucherFragment.CallbackResult() {
            @Override
            public void sendResult(Object obj) {
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
            public void sendResult(Object obj) {
                getPengumuman();
            }
        });
    }

    private void deleteBanner(int id_banner) {
        AndroidNetworking.delete(WEB_URL + "api/master/voucher/{id_voucher}")
                .addPathParameter("id_voucher", String.valueOf(id_banner))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Voucher Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        getPengumuman();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
