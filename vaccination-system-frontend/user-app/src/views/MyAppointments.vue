<template>
  <div class="appointments-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的预约</span>
        </div>
      </template>

      <el-table :data="appointments" stripe>
        <el-table-column prop="id" label="预约编号" width="80" />
        <el-table-column label="接种对象" width="100">
          <template #default="{ row }">
            {{ row.familyMemberId ? getMemberName(row.familyMemberId) : '本人' }}
          </template>
        </el-table-column>
        <el-table-column prop="vaccineId" label="疫苗名称" width="150">
          <template #default="{ row }">
            {{ getVaccineName(row.vaccineId) }}
          </template>
        </el-table-column>
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="appointmentTime" label="预约时段" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="danger" size="small" @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const router = useRouter()
const appointments = ref([])
const vaccines = ref([])
const familyMembers = ref([])

const statusText = (status) => {
  return ['待接种', '已接种', '已取消', '过期未接种'][status]
}

const statusType = (status) => {
  return ['warning', 'success', 'info', 'danger'][status]
}

const getVaccineName = (id) => {
  const v = vaccines.value.find(v => v.id === id)
  return v ? v.name : ''
}

const getMemberName = (id) => {
  const m = familyMembers.value.find(m => m.id === id)
  return m ? m.name : ''
}

const handleCancel = async (id) => {
  await ElMessageBox.confirm('确定取消该预约吗？', '提示', { type: 'warning' })
  await api.cancelAppointment(id)
  ElMessage.success('取消成功')
  loadData()
}

const loadData = async () => {
  const res = await api.getAppointments()
  appointments.value = res.data
}

onMounted(loadData)
</script>

<style scoped>
.appointments-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
