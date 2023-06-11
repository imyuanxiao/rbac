// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 获取当前的用户 GET /api/auth/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.CurrentUser>('/api/auth/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/auth/logout */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/auth/logout', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/auth/login */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<API.LoginResponse>('/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}


/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取用户列表 POST /api/user/page */
export async function getUserListByConditions(
  params: {
    current?: number;
    pageSize?: number;
    username?: string;
    userStatus?: number;
    roleIds?: number[];
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.ListData<API.UserListItem>>(`/api/user/page`, {
    method: 'POST',
    data:{
      ...params,
      roleIds: params.roleIds?.map(id => Number(id))
    },
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 更新用户 PUT /api/user/update */
export async function updateUser(body: API.UserListItem, options?: { [key: string]: any }) {
  return request<API.UserListItem>('/api/user/update', {
    method: 'PUT',
    data: {
      ...body,
      roleIds: body.roleIds.map(id => Number(id)),
      orgIds: body.orgIds.map(id => Number(id))
    },
    ...(options || {}),
  });
}

/** 新建用户 POST /api/user/add */
export async function addUser(body: API.UserListItem, options?: { [key: string]: any }) {
  return request<API.UserListItem>('/api/user/add', {
    method: 'POST',
    data: body,
    ...(options || {}),
  });
}

/** 删除用户 DELETE /api/user/delete */
export async function removeUser(body: number[], options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/user/delete', {
    method: 'DELETE',
    data: body,
    ...(options || {}),
  });
}

/** 获取数据列表 GET /api/data/page */
export async function getDataList(
  params: {
    current?: number;
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.ListData<API.DataListItem>>(`/api/data/page/${params.current}&${params.pageSize}`, {
    method: 'GET',
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 获取登录历史列表 GET /api/user/loginHistory */
export async function getLoginHistoryListByConditions(
  params: {
    current?: number;
    pageSize?: number;
    username?: string;
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.ListData<API.LoginHistoryListItem>>(`/api/user/loginHistory`, {
    method: 'POST',
    data:{
      ...params
    },
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 获取用户列表 GET /api/role/list */
export async function getRoleList(options?: { [key: string]: any },) {
  const response = await request<API.ListData<API.RoleListItem>>('/api/role/list', {
    method: 'GET',
    ...(options || {}),
  });
  return {
    response
  };
}

/** 获取角色分页数据 GET /api/role/page */
export async function getRolePage(
  params: {
    current?: number;
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.ListData<API.RoleListItem>>(`/api/role/page/${params.current}&${params.pageSize}`, {
    method: 'GET',
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 更新角色 PUT /api/role/update */
export async function updateRole(body: API.RoleListItem, options?: { [key: string]: any }) {

  return request<API.UserListItem>('/api/role/update', {
    method: 'PUT',
    data: {
      ...body,
      permissionIds: body.permissionIds?.map(item => item.value),
    },
    ...(options || {}),
  });
}

/** 新建角色 POST /api/role/add */
export async function addRole(body: API.RoleListItem, options?: { [key: string]: any }) {
  return request<API.UserListItem>('/api/role/add', {
    method: 'POST',
    data: {
      ...body,
      permissionIds: body.permissionIds?.map(item => item.value),
    },
    ...(options || {}),
  });
}

/** 删除角色 DELETE /api/role/delete */
export async function removeRole(body: number[], options?: { [key: string]: any }) {
  console.log('removeUser>>', options)
  return request<Record<string, any>>('/api/role/delete', {
    method: 'DELETE',
    data: body,
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
