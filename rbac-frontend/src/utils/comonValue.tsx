import {FormattedMessage} from "@@/exports";
import React from "react";

export const roleEnum = {
    1: {
      text: (
        <FormattedMessage
          id="pages.searchTable.role.admin"
      defaultMessage="管理员"
      />
  ),
  },
  2: {
    text: (
      <FormattedMessage
        id="pages.searchTable.role.user"
    defaultMessage="用户"
      />
  ),
  },
}

export const orgEnum = {
  1:  {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org1"
        defaultMessage="总公司"
      />
    ),
  },
  2: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org2"
        defaultMessage="分公司1"
      />
    ),
  },
  3: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org3"
        defaultMessage="分公司2"
      />
    ),
  },
}

export const userStatusEnum = {
    0: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.inUse"
          defaultMessage="正常"
        />
      ),
      status: 'Success',
    },
    1: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.disabled"
          defaultMessage="停用" />
      ),
      status: 'Error',
    },
    2: {
      text: (
        <FormattedMessage id="pages.searchTable.nameStatus.deleted"
                          defaultMessage="注销" />
      ),
      status: 'Default',
    },
}

export const userStatusOptions = [
  {
    value: 0,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.inUse"
        defaultMessage="正常"
      />
    ),
  },
  {
    value: 1,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.disabled"
        defaultMessage="停用"
      />
    ),
  },
  {
    value: 2,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.deleted"
        defaultMessage="注销"
      />
    ),
  },
];


export const loginHistoryEnum = {
  0: {
    text: (
      <FormattedMessage
        id="pages.searchTable.loginHistory.login"
        defaultMessage="登录"
      />
    ),
  },
  1: {
    text: (
      <FormattedMessage
        id="pages.searchTable.loginHistory.logout"
        defaultMessage="登出"
      />
    ),
  }
}

export const permissionEnum = {
  1000: {
    text: (
      <FormattedMessage
        id="type.permission.canUserAdmin"
        defaultMessage="用户管理"
      />
    ),
  },
  1001: {
    text: (
      <FormattedMessage
        id="type.permission.canAddUser"
        defaultMessage="新增用户"
      />
    ),
  },
  1002: {
    text: (
      <FormattedMessage
        id="type.permission.canDeleteUser"
        defaultMessage="删除用户"
      />
    ),
  },
  1003: {
    text: (
      <FormattedMessage
        id="type.permission.canEditUser"
        defaultMessage="编辑用户"
      />
    ),
  },
  3000: {
    text: (
      <FormattedMessage
        id="type.permission.canDataAdmin"
        defaultMessage="数据管理"
      />
    ),
  },
  5000: {
    text: (
      <FormattedMessage
        id="type.permission.canRoleAdmin"
        defaultMessage="角色管理"
      />
    ),
  },
}
