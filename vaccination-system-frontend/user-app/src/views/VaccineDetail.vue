<template>
  <div class="vaccine-detail-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>疫苗详情</span>
          <el-button text @click="router.back()">返回</el-button>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="疫苗名称">{{ vaccine?.name }}</el-descriptions-item>
        <el-descriptions-item label="疫苗编码">{{ vaccine?.code }}</el-descriptions-item>
        <el-descriptions-item label="生产厂家">{{ vaccine?.manufacturer }}</el-descriptions-item>
        <el-descriptions-item label="类型">
          <el-tag :type="vaccine?.type === 0 ? 'success' : 'warning'">
            {{ vaccine?.type === 0 ? '免费' : '自费' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          {{ vaccine?.type === 0 ? '免费' : `￥${vaccine?.price}` }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="vaccine?.status === 0 ? 'success' : 'danger'">
            {{ vaccine?.status === 0 ? '正常' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="说明" :span="2">{{ vaccine?.description }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import api from '../api'

const router = useRouter()
const route = useRoute()
const vaccine = ref(null)

onMounted(async () => {
  const res = await api.getVaccineDetail(route.params.id)
  vaccine.value = res.data
})
</script>

<style scoped>
.vaccine-detail-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
