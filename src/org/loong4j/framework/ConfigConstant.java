package org.loong4j.framework;

/**
 * Created by Loong on 2016/1/7.
 * 提供相关配置项常量
 */
public interface  ConfigConstant {
    String CONFIG_FILE="loong.properties";

    String JDBC_DIRVER="loong.framework.jdbc.driver";
    String JDBC_URL="loong.framework.jdbc.url";
    String JDBC_USERNAME="loong.framework.jdbc.username";
    String JDBC_PASSWORD="loong.framework.jdbc.password";

    String APP_BASE_PACKAGE="loong.framework.app.base_package";
    String APP_JSP_PATH="loong.framework.app.jsp_path";
    String APP_ASSET_PATH="loong.framework.app.asset_path";
}
