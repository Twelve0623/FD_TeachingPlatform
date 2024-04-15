<template>
  <div class="question">
    <div class="flex items-center pointer">
      <icon-formkit-help size="16" @click="handleOpen" />
    </div>
    <el-dialog v-model="isShowSearch" width="60%" destroy-on-close draggable :modal="false" :show-close="true">
      <div class="question-content">
        <div class="card table-search">
          <el-form ref="searchFormRef" :model="searchForm" label-width="70" @submit.prevent>
            <el-row :gutter="10">
              <el-col :span="20">
                <el-form-item label="定义名称">
                  <el-input v-model="searchForm.keywords" placeholder="请输入" />
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-space>
                  <el-button type="primary" @click="handleSearch">搜索</el-button>
                </el-space>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div class="card table-main">
          <el-table :data="tableData" style="width: 100%; height: 100%" border>
            <el-table-column prop="id" label="序号" align="center" width="80" />

            <el-table-column prop="saleShop" label="模块" align="center" width="150">
              <template #default="{ row }">
                <div class="tc">{{ (row as FieldsTipsDialogType).targetClass?.ClassName }}</div>
              </template>
            </el-table-column>
            <el-table-column prop="targetName" label="定义名称" align="center" width="150" />
            <el-table-column prop="targetDescription" label="定义描述" align="left" min-width="200" />
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
// import { useRouter } from 'vue-router';
// import { useAuthStore } from '@/stores/modules/auth';
import { fetchFieldsTipsDialogDataPort, FieldsTipsDialogType } from '@/api/modules/base';
import { localSet } from '@/utils';

// 按钮权限
// const { isDisabledRead, isDisabledExport } = useButtonAuth({
//   read: 'OPERATION_FAMILIAR_CUSTOMER_ACHIEVEMENT_READ',
//   exports: 'OPERATION_FAMILIAR_CUSTOMER_ACHIEVEMENT_EXPORT',
//   initFn: () => getTableData()
// });

onMounted(() => {
  getTableData(1);
});

// 搜索表单类型
type SearchFormType = {
  keywords: string;
};
const initFormData = (): SearchFormType => ({
  keywords: ''
});
const searchForm = ref<SearchFormType>(initFormData());
const searchParams = computed<SearchFormType>(() => {
  let { keywords } = toValue(searchForm);
  return { keywords };
});

const tableData = ref<FieldsTipsDialogType[]>([]);
const handleSearch = () => {
  getTableData();
};
const getTableData = async (firstLoad = 0) => {
  try {
    let res: any = await fetchFieldsTipsDialogDataPort(searchParams.value);
    if (res.code === 200) {
      tableData.value = res.data;
      if (firstLoad && searchParams.value.keywords == '') localSet('questionMap', res.data);
    }
  } catch (e) {
    console.log('e : ', e);
  }
};

// 打开搜索框
const isShowSearch = ref(false);
const handleOpen = () => {
  isShowSearch.value = true;
};
</script>

<style scoped lang="scss">
.question {
  .question-content {
    display: flex;
    flex-flow: column nowrap;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 500px;
    .table-search {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      padding-bottom: 0 !important;
      margin-top: 30px;
    }
    .table-main {
      flex: 1;
      width: 100%;
      margin-top: 10px;
    }
  }
  .icon {
    width: 18px;
    height: 18px;
  }
  :deep(.el-dialog) {
    border-radius: 10px;
    .el-dialog__header {
      border-bottom: none;
    }
    .el-dialog__body {
      padding-top: 0;
    }
  }
}

/* 手机模式时 */
.mobile {
  .menu-search-dialog {
    :deep(.el-autocomplete) {
      width: 76%;
    }
  }
}
</style>
