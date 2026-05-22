<template>
  <div class="appointment-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>预约管理</span>
        </div>
      </template>

      <el-table :data="appointments" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="userId" label="用户ID" width="80" />
        <el-table-column prop="vaccineId" label="疫苗ID" width="80" />
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="appointmentTime" label="预约时间" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleComplete(row)" v-if="row.status === 0">完成接种</el-button>
            <el-button type="danger" size="small" @click="handleCancel(row)" v-if="row.status === 0">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const appointments = ref([])

const statusText = (status) => {
  return ['待接种', '已接种', '已取消', '过期'][status]
}

const statusType = (status) => {
  return ['warning', 'success', 'info', 'danger'][status]
}

const handleComplete = async (row) => {
  ElMessageBox.confirm('确认完成接种？', '提示', { type: 'warning' })
    .then(async () => {
      await api.cancelAppointment(row.id)
      ElMessage.success('已标记为完成')
      loadData()
    })
    .catch(() => {})
}

const handleCancel = async (row) => {
  ElMessageBox.confirm('确认取消该预约？', '提示', { type: 'warning' })
    .then(async () => {
      await api.cancelAppointment(row.id)
      ElMessage.success('已取消')
      loadData()
    })
    .catch(() => {})
}

const loadData = async () => {
  const res = await api.getAppointmentList()
  appointments.value = res.data
}

onMounted(loadData)
</script>

<style scoped>
.appointment-list-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>