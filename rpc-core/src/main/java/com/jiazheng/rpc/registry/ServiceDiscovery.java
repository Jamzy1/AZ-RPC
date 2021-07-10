package com.jiazheng.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 * @author Jamzy
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}
