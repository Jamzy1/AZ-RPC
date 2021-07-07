package com.jiazheng.rpc.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 用于测试的api实体
 *
 * 这个对象需要实现Serializable接口，因为它需要在调用过程中从客户端传递给服务端。
 *
 * @author Jamzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloObject2  implements Serializable {

    private Integer id;
    private String message;

}
