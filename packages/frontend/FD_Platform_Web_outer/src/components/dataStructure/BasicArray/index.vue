<template>
  <div class="ArrayMainSection">
    <el-card class="OperationBar">
      <el-steps :active="active" align-center>
        <el-step v-for="pl in configs.process.processList" :title="pl.stepNameZH" :description="pl.description" />
      </el-steps>
      <div>
        <el-button class="ButtonList" :active="active" style="margin-top: 12px" @click="last">Last step</el-button>
        <el-button class="ButtonList" :active="active" style="margin-top: 12px" @click="next">Next step</el-button>
        <el-button class="ButtonList" :active="active" style="margin-top: 12px"
          @click="drawer.show = true">操作步骤</el-button>
      </div>
    </el-card>
    <el-card class="OperationBar" v-show="active == 0">
      <inLineCodeBlock :inLineCode="basic"></inLineCodeBlock>
    </el-card>

    <el-card class="OperationBar" v-show="active == 1">
      <el-button style="margin-top: 12px" @click="fillerValue('Ran')">填充随机数</el-button>
      <el-button style="margin-top: 12px" @click="fillerValue('Ind')">填充同下标</el-button>
    </el-card>

    <el-card class="mainCard" v-show="active == 1">
      <!-- <div>{{ createArray.massage }}</div> -->
      <div class="">
        <div class="initSelection">
          <span> List </span>
          <el-input-number :max="createArray.maxInd" :min="createArray.minInd"
            @keyup.enter.native="initList(createArray.listSize)" @click="initList(createArray.listSize)"
            v-model="createArray.listSize" />
          <span> = </span>
        </div>
        <div class="ArrayBlock">
          <div class="displayInLine"> [ </div>
          <div class=" displayInLine CreatedArray" v-for="(value, index) in createArray.currentList">
            <div class="ArrayItem">
              <el-input class="ArrayInput" size="large" v-model="createArray.currentList[index]" width="5px" />
              <span class="ArrayItem" v-if="index < createArray.listSize - 1">,</span>
            </div>
            <div class="ArrayIndex"> {{ index }} </div>
          </div>
          <div class="displayInLine"> ] </div>
        </div>

        <!-- <p> {{ createArray.currentList }} </p> -->
      </div>
    </el-card>

    <el-card class="OperationBar" v-show="active == 2">

      <span> 下标编号 </span>
      <el-input-number :max="createArray.listSize - 1" :min="0" v-model="createArray.seletedInd" />
      <el-button class="ArrayOperationButton" type="primary" v-for="ele in createArray.OpertionButton" :icon="ele.icon">
        {{ ele.event }} </el-button>
      <span> 需要搜索的值 </span>
      <el-input-number :max="99" :min="0" v-model="createArray.searchValue" />
      <el-button class="ArrayOperationButton" type="primary" :icon="Search">
        Search </el-button>
    </el-card>

    <el-card class="mainCard" v-show="active == 2">
      <!-- <div>{{ createArray.massage }}</div> -->
      <div class="">
        <div class="initSelection">
          <span> List </span>
          [ {{ createArray.listSize }} ]
          <span> = </span>
        </div>
        <div class="ArrayBlock">
          <div class="displayInLine"> [ </div>
          <div class=" displayInLine CreatedArray" v-for="(value, index) in createArray.currentList">
            <div class="ArrayItem"
              :style="{ borderRadius: '8px', border: index == createArray.seletedInd ? '2px solid gray' : '' }">
              <span> {{ value }} </span>
              <span class="ArrayOperationButton ArrayIndex boards" :style="{
                borderRadius: '8px',
                border: index != createArray.seletedInd ? '2px solid gray' : '',
                background: index == createArray.seletedInd ? 'gray' : ''
              }"> {{ index }} </span>
              <span class="ArrayItem" v-if="index < createArray.listSize - 1">,</span>
            </div>
            <el-row>
              <el-button class="ArrayOperationButton" type="primary" v-for="ele in createArray.OpertionButton"
                :icon="ele.icon" circle />
            </el-row>
          </div>
          <div class="displayInLine"> ] </div>
        </div>

        <!-- <p> {{ createArray.currentList }} </p> -->
      </div>

    </el-card>

    <el-card class="mainCard" v-show="active == 3"></el-card>
  </div>
  <el-drawer class="mainCard" v-model="drawer.show" title="I am the title" direction="rtl" :with-header="false">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick"  >
      <el-tab-pane :label=" codes.LanName " :name="codes.LanName" v-for="codes in drawer.expCode">
          <inLineCodeBlock :inLineCode="codes.code"></inLineCodeBlock>
        </el-tab-pane>
    </el-tabs>
  </el-drawer>
</template>

<script lang="ts">
import { ref, onMounted, defineComponent } from "vue";
import inLineCodeBlock from './CodeDisplayer.vue';
import { ElMessageBox, TabsPaneContext, } from 'element-plus'
import { Delete, Plus, Edit, Search, } from '@element-plus/icons-vue'

import commonJs from "@/assets/js/commonJs";
import { useStore } from "vuex";
import { randomInt } from "crypto";

