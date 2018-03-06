package com.oklib.utils.network.Exception;


import java.util.List;

/**
 * 接口请求返回结果配置
 *
 * @author Damon.Han
 */
public class NetResponseConfig {

    /**
     * isFormat : false
     * sucessCode : ["0","1001"]
     * error : {"1001":"网络异常"}
     */

    private String isFormat;
    /**
     * 1001 : 网络异常
     */

    private ErrorBean error;
    private List<String> sucessCode;

    public String getIsFormat() {
        return isFormat;
    }

    public void setIsFormat(String isFormat) {
        this.isFormat = isFormat;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<String> getSucessCode() {
        return sucessCode;
    }

    public void setSucessCode(List<String> sucessCode) {
        this.sucessCode = sucessCode;
    }

    public static class ErrorBean {
        private String value1001;

        public String getValue1001() {
            return value1001;
        }

        public void setValue1001(String value1001) {
            this.value1001 = value1001;
        }
    }
}
