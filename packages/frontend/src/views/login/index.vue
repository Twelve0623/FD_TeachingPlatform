<template>
  <div class="login-container flx-center">
    <div class="login-box">
      <div class="login-left">
        <img class="login-left-img" src="@/assets/images/login_left.png" alt="login" />
      </div>
      <div class="login-form">
        <div class="login-logo">
          <img class="login-icon" src="@/assets/images/favicon.ico" alt="" />
          <h2 class="logo-text1">复旦大学继续教育学院</h2>
          <h2 class="logo-text2">智慧云课堂平台</h2>
        </div>
        <div class="form-wrap">
          <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="logo-ruleForm">
            <el-form-item prop="studentNo">
              <el-input v-model.number="ruleForm.studentNo" placeholder="学号" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="密码" v-model="ruleForm.password" type="password" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="code">
              <div class="form-code-row-wrap">
                <el-input placeholder="验证码" v-model="ruleForm.code" autocomplete="off" style="width: 50%" />
                <img class="form-code-wrap" :src="codeBase64" @click="handleGetCode" alt="" />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button class="submit" round @click="submit" size="large" type="primary" :loading="loading"> 登录 </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="login">
import { reactive, ref } from 'vue';
import type { FormRules } from 'element-plus';
import { loginApi, getCodeImg } from '@/api/modules/login';
import { useUserStore } from '@/stores/modules/user';
import { useKeepAliveStore } from '@/stores/modules/keepAlive';
import { HOME_URL } from '@/config';
import { ElNotification } from 'element-plus';
import { getTimeState } from '@/utils';
import JSEncrypt from 'jsencrypt';
const userStore = useUserStore();
const keepAliveStore = useKeepAliveStore();
const router = useRouter();
const ruleFormRef = ref();
const loading = ref(false);
const ruleForm = reactive({
  studentNo: '',
  password: '',
  code: ''
});
const codeBase64 = ref('');
const uuid = ref('');

const rules = reactive<FormRules<typeof ruleForm>>({
  studentNo: [
    {
      required: true,
      message: '请输入学号',
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'change'
    }
  ],
  code: [
    {
      required: true,
      message: '请输入验证码',
      trigger: 'change'
    }
  ]
});

const submit = () => {
  // 表单验证
  ruleFormRef.value?.validate(async (valid: any) => {
    if (valid) {
      loading.value = true;
      try {
        const key =
          'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDg3zTezT6W7PovTxENrXGlO4y5+rM5PfRnLGPIc8Tqlx+KQyyjr3xMktoZBmDvmlrlRKYdRAScjx+yHcRRmPjeA5AnmugT/uNIft7iX+gq2g7Ey0qPf6Jy8c85H12FkgXq8vFXqGxMKU4owChqu/7xqAtBEwFX7s2f3WzorOGyvwIDAQAB';
        const encrypt = new JSEncrypt();
        encrypt.setPublicKey(key);
        const param = {
          studentNo: ruleForm.studentNo,
          // password: encrypt.encrypt(ruleForm.password),
          password: ruleForm.password,
          code: ruleForm.code,
          uuid: uuid.value
        };
        // 执行登录接口
        const { code, msg, data } = await loginApi(param);
        if (code == '0') {
          console.log(data, 'res------------------------');
          userStore.setToken(data.token);
          keepAliveStore.setKeepAliveName();
          loading.value = false;
          // 跳转到首页
          router.push(HOME_URL);
          ElNotification({
            title: getTimeState(),
            message: '欢迎登录智慧云课堂平台',
            type: 'success',
            duration: 3000
          });
        } else {
          throw { msg };
        }
      } catch (error: any) {
        console.log('error submit!', error);
        loading.value = false;
        handleGetCode();
      }
    } else {
      console.log('error submit!');
      return false;
    }
  });
};

async function handleGetCode() {
  try {
    let { code, msg, data } = await getCodeImg();
    if (code == '0') {
      codeBase64.value = data.img;
      uuid.value = data.uuid;
    } else {
      throw { msg };
    }
  } catch (error: any) {
    console.log(error);
  }
}
onMounted(async () => {
  handleGetCode();
});
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
