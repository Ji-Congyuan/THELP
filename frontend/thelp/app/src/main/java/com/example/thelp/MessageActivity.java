package com.example.thelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.data.Message;
import com.example.data.MessageAdapter;
import com.example.data.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private List<Message> messageList_unread = new ArrayList<>();
    private List<Message> messageList_read = new ArrayList<>();
    private RecyclerView recyclerView_unread = null;
    private RecyclerView recyclerView_read = null;
    private MessageAdapter adapter_unread = null;
    private MessageAdapter adapter_read = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        getMsgList();
        recyclerView_unread = findViewById(R.id.recycler_view_unread);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_unread.setLayoutManager(linearLayoutManager);
        adapter_unread = new MessageAdapter(messageList_unread, this);
        recyclerView_unread.setAdapter(adapter_unread);

        recyclerView_read = findViewById(R.id.recycler_view_read);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView_read.setLayoutManager(linearLayoutManager2);
        adapter_read = new MessageAdapter(messageList_read, this);
        recyclerView_read.setAdapter(adapter_read);



//        listener = new MainActivity.EndLessOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                getMoreData(currentPage);
//            }
//        };
//        recyclerView.addOnScrollListener(listener);
    }

    private void getMsgList(){
        for (int i = 0; i < 3; i++) {
            String picUrl = "https://overwatch.nosdn.127.net/2/heroes/Echo/hero-select-portrait.png";
            messageList_unread.add(new Message(picUrl,"昵称"+String.valueOf(i), "订单奖励多少呀呀呀？", "00:00", false));
        }
        for (int i = 3; i < 5; i++) {
            String picUrl = "https://overwatch.nosdn.127.net/2/heroes/Echo/hero-select-portrait.png";
            messageList_read.add(new Message(picUrl,"昵称"+String.valueOf(i+3), "给个好评兄弟？", "12:00", true));
        }
    }
}
