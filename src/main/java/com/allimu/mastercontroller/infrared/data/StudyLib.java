package com.allimu.mastercontroller.infrared.data;

import java.util.HashMap;


public class StudyLib {
	private HashMap<String, byte[]> mLib = new HashMap<String, byte[]>();
	public void Add(String _token, byte[] _data)
	{
		mLib.put(_token, _data);
	}
	public void Del(String _token)
	{
		mLib.remove(_token);
	}
	public void Edit(String _token, byte[] _data)
	{
		Del(_token);
		Add(_token, _data);
	}
	public byte[] Get(String _token)
	{
		return mLib.get(_token);
	}
	public void Save() 
	{
		
	}
}
