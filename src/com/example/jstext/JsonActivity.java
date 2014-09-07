package com.example.jstext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class JsonActivity extends Activity {

	private static final String TAG = "JsonActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		Log.d(TAG, "JsonTest onCreate");
		AssetManager  assetManager = getAssets();
		InputStream is = null;
		try {
			is = assetManager.open("real_jsondata_demo.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String s = read(is);
//		Log.i(TAG, "JsonTest! s=" + s);
		List<DiseaseDetailInfo> list = parseJson(s);
		DiseaseDetailInfo a = list.get(0);
		Log.i(TAG,"jsonsize (list1.length)"+a.getFirstArray().size()+"(list2.length)"+a.getSecArray().size());
	}

	private String read(InputStream is) {
		ByteArrayOutputStream opS = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len = -1;
		try {

			while ((len = is.read(buf)) != -1) {
				opS.write(buf, 0, len);
			}
			opS.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(opS.toByteArray());

	}

	private String readDeviceInfoFromFile(File f) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i = fis.read()) != -1) {
				baos.write(i);
			}
			return new String(baos.toByteArray());
		} catch (FileNotFoundException e) {
			Log.d(TAG, "File:" + f.getName() + " not found");
		} catch (IOException e) {
			Log.d(TAG, "IOException:"
					+ (TextUtils.isEmpty(e.getMessage()) ? "unknow exception"
							: e.getMessage()));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					Log.d(TAG,
							TextUtils.isEmpty(e.getMessage()) ? "unknow exception"
									: e.getMessage());
				}
			}
		}

		return "";
	}

	private List<DiseaseDetailInfo> parseJson(String s) {

		try {
			JSONObject object = new JSONObject(s);
			JSONArray allarray = object.optJSONArray("all");
			
			List<DiseaseDetailInfo> list = new ArrayList<DiseaseDetailInfo>();
			for (int i = 0;i<allarray.length();i++){
				JSONObject disease = allarray.getJSONObject(i);
				DiseaseDetailInfo oneDisease = new DiseaseDetailInfo(disease);
				list.add(oneDisease);
				Log.i(TAG,"(name)"+oneDisease.getName()+"(code)"+oneDisease.getDiseaseCode()+"(total)"+oneDisease.getTotalPoint()+"(array)"+oneDisease.getFirstArray());
				Log.i(TAG,"jsonsize (list1.length)"+oneDisease.getFirstArray().size()+"(list2.length)"+oneDisease.getSecArray().size());
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
/*
	private List<MoreGameListInfo> parseMoreGameListData(String httpReturn) {

		PBLog.i(httpReturn);

		if (TextUtils.isEmpty(httpReturn)) {
			return null;
		}

		try {

			JSONObject object = new JSONObject(httpReturn);
			boolean result = object.optBoolean("result");

			mError = 0;

			if (!result) {
				mError = object.optInt("err");
				return null;
			}

			JSONArray adList = object.optJSONArray("datas");
			if (adList == null) {
				return null;
			}

			List<MoreGameListInfo> list = new ArrayList<MoreGameListInfo>();

			for (int i = 0; i < adList.length(); i++) {
				JSONObject ad = adList.getJSONObject(i);
				if (ad == null) {
					continue;
				}
				MoreGameListInfo mMoreGameListInfo = new MoreGameListInfo(ad);
				list.add(mMoreGameListInfo);
			}

			if (mCurrentPageNum == 1) {
				mFirstPageCount = list.size();
			}

			return list;
		} catch (JSONException e) {
			PBLog.d(TAG, "List construct failed");
		}

		return null;
	}
	*/
}
