package com.yubi.app.jsonparser.v1;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class JParser {
	Object json_tokener;
	boolean isValidated = false;
	String json_string;
	String ErrorFieldNotFound = "Fieldname not exist";
	int fieldcount;
	String separator = "-";

	public JParser(String jsonstr) throws JSONException {
		this.json_string = jsonstr;
		this.json_tokener = (JSONObject) new JSONTokener(jsonstr).nextValue();
		this.isValidated = (this.json_tokener != null);
	}

	public String getValue(String keys) {
		String result = "";
		String[] keys_list = keys.split(this.separator);
		
		this.fieldcount = keys_list.length;
		
		try{
		JSONObject json_object = new JSONObject(this.json_string);

		if (keys_list.length == 1) {
			result = json_object.getString(keys_list[0]);
		} else {
			for (int i = 0; i < keys_list.length; i++) {
				String fieldname = keys_list[i];

				if (json_object.has(fieldname)) {
					if (i != (this.fieldcount - 1)) {
						json_object = json_object.getJSONObject(fieldname);
					} else {
						result = json_object.getString(keys_list[i]);
					}
				} else {
					result = ErrorFieldNotFound;
				}
			}
		}

		} catch(JSONException je){
			Log.e("Jparse", "'"+je.getMessage()+"'");
		}
		return result;
	}
	
	public JSONObject getJSONObject(String keys) {
		JSONObject result = null;
		String[] keys_list = keys.split(this.separator);
		
		this.fieldcount = keys_list.length;
		
		try{
		JSONObject json_object = new JSONObject(this.json_string);

		if (keys_list.length == 1) {
			if (json_object.has(keys_list[0])) {
				result = json_object.getJSONObject(keys_list[0]);
			}
		} else {
			for (int i = 0; i < keys_list.length; i++) {
				String fieldname = keys_list[i];

				if (json_object.has(fieldname)) {
					if (i != (this.fieldcount - 1)) {
						json_object = json_object.getJSONObject(fieldname);
					} else {
						result = json_object.getJSONObject(keys_list[i]);
					}
				}
			}
		}

		} catch(JSONException je){
			Log.e("Jparse", "'"+je.getMessage()+"'");
		}
		return result;
	}
	
	public String getString(String keys){
		return getValue(keys);
	}
	
	public int getInt(String keys){
		String result = getValue(keys);
		int iresult = 0;
		try {
			iresult = Integer.parseInt(result);
		} catch(Exception e){
			
		}
		return iresult;
	}
}
