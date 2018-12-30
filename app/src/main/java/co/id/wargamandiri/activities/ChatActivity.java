package co.id.wargamandiri.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.ChatAdapter;
import co.id.wargamandiri.adapter.EmptyAdapter;
import co.id.wargamandiri.models.BankResponse;
import co.id.wargamandiri.models.UploadChatResponse;
import co.id.wargamandiri.models.chat.ChatResponse;
import co.id.wargamandiri.models.chat.DataItemChat;
import co.id.wargamandiri.services.MyFirebaseMessagingService;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.BANK;
import static co.id.wargamandiri.data.Constans.CHAT_ROOM;
import static co.id.wargamandiri.data.Constans.CHAT_USER;

public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity";
    @BindView(R.id.btnSend)
    ImageButton btnSend;
    @BindView(R.id.recyclerChat)
    RecyclerView recyclerChat;
    @BindView(R.id.editWriteMessage)
    EditText editWriteMessage;

    ChatAdapter chatAdapter;

    int id_pengirim;

    Session session;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        IntentFilter intent = new IntentFilter("co.id.wargamandiri.CUSTOM_EVENT");
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, intent);

        session = new Session(this);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerChat.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(this);

        recyclerChat.setAdapter(chatAdapter);
        Bundle data = getIntent().getExtras();
        id_pengirim = data.getInt("pengirim", 0);

        Log.d(TAG, "pengirim: " + id_pengirim + " penerima " + session.getUser().getData().getId());

        getData();


    }

    private void getData() {
        AndroidNetworking.post(CHAT_ROOM)
                .addBodyParameter("pengirim", String.valueOf(id_pengirim))
                .addBodyParameter("penerima", String.valueOf(session.getUser().getData().getId()))
                .build()
                .getAsObject(ChatResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ChatResponse) {
                            if (((ChatResponse) response).getData().size() > 0) {
                                chatAdapter.swap(((ChatResponse) response).getData());
                                linearLayoutManager.scrollToPosition(chatAdapter.getItemCount() - 1);
                            } else {

                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR RESPONSE", anError.getErrorDetail());

                    }
                });
    }

    private void chatUser() {
        AndroidNetworking.post(CHAT_USER)
                .addBodyParameter("pengirim", String.valueOf(id_pengirim))
                .addBodyParameter("penerima", String.valueOf(session.getUser().getData().getId()))
                .addBodyParameter("pesan", editWriteMessage.getText().toString())
                .addBodyParameter("created_by", String.valueOf(session.getUser().getData().getId()))
                .build()
                .getAsObject(UploadChatResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof UploadChatResponse) {
                            if (((UploadChatResponse) response).getData() != null) {
                                chatAdapter.add(((UploadChatResponse) response).getData());
                                chatAdapter.notifyDataSetChanged();
                                editWriteMessage.setText("");

                                linearLayoutManager.scrollToPosition(chatAdapter.getItemCount() - 1);
                            } else {

                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR RESPONSE", anError.getErrorDetail());

                    }
                });
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
        chatUser();
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            String pesan = intent.getStringExtra("pesan");
            Log.d(TAG, "onReceive: " + pesan);
            DataItemChat chat = new DataItemChat();
            chat.setPesan(pesan);
            chat.setIdUser(id_pengirim);

            chatAdapter.add(chat);
            chatAdapter.notifyDataSetChanged();

            linearLayoutManager.scrollToPosition(chatAdapter.getItemCount() - 1);
        }
    };
}
