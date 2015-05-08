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
		
		//发送的内容
		private String content_str;
		
		private TextAdapter adapter;
		//欢迎语
		private String [] welcome_array;
		
		private double currentTime,oldTime = 0;
	
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			initView();
		}
		
		/**
		 * 初始化界面
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
			//初始化欢迎语
			listData = new ListData(getRandomWelcomeWord(), ListData.RECEIVER,getTime());
			lists.add(listData);
		}

		public void getDataUrl(String data) {			
			parseText(data);
		}
		
		/**
		 * 获取别人发送过来的消息
		 * @param str
		 */
		public void parseText(String str){
			try {
				JSONObject jb = new JSONObject(str);
				//封装数据
				ListData listData;
				listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
				lists.add(listData);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		/**
		 * 点击发送按钮发送消息
		 */
		public void onClick(View v) {
			//获取发送时间
			getTime();
			content_str = sendtext.getText().toString();
			sendtext.setText("");
			//替换空格
			String dropk = content_str.replace(" ", "");
			//替换回车
			String droph = dropk.replace("\n", "");
			ListData listData;
			listData = new ListData(content_str,ListData.SEND,getTime());
			//把发送的内容放到集合中,加入到消息栏
			lists.add(listData);
			//数据过多，移除数据（消息）
			if(lists.size()>30){
				for(int i = 0; i < lists.size(); i++){
					lists.remove(i);
				}
			}
			
			
			//刷新数据
			adapter.notifyDataSetChanged(); 
			//触发AsyncTask(HttpData)
			httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=6b542db6b00150e198e0f636babf9864&info="+droph, this)
			.execute();
			
		}
		
		/**
		 * 获取当前时间
		 */
		private String getTime(){
			currentTime = System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			Date curDate = new Date();
			String str = format.format(curDate);
			//超过60秒显示时间
			if(currentTime - oldTime >= 1*1000){
				oldTime = currentTime;
				return str;
			}else{
			return "";
			}
		}
		
		/**
		 * 获取随机欢迎语
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