export default defineComponent({
  name: 'DataStructure-List',
  components: { Delete, Plus, Edit, Search, inLineCodeBlock },
  data() {
    return {
      configs: {
        process: {
          processList: [
            { stepNameZH: '基本概念', description: '线性表基本概念', },
            { stepNameZH: '初始化列表', description: '指定线性表长度', },
            { stepNameZH: '线性表操作', description: '对线性表进行增删改查操作', },
            { stepNameZH: '自己尝试以下', description: '通过示例内容自己尝试实现', },
          ]
        }
      },
      basic: [
        '线性表是具有相同数据类型的n个数据元素的有限序列',
        '顺序表即线性表的顺序存储结构。它是通过一组地址连续的存储单元对线性表中的数据进行存储的，相邻的两个元素在物理位置上也是相邻的。比如，第1个元素是存储在线性表的起始位置LOC(1)，那么第i个元素即是存储在LOC(1)+(i-1)*sizeof(ElemType)位置上，其中sizeof(ElemType)表示每一个元素所占的空间。'
      ],
      createArray: {
        listSize: 5,
        maxInd: 20,
        minInd: 1,
        seletedInd: 0,
        searchValue: 0,
        currentList: Array(5),
        removedElement: [],
        OpertionButton: [
          { event: 'Add', icon: Plus },
          { event: 'Delete', icon: Delete },
          { event: 'Edit', icon: Edit },
          // { event: 'Search', icon: Search },
        ],
        massage: ''
      },
      drawer: {
        show: false,
        direction: 'test test',
        expCode: [
          {
            LanName: 'Python',
            code: [
              'testtest',
              'test',
              'testtest',
              'testtest',
              'testtest',
              'testtest',
              'testtest',
              'testtest',
              'testtest',
              'testtest',
            ]
          },
          {
            LanName: 'JAVA',
            code: [
              'list = []',
            ]
          },
          {
            LanName: 'C/C++',
            code: [
              'list = []',
            ]
          },
          {
            LanName: 'test',
            code: [
              'list = []',
            ]
          }
        ]
      }
    }
  },
  methods: {
    initList(listSize) {
      if (listSize <= this.createArray.maxInd && listSize >= this.createArray.minInd) {
        listSize = parseInt(listSize)
        this.createArray.currentList = new Array(listSize)
        this.createArray.massage = ''
      } else {
        this.createArray.massage = '输入一个整数且在 1～20之间'
      }
      console.log(this.createArray.currentList)
    },

    fillerValue(setting) {
      // console.log('>>>>>>')
      let tmpArr = [0];
      let keys;
      for (keys = 0; keys < this.createArray.listSize; keys++) {

        if (setting == 'Ran') {
          if (keys == 0) {
            tmpArr = [Math.round(Math.random() * 99)];
          } else {
            tmpArr.push(Math.round(Math.random() * 99))
          }
        } else if (setting == 'Ind') {
          if (keys == 0) {
            tmpArr = [keys];
          } else {
            tmpArr.push(keys)
          }
        }
      }

      this.createArray.currentList = tmpArr
    },

    SetSearchValue() {
      const len = this.createArray.currentList.length
      this.createArray.searchValue = this.createArray.currentList[len - 1]
    }
  },
  setup() {
    const active = ref(0);
    const activeName = ref('JAVA');

    const next = () => {
      if (active.value++ > 2) active.value = 3
    }

    const last = () => {
      if (active.value-- < 1) active.value = 0
    }
    const handleClick = (tab: TabsPaneContext, event: Event) => {
      console.log(tab, event)
    }

    return { active, next, last, handleClick, activeName, Edit, Search, Delete, Plus }
  }

})

</script>
<style scoped lang="css">
.ArrayMainSection {
  position: relative;
  align-items: center;
  margin: auto;
  align-content: center;
}

.ButtonList {
  position: relative;
  display: inline-flex;
  align-content: center;
}

.mainCard {
  margin: .5vw;
  padding: 2vw;

}

.OperationBar {
  margin: .5vw;

}

.initSelection {
  float: left;
  margin: auto;
  padding-top: 1.8vh;
}

.ArrayOperationButton {
  margin: .1vw;
  display: inline-block;
  align-content: center;
}

.displayInLine {
  float: left;
  font-size: 5vh;
}

.initSelection {
  display: inline-block;
  width: 15vw;
}

.CreatedArray {
  box-sizing: border-box;
  display: inline-block;
  width: 9vw;
}


.ArrayItem {
  display: inline-block;
  font-size: 4vh;
}


.ArrayInput {
  display: inline-block;
  font-size: 1.5vh;
  width: 75%;
  align-items: center;
  text-align: center;
  align-self: center;
}

.ArrayIndex {
  font-size: 2vh;
  text-align: center;
}

.boards {
  width: 1.5vw;
  height: 1.2vw;
  text-align: center;
  border-radius: 8px;
}

.ArrayBlock {
  display: inline-block;
  font-size: 2vh;
  text-align: center;
  width: 60vw;
}


.codeBlock{
  background-color: gray;
}

.codeDisplayerIDs{
  text-align: right;
  width: 1%;
  float: left;
}


.CodeContext {
  text-align: left;
  background-color: gray;
  float: left;
  width: 98%;
  border-left: 2px solid black;
  margin-left: .5%;
}
</style>
