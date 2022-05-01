package cn.edu.jxnu.server.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成Json格式的数据类
 *
 * @Author hanhan
 */
public class JsonResult<T> {
    private Map<String, Object> data;

    /**
     * 不返回数据默认操作成功
     */
    public JsonResult() {
        this(200, "success");
    }

    /**
     * 支持人为指定状态码与提示信息
     *
     * @param code
     * @param msg
     */
    public JsonResult(int code, String msg) {
        data = new HashMap<>();
        data.put("code", code);
        data.put("msg", msg);
    }

    /**
     * 提供了返回的数据则为成功
     *
     * @param result 新增数据
     */
    public JsonResult(T result) {
        this();
        data.put("result", result);
    }

    /**
     * 返回数据并自定义提示信息，为正确
     *
     * @param result
     * @param msg
     */
    public JsonResult(T result, String msg) {
        this(result);
        data.put("msg", msg);
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "data=" + data +
                '}';
    }

    /**
     * 在根数据下添加字段
     *
     * @param mp 新字段
     */
    public void Add(Map<String, Object> mp) {
        for (Map.Entry<String, Object> item : mp.entrySet())
            data.put(item.getKey(), item.getValue());
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
