package org.loong4j.framework.bean;

/**
 *
 * 返回数据对象
 *
 * Created by Loong on 2016/5/25.
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model){
        this.model=model;
    }

    public Object getModel(){
        return model;
    }
}
