package com.john.robot;

import java.util.List;

import com.example.robot.R;
import com.john.robot.bean.ListData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {

	private List<ListData> lists;
	private Context mContext;
	
	private RelativeLayout layout;
	
	public TextAdapter(List<ListData> lists,Context mContext){
		this.lists = lists;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		
		//加载别人发送消息的界面
		if(lists.get(position).getFlag() == ListData.RECEIVER){
			layout = (RelativeLayout) inflater.inflate(R.layout.left_item, null);
		}
		//加载自己发送消息的界面
		if(lists.get(position).getFlag() == ListData.SEND){
			layout = (RelativeLayout) inflater.inflate(R.layout.right_item, null);
		}
		//加载发送的内容
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		
		//发送时间
		TextView time = (TextView) layout.findViewById(R.id.time);
		
		//显示时间
		time.setText(lists.get(position).getTime());
		
		//显示发送或接收内容
		tv.setText(lists.get(position).getContent());
		
		return layout;
	}

}
