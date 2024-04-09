package com.teaching.platform.kern;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 23:01
 **/
public interface IConstant {
    final class Redis{
        public static final int EXPIRE = 30 * 24 * 60 * 60 ;

        public static final String REDIS_STUDENT_LOGIN_TOKEN = "teaching-student-token:";

        public static final String REDIS_LOGIN_CODE = "teaching-student-loginCode:";
        public static final String REDIS_COURSE_USER_COUNT = "teaching-student-courseUser:";

    }
    enum UserDelFlag {
        NO_DEL(0, "未删除"), DEL(1, "已删除");

        private final Integer code;
        private final String info;

        UserDelFlag(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo()
        {
            return info;
        }
    }
    enum UserStatus {
        OK(0, "正常"), DISABLE(1, "停用");

        private final Integer code;
        private final String info;

        UserStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode()
        {
            return code;
        }

        public String getInfo()
        {
            return info;
        }
    }
    enum CourseEnum {
        COURSE_STATUS_OPEN(0, "开放课程"),
        COURSE_STATUS_NO_OPEN(1, "未开放课程"),
        COURSE_TYPE_CREATED(0, "自建课程"),
        COURSE_TYPE_JUMP(1, "外链"),

        ;

        private final Integer code;
        private final String info;

        CourseEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo()
        {
            return info;
        }
    }
}
