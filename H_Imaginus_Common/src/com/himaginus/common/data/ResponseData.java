package com.himaginus.common.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResponseData implements Externalizable {
	
	// # write 관련 함수 ==================================================================================
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		for (Field field : getAllFields(this.getClass())) {
			field.setAccessible(true);
			if( checkIsTransField(field) ) continue;
			
			try {
				Class<?> fieldClass = field.getType();
				if( fieldClass.isAssignableFrom(List.class) ){
					List<?> list = (List<?>) field.get(this);
					writeData(int.class, list.size(), out);
					for(int i=0; i< list.size(); i++){
						Object obj = list.get(i);
						writeData(obj.getClass(), obj, out);
					}
				}else if( fieldClass.isAssignableFrom(Map.class) ){
					Map<?,?> map = (Map<?, ?>) field.get(this);
					writeData(int.class, map.size(), out);
					Iterator<?> iter = map.entrySet().iterator();
					while(iter.hasNext()){
						Entry<?,?> entry = (Entry<?, ?>) iter.next();
						Object key = entry.getKey();
						Object value = entry.getValue();
						writeData(key.getClass(), key, out);
						writeData(value.getClass(), value, out);
					}
				}else{
					writeData(field.getType(), field.get(this), out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.flush();
	}
	
	private void writeData(Class<?> objClass, Object obj, ObjectOutput out){
		try {
			if( objClass.isAssignableFrom( String.class) ){
				out.writeUTF((String) obj);;
			}else if( objClass.isAssignableFrom(int.class ) || objClass.isAssignableFrom(Integer.class) ){
				out.writeInt((int) obj);
			}else if( objClass.isAssignableFrom(long.class) || objClass.isAssignableFrom(Long.class) ){
				out.writeLong((long) obj);
			}else if( objClass.isAssignableFrom(double.class) || objClass.isAssignableFrom(Double.class) ){
				out.writeDouble((double) obj);
			}else if( objClass.isAssignableFrom(boolean.class) || objClass.isAssignableFrom(Boolean.class) ){
				out.writeBoolean((boolean) obj);
			}else if( ResponseData.class.isAssignableFrom(objClass) ){
				((ResponseData)obj).writeExternal(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// # read 관련 함수 ====================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("available : "+in.available());
		for (Field field : getAllFields(this.getClass())) {
			try {
				field.setAccessible(true);
				if( checkIsTransField(field) ) continue;
				
				Class<?> fieldClass = field.getType();
				if( fieldClass.isAssignableFrom(List.class) ){
					int size = in.readInt();
					List<Object> list = (List<Object>) field.get(this);
					Type[] types = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
					for (int i = 0; i < size; i++) {
						list.add(readData(Class.forName(types[0].getTypeName()), in));
					}
				}else if( fieldClass.isAssignableFrom(Map.class)){
					int size = in.readInt();
					Map<Object, Object> map = (Map<Object, Object>) field.get(this);
					Type[] types = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
					
					for (int i = 0; i < size; i++) {
						Object key = readData(Class.forName(types[0].getTypeName()), in);
						Object value = readData(Class.forName(types[1].getTypeName()), in);
						map.put(key, value);
					}
				}else{
					field.set(this, readData(fieldClass, in));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Object readData(Class<?> fieldClass, ObjectInput in){
		try {
			if( fieldClass.isAssignableFrom(String.class) ){
				return in.readUTF();
			}else if( fieldClass.isAssignableFrom(int.class ) || fieldClass.isAssignableFrom(Integer.class) ){
				return in.readInt();
			}else if( fieldClass.isAssignableFrom(long.class) || fieldClass.isAssignableFrom(Long.class) ){
				return in.readLong();
			}else if( fieldClass.isAssignableFrom(double.class) || fieldClass.isAssignableFrom(Double.class) ){
				return in.readDouble();
			}else if( fieldClass.isAssignableFrom(boolean.class) || fieldClass.isAssignableFrom(Boolean.class) ){
				return in.readBoolean();
			}else if( ResponseData.class.isAssignableFrom(fieldClass) ){
				ResponseData data = (ResponseData) fieldClass.newInstance();
				data.readExternal(in);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// # 모두 쓰는 함수 ======================================================================================
	
	
	public boolean checkIsTransField(Field field) {
		return field.getName().equals("serialVersionUID") || Modifier.isTransient(field.getModifiers());
	}

	// # 해당 클래스의 모든 필드를 구하는 함수
	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		if (clazz.getName().equals("java.lang.Object"))
			return list;

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length - 1; i++) {
			for (int j = i + 1; j < fields.length; j++) {
				byte[] bytesI = fields[i].getName().getBytes();
				byte[] bytesJ = fields[j].getName().getBytes();

				int length = bytesI.length < bytesJ.length ? bytesI.length : bytesJ.length;
				boolean isOrdered = false;
				for (int b = 0; b < length; b++) {
					byte byteI = bytesI[b];
					byte byteJ = bytesJ[b];
					if (byteI > byteJ) {
						Field tempField = fields[j];
						fields[j] = fields[i];
						fields[i] = tempField;
						isOrdered = true;
						break;
					} else if (byteI < byteJ) {
						isOrdered = true;
						break;
					}
				}
				if (!isOrdered) {
					if (bytesI.length > bytesJ.length) {
						Field tempField = fields[j];
						fields[j] = fields[i];
						fields[i] = tempField;
					}
				}
			}
		}
		list.addAll(Arrays.asList(fields));

		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			list.addAll(getAllFields(superClazz));
		}
		return list;
	}
}
