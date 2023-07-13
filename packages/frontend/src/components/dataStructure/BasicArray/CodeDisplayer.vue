<template>
    <div class="codeBlock" v-for="(texts, index) in props.inLineCode">
        <span class="codeDisplayerIDs"> {{ index + 1 }} </span> <span class="CodeContext"> {{ texts }} </span>
    </div>
</template>
  
<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: 'DataStructure-List',
    props: {
        inLineCode: {
            type:Array,
            default:[
                '',
            'list = []',
            ]
        }
    },
    setup(props){
        return {props}
    }
})

</script>
<style scoped lang="css">
.codeBlock {
    background-color: gray;
}

.codeDisplayerIDs {
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
  