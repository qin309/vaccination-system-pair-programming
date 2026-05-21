<template>
  <div class="vaccine-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>疫苗列表</span>
        </div>
      </template>

      <el-table :data="vaccines" stripe>
        <el-table-column prop="name" label="疫苗名称" width="150" />
        <el-table-column prop="code" label="疫苗编码" width="120" />
        <el-table-column prop="manufacturer" label="生产厂家" width="150" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.type === 0 ? 'success' : 'warning'">
              {{ row.type === 0 ? '免费' : '自费' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">
            {{ row.type === 0 ? '-' : `￥${row.price}` }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="说明" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row.id)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api'

const router = useRouter()
const vaccines = ref([])

const viewDetail = (id) => {
  router.push(`/vaccine/${id}`)
}

onMounted(async () => {
  const res = await api.getVaccines()
  vaccines.value = res.data
})
</script>

<style scoped>
.vaccine-list-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
