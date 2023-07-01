package com.teaching.common.core;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 15:32
 **/
public class RequestContextThreadHolder {
    private RequestContextSession session;

    private static final InheritableThreadLocal<RequestContextThreadHolder> HOLDER = new InheritableThreadLocal<RequestContextThreadHolder>(){
        @Override
        protected RequestContextThreadHolder initialValue() {
            return new RequestContextThreadHolder();
        }
    };


    /**
     * 设置Session
     * @param session
     */
    public RequestContextSession setSession(RequestContextSession session){
        this.session = session;
        return session;
    }

    /**
     * 获取context
     * @return
     */
    public static RequestContextThreadHolder get(){
        return HOLDER.get();
    }

    public void clear() {
        HOLDER.remove();
    }

    public RequestContextSession getSession() {
        return session;
    }
}
