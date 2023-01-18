<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import {message} from "ant-design-vue";

import router from "../router";
import Book from "../domain/Book"
import {logout} from "../api/user";
import {create, drop, update, searchWithPage, searchWithSimplePage} from "../api/book";

// 关键词
const keyword = ref("")

// 分页
const page = reactive({
  current: 1,
  size: 5,
  pages: 0,
  total: 0,
  records: Array<Book>()
})

// 弹出框
const visibleUpdate = ref(false);
const visibleDelete = ref(false);
const visibleInsert = ref(false)

// 弹出框中的数据
const bookId = ref(0)
const book = reactive({
  title: '',
  authors: '',
  publisher: ''
})

// 初始化：分页搜索，无关键词
onMounted(onSearch)

async function onClick() {
  const r = await logout()
  if (r.code == 200) {
    // 成功，跳转 UserPage
    message.info("登出成功")
    await router.replace("/user")
  } else {
    // 失败
    message.warn(r.msg)
  }
}

async function onSearch() {
  if (keyword.value.length === 0) {
    // 搜索
    const r = await searchWithPage(page.current, page.size)
    if (r.code == 200) {
      // 成功
      page.pages = r.data.pages
      page.total = r.data.total
      page.records = r.data.records
    } else {
      // 失败
      message.warn("检索失败")
    }
  } else {
    // 简单搜索
    const r = await searchWithSimplePage(keyword.value, page.current, page.size)
    if (r.code == 200) {
      // 成功
      page.pages = r.data.pages
      page.total = r.data.total
      page.records = r.data.records
    } else {
      // 失败
      message.warn("检索失败")
    }
  }
}

const onShowUpdate = (id: number, recode: Book) => {
  bookId.value = id
  book.title = recode.title
  book.authors = recode.authors
  book.publisher = recode.publisher
  visibleUpdate.value = true
}

const onShowDelete = (id: number) => {
  bookId.value = id
  visibleDelete.value = true
}

const onShowInsert = () => {
  book.title = ""
  book.authors = ""
  book.publisher = ""
  visibleInsert.value = true
}

async function onUpdate() {
  const r = await update(bookId.value, {
    title: book.title,
    authors: book.authors,
    publisher: book.publisher
  })
  if (r.code == 200) {
    // 成功
    message.info("修改成功")
    await onSearch();
  } else {
    // 失败
    message.warn(r.msg)
  }
  visibleUpdate.value = false
}

async function onDelete() {
  const r = await drop(bookId.value)
  if (r.code == 200) {
    // 成功
    message.info("删除成功")
    await onSearch()
  } else {
    // 失败
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
    // 成功
    message.info("新增成功")
    await onSearch();
  } else {
    // 失败
    message.warn(r.msg)
  }
  visibleInsert.value = false
}
</script>

<template>
  <a-page-header title="文章管理">
    <template #extra>
      <a-button type="primary" @click="onClick">登出</a-button>
    </template>
  </a-page-header>

  <div class="custom">
    <a-input-search v-model:value="keyword" @search="onSearch"/>

    <a-button type="primary" @click="onShowInsert">新增</a-button>

    <a-table :columns="[
      { title: '篇名', dataIndex: 'title' },
      { title: '作者', dataIndex: 'authors' },
      { title: '刊名', dataIndex: 'publisher' },
      { title: '操作', dataIndex: 'id', slots: { customRender: 'name' }, align: 'center', width: '200px'},
      ]" :rowKey='record=>record.id' :data-source="page.records" :pagination="false">
      <template #name="{ record }">
        <div style="text-align: center">
          <a-button type="primary" @click="onShowDelete(record.id)">删除</a-button>
          <a-button type="primary" @click="onShowUpdate(record.id, record)" style="margin-left: 20px"> 修改</a-button>
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

  <a-modal v-model:visible="visibleDelete" title="删除" @ok="onDelete"/>

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
