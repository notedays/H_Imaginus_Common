package com.himaginus.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData extends ResponseData{
	public String name = "Test";
	public int intValue = 12;
	public long longValue = 12323l;
	public double doubleValue = 1.232d;
	public boolean flag = true;
	public List<CityData> cityList = new ArrayList<CityData>();
	public Map<Integer, CityData> cityMap = new HashMap<>();
}
