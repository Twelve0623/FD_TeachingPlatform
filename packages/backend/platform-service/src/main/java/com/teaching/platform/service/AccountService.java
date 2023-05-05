package com.teaching.platform.service;

import com.teaching.common.core.BaseService;
import com.teaching.common.helper.BeanHelper;
import com.teaching.platform.dao.AppletsUserDao;
import com.teaching.platform.dto.response.TestResponse;
import com.teaching.platform.entity.AppletsUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/4 17:35
 **/
@Service
public class AccountService extends BaseService {
    @Resource
    private AppletsUserDao appletsUserDao;

    public List<TestResponse> demo() {

        List<AppletsUser> appletsUsers = appletsUserDao.selectList(null);

        return BeanHelper.castTo(appletsUsers,TestResponse.class);
    }
}
