<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import {message} from "ant-design-vue";

import router from "../router";
import Page from "../common/Page";
import {logout} from "../api/user";
import Book from "../domain/Book";
import {create, deleteById, updateById, readBatchByPage} from "../api/book";

// 搜索
const keyword = ref("")
const page = reactive<Page<Book>>({
  current: 1,
  size: 5,
  pages: 0,
  total: 0,
  records: Array<Book>()
})

// 状态
const visibleInsert = ref(false)
const visibleDelete = ref(false)
const visibleUpdate = ref(false)

// 书籍
const bookId = ref(0)
const book = reactive<Book>({
  title: '',
  authors: '',
  publisher: ''
})

onMounted(onSearch)

async function onLogout() {
  await logout()
  message.info("登出成功")
  // 跳转页面
  await router.replace("/user")
}

async function onSearch() {
  const r = await readBatchByPage(keyword.value, page.current, page.size)
  if (r.code === 200) {
    page.pages = r.data.pages
    page.total = r.data.total
    page.records = r.data.records
  } else {
    message.warn("检索失败")
  }
}

const onShowUpdate = (id: number, record: Book) => {
  bookId.value = id
  book.title = record.title
  book.authors = record.authors
  book.publisher = record.publisher
  visibleUpdate.value = true
}

const onShowDelete = (id: number, record: Book) => {
  bookId.value = id
  book.title = record.title
  book.authors = record.authors
  book.publisher = record.publisher
  visibleDelete.value = true
}

const onShowInsert = () => {
  book.title = ""
  book.authors = ""
  book.publisher = ""
  visibleInsert.value = true
}

async function onUpdate() {
  const r = await updateById(bookId.value, {
    title: book.title,
    authors: book.authors,
    publisher: book.publisher
  })
  if (r.code == 200) {
    message.info("修改成功")
    // 更新搜索结果
    await onSearch();
  } else {
    message.warn(r.msg)
  }
  visibleUpdate.value = false
}

async function onDelete() {
  const r = await deleteById(bookId.value)
  if (r.code == 200) {
    message.info("删除成功")
    // 更新搜索结果
    await onSearch()
  } else {
    message.warn(r.msg)
  }
  visibleDelete.value = false
}

async function onInsert() {
  const r = await create({
    title: book.title,
    authors: book.authors,
    publisher: book.publisher
  })
  if (r.code == 200) {
    message.info("新增成功")
  } else {
    message.warn(r.msg)
  }
  visibleInsert.value = false
}
</script>

<template>
  <a-page-header title="文献管理">
    <template #extra>
      <a-button type="primary" @click="onLogout">登出</a-button>
    </template>
  </a-page-header>

  <div class="custom">
    <a-input-search v-model:value="keyword" @search="onSearch"/>

    <a-button type="primary" @click="onShowInsert()">新增</a-button>

    <a-table :columns="[
      { title: '篇名', dataIndex: 'title' },
      { title: '作者', dataIndex: 'authors' },
      { title: '刊名', dataIndex: 'publisher' },
      { title: '操作', dataIndex: 'id', slots: { customRender: 'name' }, align: 'center', width: '200px'},
      ]" :rowKey='record=>record.id' :data-source="page.records" :pagination="false">
      <template #name="{ record }">
        <div style="text-align: center">
          <a-button type="primary" @click="onShowDelete(record.id, record)">删除</a-button>
          <a-button type="primary" @click="onShowUpdate(record.id, record)" style="margin-left: 20px">修改</a-button>
        </div>
      </template>
    </a-table>

    <a-pagination show-size-changer
                  v-model:current="page.current" v-model:pageSize="page.size" :total="page.total"
                  @showSizeChange="onSearch" @change="onSearch"/>
  </div>

  <a-modal v-model:visible="visibleUpdate" title="修改" @ok="onUpdate">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal v-model:visible="visibleDelete" title="删除" @ok="onDelete">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title" :disabled="true">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors" :disabled="true">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher" :disabled="true">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal v-model:visible="visibleInsert" title="新增" @ok="onInsert">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
