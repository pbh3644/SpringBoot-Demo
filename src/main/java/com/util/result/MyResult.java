package com.util.result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author pangbohuan
 * @description: 定义一个统一的返回格式
 * @date 2018-07-25 17:40
 **/
@Data
public class MyResult implements Serializable {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应业务状态
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static MyResult build(Integer status, String msg, Object data) {
        return new MyResult(status, msg, data);
    }

    public static MyResult ok(Object data) {
        return new MyResult(data);
    }

    public static MyResult ok() {
        return new MyResult(null);
    }

    public static MyResult errorMsg(String msg) {
        return new MyResult(ResponseStatusCode.BUSINESS_EXCEPTIONS, msg, null);
    }

    public static MyResult errorMap(String msg, Object data) {
        return new MyResult(ResponseStatusCode.BUSINESS_EXCEPTIONS, msg, data);
    }

    public static MyResult errorTokenMsg(String msg) {
        return new MyResult(ResponseStatusCode.TOKEN_EXCEPTION, msg, null);
    }

    public static MyResult errorPermissionMsg(String msg) {
        return new MyResult(ResponseStatusCode.LIMITED_AUTHORITY, msg, null);
    }

    public static MyResult errorException(String msg) {
        return new MyResult(ResponseStatusCode.SYSTEM_EXCEPTION, msg, null);
    }


    public MyResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = "N";
    }

    public MyResult(Object data) {
        this.status = ResponseStatusCode.SUCCEED;
        this.msg = "OK";
        this.data = data;
        this.code = "Y";
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: 将json结果集转化为LeeJSONResult对象
     * 需要转换的对象是一个类
     */
    public static MyResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, MyResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     * @Description: 没有object对象的转化
     */
    public static MyResult format(String json) {
        try {
            return MAPPER.readValue(json, MyResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: Object是集合转化
     * 需要转换的对象是一个list
     */
    public static MyResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
