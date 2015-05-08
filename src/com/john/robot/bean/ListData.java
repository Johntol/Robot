package com.john.robot.bean;

public class ListData {
	
	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	
	//��ȡ������
	private String content;
	
	//��־λ��ʾ���ͻ��ǽӽ���
	private int flag;
	
	private String time;
	
	
	public ListData(String content,int flag,String time){
		setContent(content);
		setFlag(flag);
		setTime(time);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setFlag(int flag){
		this.flag = flag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}

