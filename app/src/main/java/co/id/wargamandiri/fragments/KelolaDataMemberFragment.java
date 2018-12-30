package co.id.wargamandiri.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.activities.ChatActivity;
import co.id.wargamandiri.activities.KelolaActivity;
import co.id.wargamandiri.activities.MenuActivity;
import co.id.wargamandiri.adapter.EmptyAdapter;
import co.id.wargamandiri.adapter.MemberAdapter;
import co.id.wargamandiri.models.DataItemMember;
import co.id.wargamandiri.models.MemberResponse;
import co.id.wargamandiri.models.UploadMemberResponse;
import co.id.wargamandiri.models.UserResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.MEMBER;
import static co.id.wargamandiri.data.Constans.MEMBER_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataMemberFragment extends Fragment {
    private static final String TAG = "KelolaDataMemberFragmen";

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    Unbinder unbinder;

    MemberAdapter adapter;

    Session session;

    public KelolaDataMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelola_data_member, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MemberAdapter(getActivity());
        getAllMember();
        recyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMember();
            }
        });

        adapter.setOnItemClick(new MemberAdapter.OnItemClick() {

            @Override
            public void onChat(int pos) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pengirim", adapter.getItem(pos).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onInfo(int pos) {

            }

            @Override
            public void onSwicth(int pos, boolean checked) {
                Log.d(TAG, "onSwicth: " + checked);
                if (checked) {
                    aktivasiMember(adapter.getItem(pos), 1);
                } else {
                    aktivasiMember(adapter.getItem(pos), 0);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getAllMember() {
        refresh.setRefreshing(true);
        AndroidNetworking.get(MEMBER)
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addQueryParameter("admin", String.valueOf(false))
                .build()
                .getAsObject(MemberResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        if (response instanceof MemberResponse) {
                            if (((MemberResponse) response).getData().size() > 0) {
                                adapter.swap(((MemberResponse) response).getData());
                                recyclerView.setAdapter(adapter);
                            } else {
                                recyclerView.setAdapter(new EmptyAdapter(getActivity()));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void aktivasiMember(final DataItemMember member, final int status) {
        AndroidNetworking.put(MEMBER_STATUS + "/{id}")
                .addPathParameter("id", String.valueOf(member.getId()))
                .addBodyParameter("status", String.valueOf(status))
                .build()
                .getAsObject(UploadMemberResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof UploadMemberResponse) {
                            if (status == 0) {
                                CommonUtil.showToast(getActivity(), "User dengan nama " + member.getName() + " dinonaktifkan");
                            } else {
                                CommonUtil.showToast(getActivity(), "User dengan nama " + member.getName() + " diaktifkan");
                            }
                            getAllMember();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

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
                getAllMember();
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
                getAllMember();
            }
        });
    }

    private void delete(int id) {
        AndroidNetworking.delete(MEMBER + "{id_pengumuman}")
                .addPathParameter("id_pengumuman", String.valueOf(id))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Pengumuman Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        getAllMember();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
