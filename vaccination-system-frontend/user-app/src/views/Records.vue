<template>
  <div class="records-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>接种记录</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="loadRecords">
        <el-tab-pane label="本人记录" name="self" />
        <el-tab-pane v-for="member in familyMembers" :key="member.id" :label="`${member.name}的记录`" :name="`family-${member.id}`" />
      </el-tabs>

      <el-table :data="records" stripe>
        <el-table-column prop="vaccineId" label="疫苗名称" width="150">
          <template #default="{ row }">
            {{ getVaccineName(row.vaccineId) }}
          </template>
        </el-table-column>
        <el-table-column prop="batchNo" label="批次号" width="120" />
        <el-table-column prop="injectionSite" label="接种部位" width="100" />
        <el-table-column prop="doctorName" label="接种医生" width="100" />
        <el-table-column prop="vaccinationTime" label="接种时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'">
              {{ row.status === 0 ? '正常' : '有不良反应' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nextVaccinationDate" label="下次接种日期" width="120" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api'

const router = useRouter()
const activeTab = ref('self')
const records = ref([])
const vaccines = ref([])
const familyMembers = ref([])

const getVaccineName = (id) => {
  const v = vaccines.value.find(v => v.id === id)
  return v ? v.name : ''
}

const loadRecords = async (tab) => {
  if (tab === 'self') {
    const res = await api.getRecords()
    records.value = res.data
  } else if (tab.startsWith('family-')) {
    const memberId = parseInt(tab.split('-')[1])
    const res = await api.getFamilyRecords(memberId)
    records.value = res.data
  }
}

onMounted(async () => {
  // 需要从疫苗模块获取疫苗列表
  // const res1 = await api.getVaccines()
  // vaccines.value = res1.data
  const res2 = await api.getFamilyMembers()
  familyMembers.value = res2.data
  loadRecords('self')
})
</script>

<style scoped>
.records-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
