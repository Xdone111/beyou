package byou.yadun.wallet.utils;

import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @param <T>
 */
public class JsonUtil<T> {
	
	public static String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String json=o.toString();
		try {
			json=mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static Object read2Object(String json, Class<?>  c){
		ObjectMapper mapper = new ObjectMapper();
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性  
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			return mapper.readValue(json, c);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<LinkedHashMap<String, Object>> read2List(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<LinkedHashMap<String, Object>> list = mapper.readValue(json,
					List.class);
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object[] read2Array(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, Object[].class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, Object>> read2Map(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Map<String, Object>> maps = mapper.readValue(json,
					Map.class);
			return maps;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static Map<String, Object> read2Maps(String json) {
		if (TextUtils.isEmpty(json))
			return null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> maps = mapper.readValue(json, Map.class);
			return maps;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, List<LinkedHashMap<String, Object>>> read2MapList(
			String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, List<LinkedHashMap<String, Object>>> maps = mapper
					.readValue(json, Map.class);
			return maps;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
