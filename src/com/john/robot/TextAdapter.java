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
		
		//���ر��˷�����Ϣ�Ľ���
		if(lists.get(position).getFlag() == ListData.RECEIVER){
			layout = (RelativeLayout) inflater.inflate(R.layout.left_item, null);
		}
		//�����Լ�������Ϣ�Ľ���
		if(lists.get(position).getFlag() == ListData.SEND){
			layout = (RelativeLayout) inflater.inflate(R.layout.right_item, null);
		}
		//���ط��͵�����
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		
		//����ʱ��
		TextView time = (TextView) layout.findViewById(R.id.time);
		
		//��ʾʱ��
		time.setText(lists.get(position).getTime());
		
		//��ʾ���ͻ��������
		tv.setText(lists.get(position).getContent());
		
		return layout;
	}

}
