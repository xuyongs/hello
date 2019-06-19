package com.agent.czb.common.code;


/**
 * 编码/解码器
 */
public interface Coder {

    /**
     * 编码
     *
     * @param data 原始数据
     * @param key  加密密钥
     * @return 编码后的数据
     */
    String encode(String data, String key);

    /**
     * 解码
     *
     * @param data 编码数据
     * @param key  解密密钥
     * @return 解码后的数据
     */
    String decode(String data, String key);

    /**
     * 生成key
     *
     * @return key
     */
    String createKey();
}
