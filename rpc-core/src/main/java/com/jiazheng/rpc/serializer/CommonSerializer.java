package com.jiazheng.rpc.serializer;


/**
 * 通用的序列化反序列化接口
 * @author Jamzy
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);


    //获得该序列化器的编号
    int getCode();

    //根据编号获取序列化器
    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }
}
