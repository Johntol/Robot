package com.john.robot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.robot.R;
import com.john.robot.bean.ListData;
import com.john.robot.util.HttpData;
import com.john.robot.util.HttpGetDataListener;

public class MainActivity extends Activity implements HttpGetDataListener,OnClickListener {
		
		private HttpData httpData;
		
		private List<ListData> lists;
		
		private ListView lv;
		private EditText sendtext;
		private Button send_btn;
		
		//���͵�����
		private String content_str;
		
		private TextAdapter adapter;
		//��ӭ��
		private String [] welcome_array;
		
		private double currentTime,oldTime = 0;
	
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			initView();
		}
		
		/**
		 * ��ʼ������
		 */
		private void initView(){
			lv = (ListView) findViewById(R.id.lv);
			sendtext = (EditText) findViewById(R.id.sendText);
			send_btn = (Button) findViewById(R.id.send_btn);
			lists = new ArrayList<ListData>();
			send_btn.setOnClickListener(this);
			adapter = new TextAdapter(lists, this);
			lv.setAdapter(adapter);
			ListData listData;
			//��ʼ����ӭ��
			listData = new ListData(getRandomWelcomeWord(), ListData.RECEIVER,getTime());
			lists.add(listData);
		}

		public void getDataUrl(String data) {			
			parseText(data);
		}
		
		/**
		 * ��ȡ���˷��͹�������Ϣ
		 * @param str
		 */
		public void parseText(String str){
			try {
				JSONObject jb = new JSONObject(str);
				//��װ����
				ListData listData;
				listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
				lists.add(listData);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		/**
		 * ������Ͱ�ť������Ϣ
		 */
		public void onClick(View v) {
			//��ȡ����ʱ��
			getTime();
			content_str = sendtext.getText().toString();
			sendtext.setText("");
			//�滻�ո�
			String dropk = content_str.replace(" ", "");
			//�滻�س�
			String droph = dropk.replace("\n", "");
			ListData listData;
			listData = new ListData(content_str,ListData.SEND,getTime());
			//�ѷ��͵����ݷŵ�������,���뵽��Ϣ��
			lists.add(listData);
			//���ݹ��࣬�Ƴ����ݣ���Ϣ��
			if(lists.size()>30){
				for(int i = 0; i < lists.size(); i++){
					lists.remove(i);
				}
			}
			
			
			//ˢ������
			adapter.notifyDataSetChanged(); 
			//����AsyncTask(HttpData)
			httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=6b542db6b00150e198e0f636babf9864&info="+droph, this)
			.execute();
			
		}
		
		/**
		 * ��ȡ��ǰʱ��
		 */
		private String getTime(){
			currentTime = System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			Date curDate = new Date();
			String str = format.format(curDate);
			//����60����ʾʱ��
			if(currentTime - oldTime >= 1*1000){
				oldTime = currentTime;
				return str;
			}else{
			return "";
			}
		}
		
		/**
		 * ��ȡ�����ӭ��
		 * @return
		 */
		private String getRandomWelcomeWord(){
			String welcome_word = null;
			welcome_array = this.getResources().getStringArray(R.array.welcome_word);
			int index = (int) (Math.random()*(welcome_array.length-1));
			welcome_word = welcome_array[index];
			return welcome_word;
		}
		
		
		
}
