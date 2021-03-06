package com.example.data;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thelp.ChatActivity;
import com.example.thelp.R;
import com.github.library.bubbleview.BubbleImageView;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.button.MaterialButton;

import java.security.InvalidParameterException;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;
import cn.carbs.android.avatarimageview.library.SquareAvatarImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Message> messageList;
    private Activity activity;
    private int sendLayout;
    private int receiveLayout;
    private OnItemClickedListener onItemClickedListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private SquareAvatarImageView avatarView;
        private BubbleTextView bubbleTextView;
        private BubbleImageView bubbleImageView;
        private TextView timeView;


        public ViewHolder(View view, ChatAdapter.OnItemClickedListener clickListener) {
            super(view);
            avatarView = view.findViewById(R.id.chat_avatar);
            bubbleTextView = view.findViewById(R.id.chat_content_text);
            bubbleImageView = view.findViewById(R.id.chat_content_image);
            timeView = view.findViewById(R.id.chat_time);

            // set listeners
            avatarView.setOnClickListener(v -> clickListener.onAvatarClicked(getAdapterPosition()));
            bubbleImageView.setOnClickListener(v -> clickListener.onImageClicked(bubbleImageView, getAdapterPosition()));
        }
    }

    public ChatAdapter(List<Message> messages, Activity activity, int sendLayout, int receiveLayout) {
        this.messageList = messages;
        this.activity = activity;
        this.sendLayout = sendLayout;
        this.receiveLayout = receiveLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewLayout;
        if (viewType == Message.RECEIVE) {
            viewLayout = this.receiveLayout;
        } else if (viewType == Message.SEND) {
            viewLayout = this.sendLayout;
        } else {
            throw new InvalidParameterException("Invalid message type error(receive/send)");
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(viewLayout, parent, false);
        return new ViewHolder(view, this.onItemClickedListener);
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getPosition();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        Glide.with(activity).load(message.getAvatar()).centerCrop().into(holder.avatarView);
        holder.timeView.setText(message.getTime());
        if (message.getType() == Message.TEXT) {
            holder.bubbleTextView.setText(message.getFullContent());
            holder.bubbleTextView.setVisibility(View.VISIBLE);
            holder.bubbleImageView.setVisibility(View.GONE);
        } else if (message.getType() == Message.IMAGE) {
            Glide.with(activity).load(message.getFullContent()).into(holder.bubbleImageView);
            holder.bubbleImageView.setVisibility(View.VISIBLE);
            holder.bubbleTextView.setVisibility(View.GONE);
        } else {
            // TODO: raise error
            throw new InvalidParameterException("invalid message type(text/image)");
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setClickListener(OnItemClickedListener listener) {
        this.onItemClickedListener = listener;
    }

    public interface OnItemClickedListener {
        void onAvatarClicked(int position);
        void onImageClicked(View view, int position);
    }
}
