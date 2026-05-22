<template>
  <div class="batch-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>疫苗批次管理</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部批次" name="all">
          <el-table :data="batches" stripe>
            <el-table-column prop="batchNo" label="批次号" width="120" />
            <el-table-column prop="vaccineId" label="疫苗ID" width="80" />
            <el-table-column prop="productionDate" label="生产日期" width="120" />
            <el-table-column prop="expiryDate" label="有效期" width="120" />
            <el-table-column prop="quantity" label="库存数量" width="100" />
            <el-table-column prop="usedQuantity" label="已使用" width="80" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="batchStatusType(row.status)">{{ batchStatusText(row.status) }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="临期预警" name="expiring">
          <el-table :data="expiringBatches" stripe>
            <el-table-column prop="batchNo" label="批次号" width="120" />
            <el-table-column prop="vaccineId" label="疫苗ID" width="80" />
            <el-table-column prop="expiryDate" label="有效期" width="120" />
            <el-table-column prop="quantity" label="库存数量" width="100" />
            <el-table-column prop="usedQuantity" label="已使用" width="80" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const activeTab = ref('all')
const batches = ref([])
const expiringBatches = ref([])

const batchStatusText = (status) => {
  return ['正常', '临期', '过期'][status]
}

const batchStatusType = (status) => {
  return ['success', 'warning', 'danger'][status]
}

onMounted(async () => {
  const res1 = await api.getBatchList()
  batches.value = res1.data
  const res2 = await api.getExpiringBatches()
  expiringBatches.value = res2.data
})
</script>

<style scoped>
.batch-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
