package org.loong4j.framework.proxy;

/**
 * 代理接口
 * Created by Loong on 2016/7/7.
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain)throws Throwable;
}
