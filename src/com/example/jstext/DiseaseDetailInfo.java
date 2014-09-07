package com.example.jstext;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class DiseaseDetailInfo {
	private int mDiseaseCode;
	private String mName;
	private int mScore;
	private List<Integer> arraylist1 = new ArrayList<>();
	private List<Integer> arraylist2 = new ArrayList<>();
	public DiseaseDetailInfo(JSONObject disease) {
		mDiseaseCode = disease.optInt("illcode");
		mName = disease.optString("name");
		mScore = disease.optInt("score");
		arraylist1 = parseArray(disease.optJSONArray("array1"));
		arraylist2 = parseArray(disease.optJSONArray("array2"));
		mScore = computScore(arraylist1,arraylist2);
	}
	private int computScore(List<Integer> list1, List<Integer> list2) {
		int total = 0;
		for(int a:list1){
			total = total + a;
			
			
			Log.i("test","Change 2");
		}
		for(int b:list2){
			total = total + b;
		} 
		return total;
	}
	private List<Integer> parseArray(JSONArray array) {
		List<Integer> list = new ArrayList<>();
		for (int i=0;i<array.length();i++)
		{
			list.add(Integer.valueOf(array.optInt(i)));
		}
		
		return list;
	}
	
	public int getDiseaseCode(){
		return this.mDiseaseCode;
	}
	public String getName(){
		return mName;
	}
	public int getTotalPoint(){
		return mScore;
	}
	public List<Integer> getFirstArray(){
		return arraylist1;
	}
	public List<Integer> getSecArray(){
		return arraylist2;
	}
}
